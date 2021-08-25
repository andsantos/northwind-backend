pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn clean install -Dmaven.test.skip=true'
            }
        }

        stage('test') {
            steps {
                sh 'mvn test -P test -Dspring.profiles.active=test'
            }
        }

        stage('sonar') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }
}