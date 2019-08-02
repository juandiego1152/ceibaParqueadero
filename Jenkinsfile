pipeline { //Donde se va a ejecutar el Pipeline
    agent {
            label 'Slave5'
        }
    options {
        //Mantener artefactos y salida de consola para el # específico de ejecucionesrecientes del Pipeline.
        buildDiscarder(logRotator(numToKeepStr: '5'))
        //No permitir ejecuciones concurrentes de Pipeline
        disableConcurrentBuilds()
        timestamps()
    }

    //environment {
    //    SBT_HOME = tool name: 'SBT_1.2.8'
      //  SONARSCANNER_HOME = tool name: 'SonarScanner'
        //PATH = "${env.SBT_HOME}/bin:${env.PATH}"
     //}

    environment {
       SBT_HOME  = ${tool name: 'Sbt_1.2.8', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}
       //SONARSCANNER_HOME = tool name: 'SonarScanner'
       PATH = "${env.SBT_HOME}/bin:${env.PATH}"
   }

    //Una sección que define las herramientas para “autoinstalar” y poner en la PATH
    tools {
        //Preinstalada en la Configuración del Master
        jdk 'JDK8_Centos'
    }
    //Aquí comienzan los “items” del Pipeline
    stages {
        stage('Checkout') {
            steps {
                echo "------------>Checkout<------------"

                checkout([$class: 'GitSCM',
                        branches: [[name: '*/master']],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        gitTool: 'Git_Centos', submoduleCfg: [],
                        userRemoteConfigs: [[
                                credentialsId: 'GitHub_juandiego1152',
                                url: 'https://github.com/juandiego1152/ceibaParqueadero/tree/master'
                            ]]
                    ])

            }
        }
        stage('Compile & Unit Tests') {
            steps {
                echo "------------>Unit Tests<------------"
				sh 'sbt clean update compile -Dsbt.log.noformat=true'
            }
        }

        stage ('Test'){
            steps {
                echo "------------>Tests<------------"
                sh 'sbt clean coverage test coverageOff coverageReport -Dsbt.log.noformat=true'
                sh 'cd target/scala-2.11/scoverage-report'
                junit healthScaleFactor: 1.0, testResults: 'target/test-reports/**.xml'
                step([$class: 'ScoveragePublisher', reportDir: 'target/scala-2.11/scoverage-report', reportFile: 'scoverage.xml'])
            }
            post{
                failure {
                    echo 'This will run only if failed'
                    sh 'cd target/scala-2.11/scoverage-report'
                    sh 'ls -R'
                }
            }
        }
        //stage('Static Code Analysis') {
          //  steps {
            //    echo '------------>Análisis de código estático<------------'
              //  withSonarQubeEnv('Sonar') {
                //    sh "${tool name: 'SonarScanner',type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner-Dproject.settings=sonar-project.properties"
                //}
				//withSonarQubeEnv('Sonar') {
				//	sh "${tool name: 'SonarScanner'2,type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"
                //}
            //}
            //stage('Build') {
             //   steps {
               //     echo "------------>Build<------------"
                 //   sh 'sbt clean assembly -Dsbt.log.noformat=true'
                //}
            //}
        //}
    }
    post {
        //success {
          //  echo 'This will run only if successful'
		//	junit 'build/test-results/test/*.xml'
        //}
        failure {
            echo 'This will run only if failed'
			mail (
			to: 'juan.zapata@ceiba.com.co',
			subject: "FailedPipeline:${currentBuild.fullDisplayName}",
			body: "Algo esta mal con: ${currentBuild.fullDisplayName}, visita el proyecto con la sigueinte url: ${env.BUILD_URL}")
        }
    }
}