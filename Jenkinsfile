pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-hub-credential'
        DOCKER_IMAGE = 'sugammehra12/customermanagementservice'
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
        
        
	   stage('SonarQube Analysis') {
		    steps {
		        script {
		            withMaven(maven: 'MavenV_3.9.6') {
		                withCredentials([string(credentialsId: 'customerManagementProject-token', variable: 'SONAR_TOKEN')]) {
		                    bat "mvn sonar:sonar -Dsonar.projectKey=CustomerManagementServiceProject -Dsonar.host.url=http://localhost:9000 -Dsonar.login=${SONAR_TOKEN}"
		                }
		            }
		        }
		    }
		}

        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${env.DOCKER_IMAGE}:${env.BUILD_ID} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', env.DOCKER_CREDENTIALS_ID) {
                        bat "docker push ${env.DOCKER_IMAGE}:${env.BUILD_ID}"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                // Add your deployment steps here, e.g., using kubectl or docker run
                // Example deployment command:
                bat "docker run -d -p 8181:8181 ${env.DOCKER_IMAGE}:${env.BUILD_ID}"
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
