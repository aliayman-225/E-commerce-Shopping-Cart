pipeline{
	agent any
	
	environment
	{
        	DOCKER_CREDENTIALS_ID = 'DockerHub' // Replace with your Docker Hub credentials ID
        	GITHUB_CREDENTIALS_ID = 'Github-repo' // Replace with your GitHub credentials ID
        	DOCKER_IMAGE_NAME = 'shopping-cart:dev'   // Replace with your Docker image name
    	}

    	triggers {
        	githubPush()
    	}
	tools
	{
		maven "MAVEN3"
		jdk "OracleJDK11"
	}
	stages{
 	       stage('Checkout') {
           		steps {
                		// Checkout the code from GitHub
                		git branch: 'main', credentialsId: "${GITHUB_CREDENTIALS_ID}", url: 'https://github.com/aliayman-225/E-commerce-Shopping-Cart'
            		}
        	}

        	stage('Build with Maven') {
            		steps {
                		// Build the project with Maven
                		sh 'mvn install -DskipTests'
           		 }
        	}

        	stage('Build Docker Image') {
            		steps {
                		script {
                    			// Build Docker image, specifying the Dockerfile location
                    			docker.build("${DOCKER_IMAGE_NAME}", "docker/")
                		}
            		}
        	}

		stage('Push Docker Image') {
            		steps {
                		script {
                    			// Log in to Docker Hub
                    			docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                        			// Push Docker image to Docker Hub
                        			docker.image("${DOCKER_IMAGE_NAME}").push('latest')
                    			}
                		}
            		}
        	}

        	stage('Deploy') {
            		steps {
                		// Deployment steps go here (e.g., deploy to a staging environment)
                		echo 'Deploy to staging environment'
            		}
        	}
    
}

