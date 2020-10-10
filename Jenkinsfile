pipeline { 
    agent any 
	
    stages { 
        stage('Git clone repo') { 
            steps { 
            
               git url: 'https://github.com/kiransterling/weatherprediction.git'
		
            }
        }
    }
	
	
	stages { 
        stage('Build and test maven project') { 
            steps { 
			
               sh 'docker run --rm -i -v "$PWD":/usr/src/mymaven -v "$HOME/.m2":/usr/share/maven/ref -w /usr/src/mymaven maven:3.6-jdk-8-slim mvn clean install'
               sh  'ls -lrt target'
               
            }
        }
    }
	
	stages { 
        stage('Build docker image') { 
            steps { 
			
               sh 'docker build -t kashyap1729/weatherapp:latest .'
            }
        }
    }
    
    stages { 
        stage('Push docker image') { 
            steps { 
		
		   withCredentials([usernamePassword(credentialsId: 'docker-psw', passwordVariable: 'PASSWORD')]) {
  
			   sh "docker login -u kashyap1729 -p ${PASSWORD}"
               sh 'docker push kashyap1729/weatherapp:latest'
               
               }
            }
        }
    }
	
	
	stages { 
        stage('Deploy to EC2 and run image') { 
            steps { 
            
             def dockerRun = 'docker run -p 80:8080 -d --name my-app kashyap1729/weatherapp:latest'
			 
			   
                sshagent (['ec2-login']) {
                 sh "ssh -o StrictHostKeyChecking=no Ubuntu@18.217.63.227 ${dockerRun}"
               }
               
            }
        }
    }
	
}