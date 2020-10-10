pipeline { 
    agent any 
	
    stages { 
        stage('Git clone repo') { 
            steps { 
			
               sh 'git clone https://github.com/kiransterling/weatherprediction.git'
            }
        }
    }
	
	
	stages { 
        stage('Build and test maven project') { 
            steps { 
			
			   sh 'cd weatherprediction'
               sh 'docker run --rm -i -v "$PWD":/usr/src/mymaven -v "$HOME/.m2":/usr/share/maven/ref -w /usr/src/mymaven maven:3.6-jdk-8-slim mvn clean install'
               sh  'ls -lrt target'
               
            }
        }
    }
	
	stages { 
        stage('Create docker image') { 
            steps { 
			
			//need to first register with DockerHub before you can push images to your account
		
			   sh 'cd weatherprediction'
               sh 'docker build -t kashyap1729/weatherapp:latest .'
               sh 'docker push kashyap1729/weatherapp:latest'
            }
        }
    }
	
	
	stages { 
        stage('Deploy to EC2 and run image') { 
            steps { 
			 
			 //Need to login to EC2
               sh 'login to EC2 with credentials'
               sh 'docker pull kashyap1729/weatherapp:latest'
               sh 'docker run -p 80:8080 --name my-app kashyap1729/weatherapp:latest'
               
            }
        }
    }
	
}