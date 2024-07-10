pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-hub-credential'
        DOCKER_IMAGE = 'sugammehra12/CustomerManagementService'
        SONARQUBE_SERVER = 'SonarQube' // Name of your SonarQube server in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sugammehra/CustomerManagementService.git'
            }
        }
        
        stage('Build') {
            steps {
                bat './mvnw clean package'
            }
        }

        

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.BUILD_ID}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', env.DOCKER_CREDENTIALS_ID) {
                        docker.image("${env.DOCKER_IMAGE}:${env.BUILD_ID}").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                // Add your deployment steps here, e.g., using kubectl or docker run
                // For example, using Docker Compose:
                bat 'docker-compose up -d'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
