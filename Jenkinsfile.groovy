// Jenkinsfile
pipeline {
    agent any

    environment {
        DOCKER_HUB_REPO = 'your_dockerhub_username/my-java-app'
        DOCKER_HUB_CREDENTIALS = 'dockerhub-credentials-id'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/yourusername/my-java-app.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_HUB_REPO}:latest")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKER_HUB_CREDENTIALS}") {
                        docker.image("${DOCKER_HUB_REPO}:latest").push()
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning up..."
            sh 'docker rmi ${DOCKER_HUB_REPO}:latest'
        }
    }
}
