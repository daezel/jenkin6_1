pipeline{
    agent any
    environment{
        DIRECTORY_PATH ="path"
        TESTING_ENVIRONMENT="test_env"
        PRODUCTION_ENVIRONMENT="DAEZEL"
    }
    stages{
        stage('Build'){
            steps{
                echo "fetch the source code from this ->  ${DIRECTORY_PATH}"
                echo "compile the code and generate any necessary artifacts"
            }
        }
        stage('Test'){
            steps{
                echo "unit tests"
                echo "integration tests"
            }
        }
        stage('Code Quality Check'){
            steps{
                sh 'echo "check the quality of the code"'
            }
        }
        stage('Deploy'){
            steps{
                echo "deploy the application to ${TESTING_ENVIRONMENT}"
            }
        }
        stage('Approval'){
            steps{
                echo "Approval Started"
                sleep(time:10, unit: 'SECONDS')
                echo "Approval Ended"
            }
        }
        stage('Deploy to Production'){
            steps{
                echo "Deployment to ${PRODUCTION_ENVIRONMENT} Started and completed!"
            }
        }
    }
}
