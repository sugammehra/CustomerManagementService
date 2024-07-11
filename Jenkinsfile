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
		    environment {
		        scannerHome = tool 'SonarQubeScanner' // Define SonarQube scanner tool in Jenkins
		    }
		    steps {
		        script {
		            def scannerArgs = [
		                "-Dsonar.projectKey=CustomerManagementServiceProject",
		                "-Dsonar.sources=src",
		                "-Dsonar.host.url=http://localhost:9000"
		            ]
		            withCredentials([usernamePassword(credentialsId: 'customerManagementProject-token', usernameVariable: 'SONAR_LOGIN', passwordVariable: 'SONAR_PASSWORD')]) {
		                scannerArgs << "-Dsonar.login=\${SONAR_LOGIN}:${SONAR_PASSWORD}"
		                withSonarQubeEnv('SonarQube') {
		                    bat "${scannerHome}\\bin\\sonar-scanner.bat " + scannerArgs.join(" ")
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
