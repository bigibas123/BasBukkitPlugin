pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        sh '''
                            echo "PATH = ${PATH}"
                            echo "M2_HOME = ${M2_HOME}"
                        '''
        checkout scm
        sh 'mvn -Dmaven.test.failure.ignore=true -Pupstream clean package'
        archiveArtifacts 'target/*.jar'
      }
    }
  }
  tools {
    maven 'Maven 3.5.2'
    jdk 'Local JDK 1.8.0_162'
  }
}
