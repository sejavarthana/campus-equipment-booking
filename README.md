\# Campus Equipment Booking Service



Java 17 / Spring Boot REST API for a CI/CD lab assessment.



\## Build and Test



Run the tests:



`mvn clean test`



Package the application:



`mvn clean package`



The packaged JAR is located at `target/campus-equipment-booking-1.0.0.jar`.



\*\*Verified result:\*\* 12 tests passed, with 0 failures, 0 errors, and 0 skipped. Maven package completed successfully.



\## Run Locally



`mvn spring-boot:run`



The application uses port 8080 by default. Port 8080 was occupied during local testing, so this command could not start another application instance.



\## REST Endpoints



\- `GET /api/equipment`

\- `GET /api/equipment/{id}`

\- `POST /api/bookings`

\- `GET /api/bookings/{id}`

\- `POST /api/bookings/{id}/cancel`

\- `GET /actuator/health`



\## Defects Corrected



\### Booking Capacity



Corrected the capacity validation so a booking cannot exceed the available inventory.



\### Unknown Equipment



Unknown equipment identifiers are rejected with HTTP 404. Tests verify valid and unknown identifiers and ensure rejected bookings do not modify inventory.



\## Docker



Build the image:



`docker build -t campus-equipment-booking:set-c .`



The image was built with the required tag `campus-equipment-booking:set-c`.



The container was verified using host port 8081 mapped to container port 8080. The health endpoint returned `UP` at:



`http://localhost:8081/actuator/health`



The required host mapping `8080:8080` remains unverified because another process occupied host port 8080.



\## Kubernetes



Manifests are stored in the `k8s/` directory:



\- `k8s/deployment.yaml`

\- `k8s/service.yaml`



The Deployment and Service were created successfully. The Deployment was scaled to two replicas, and both application Pods reached `Running` with `1/1` ready.



Check resources:



`kubectl get deployments`



`kubectl get pods`



`kubectl get services`



Forward the Kubernetes Service to local port 8081:



`kubectl port-forward service/campus-equipment-booking 8081:8080`



Verify health from another terminal:



`Invoke-RestMethod http://localhost:8081/actuator/health`



Scale the application:



`kubectl scale deployment/campus-equipment-booking --replicas=2`



`kubectl rollout status deployment/campus-equipment-booking`



\## Notes



Inventory is stored in memory and resets when the application restarts. No database is required.

