pipeline {
	agent any
	
	stages {
		stage ('Compile Stage'){
			steps{
				withMaven(maven: 'Maven3.8.3'){
					sh 'mvn clean compile'
					
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
