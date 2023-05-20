pipeline {
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch name')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'qa', 'prod'], description: 'Environment')
    }

    agent any

    tools {
        // Define the Maven installation
        maven 'maven'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout specific branch
                checkout([$class: 'GitSCM', branches: [[name: "origin/${params.BRANCH_NAME}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/ridhimak29/alumni']]])
            }
        }

        stage('Build') {
            steps {
                // Build the project
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
            }
        }
    }
}
