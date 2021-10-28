pipeline {
	agent any
	
	stages {
		stage ('Compile Stage'){
			steps{
				withMaven(maven: 'Maven3.8.3'){
					sh 'mvn clean compile'
					sh 'mvn -f ("C://Users//USER//Documents//workspace-spring-tool-suite-4-4.12.0.RELEASE//Cards-Spring-Angular//yugioh-back//pom.xml")'
					
				}
			}
			
		}
		
		stage ('Testing Stage'){
			steps{
				withMaven(maven: 'Maven3.8.3'){
					sh 'mvn test'
				}
			}
        }	
		stage ('Deployment Stage'){
			steps{
				withMaven(maven: 'Maven3.8.3'){
					sh 'mvn deploy'
				}
			}
			
		}
			
		}
	}
