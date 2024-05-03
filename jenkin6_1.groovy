pipeline{
    agent any
    environment{
        DIRECTORY_PATH ="gh repo clone daezel/jenkin6_1"
        TESTING_ENVIRONMENT="AWS EC2"
        PRODUCTION_ENVIRONMENT="AWS EC2"
    }
    stages{
        stage('Build'){
            steps{
                echo "fetch the source code from this -> ${DIRECTORY_PATH}"
                echo "Building..."
                echo "Build automation tool: Maven"
            }      
        }
        stage('Test'){
            steps{
                echo "unit testing using -> Katalon"
                echo "integration testing using -> Selenium"
            }
            post{
                success{
                    emailext(
                        to: 'daezelgoyal01@gmail.com',
                        subject: 'Testing',
                        body: 'Testing successfuly completed!!!', 
                        attachLog: true
                    )
                }
                failure{
                    emailext(
                        to: 'daezelgoyal01@gmail.com',
                        subject: 'Testing',
                        body: 'Testing Failed!!!', 
                        attachLog: true   
                    )
                }
            }
        }
        stage('Code Quality Check'){
            steps{
                echo "checking the quality of the code"
                echo "code analysis tool-> SonarQube"
                echo "Done!!!"
            }
        }
        stage('Security Scan') {
            steps {
                echo "Perform a security scan on the code using -> OWASP Dependency-Check"
            }
            post{
                success{
                    emailext(
                        to: 'daezelgoyal01@gmail.com',
                        subject: 'Security Scan',
                        body: 'Security Scan Tests successfuly completed!!!', 
                        attachLog: true
                    )   
                }
                failure{
                    emailext(
                        to: 'daezelgoyal01@gmail.com',
                        subject: 'Security Scan',
                        body: 'Security Scan Tests Failed!!!', 
                        attachLog: true   
                    )    
                }
            }
        }
        stage('Deploy to Staging'){
            steps{
                echo "deploy the application to ${TESTING_ENVIRONMENT}"
            }
        }
        stage('integration test on staging') {
            steps {
                echo 'running integration test on staging'
            }
        }
        stage('Deploy to Production'){
            steps{
                echo "Deployment to ->  ${PRODUCTION_ENVIRONMENT} Started and completed!"
            }
        }
    }
}
