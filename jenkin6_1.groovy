pipeline {
    agent any

    stages {
        stage('Stage 1: Build') {
            steps {
                echo 'Building the code using Maven'
            }
        }

        stage('Stage 2: Unit and Integration Tests') {
            steps {
                echo 'Running unit tests using JUnit and integration tests using Selenium'
            }
            post {
                always {
                    sendEmailWithLog('Stage 2: Unit and Integration Tests')
                }
            }
        }

        stage('Stage 3: Code Analysis') {
            steps {
                echo 'Performing code analysis using SonarQube'
            }
        }

        stage('Stage 4: Security Scan') {
            steps {
                echo 'Performing security scan using OWASP ZAP'
            }
            post {
                always {
                    sendEmailWithLog('Stage 4: Security Scan')
                }
            }
        }

        stage('Stage 5: Deploy to Staging') {
            steps {
                echo 'Deploying the application to the staging server'
            }
        }

        stage('Stage 6: Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on the staging environment using Selenium'
            }
        }

        stage('Stage 7: Deploy to Production') {
            steps {
                echo 'Deploying the application to the production :  AWS EC2 instance server '
            }
        }
    }

    post {
        always {
            script {
                def emailSubject = "${currentBuild.currentResult} - ${currentBuild.fullDisplayName}"
                def emailBody = """
                    Pipeline Status: ${currentBuild.currentResult}
                    Build URL: ${env.BUILD_URL}
                """

                emailext(
                    subject: emailSubject,
                    body: emailBody,
                    to: 'daezelgoyal01@gmail.com'
                )
            }
        }
    }
}

def sendEmailWithLog(stageName) {
    script {
        def email_subject = "${currentBuild.currentResult} - ${currentBuild.fullDisplayName} - ${stageName}"
        def emailBody = """
            Stage: ${stageName}
            Status: ${currentBuild.currentResult}
            Build URL: ${env.BUILD_URL}
            Console Output: ${currentBuild.rawBuild.getLog(200)}
        """

        emailext(
            subject: email_subject,
            body: emailBody,
            to: 'abdulmueezgujjar@gmail.com',
            attachLog: true
        )
    }
}
