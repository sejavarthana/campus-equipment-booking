
pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    stages {
        stage('Checkout GitHub') {
            steps {
                checkout scm
                bat 'git status'
            }
        }

        stage('Maven Tests') {
            steps {
                bat '"C:\\Program Files\\apache-maven-3.9.16\\bin\\mvn.cmd" clean test'
            }
        }

        stage('Package JAR') {
            steps {
                bat '"C:\\Program Files\\apache-maven-3.9.16\\bin\\mvn.cmd" clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat '"C:\\Users\\SNUC\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe" build -t campus-equipment-booking:set-c .'
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: GitHub checkout, tests, packaging, and Docker build completed!'
        }
        failure {
            echo 'FAILED: Check Console Output for the first error.'
        }
    }
}
