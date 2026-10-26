
package edu.university.equipment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ApiControllerTest {

    private BookingService service;
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        service = new BookingService();
        mvc = MockMvcBuilders
                .standaloneSetup(new ApiController(service))
                .build();
    }

    @Test
    void validEquipmentIdentifierReturnsOk() throws Exception {
        mvc.perform(get("/api/equipment/LAPTOP01"))
                .andExpect(status().isOk());
    }

    @Test
    void unknownEquipmentIdentifierReturnsNotFound() throws Exception {
        mvc.perform(get("/api/equipment/UNKNOWN"))
                .andExpect(status().isNotFound());
    }

    @Test
    void bookingUnknownEquipmentReturnsNotFoundWithoutChanges()
            throws Exception {

        mvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "borrowerId": "STU1001",
                          "equipmentId": "UNKNOWN",
                          "quantity": 1
                        }
                        """))
                .andExpect(status().isNotFound());

        // No inventory should change and no booking should be created.
        org.junit.jupiter.api.Assertions.assertEquals(
                10, service.getEquipment("LAPTOP01").getAvailableQuantity());
        org.junit.jupiter.api.Assertions.assertEquals(
                4, service.getEquipment("PROJECTOR01").getAvailableQuantity());
        org.junit.jupiter.api.Assertions.assertEquals(
                8, service.getEquipment("TABLET01").getAvailableQuantity());
        org.junit.jupiter.api.Assertions.assertEquals(0, service.bookingCount());
    }
}
