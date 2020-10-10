pipeline { 
    agent any 
	
    stages { 
        stage('Git clone repo') { 
            steps { 
			
               sh 'git clone url'
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
        stage('Create docker image') { 
            steps { 
			
               sh 'docker build -t weatherapp:latest .'
            }
        }
    }
	
	
	stages { 
        stage('Deploy to EC2') { 
            steps { 
			
               sh ''
            }
        }
    }
	
}