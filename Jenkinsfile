pipeline { //Donde se va a ejecutar el Pipeline
    agent any
    options {
        //Mantener artefactos y salida de consola para el # específico de ejecucionesrecientes del Pipeline.
        buildDiscarder(logRotator(numToKeepStr: '3'))
        //No permitir ejecuciones concurrentes de Pipeline
        disableConcurrentBuilds()
    }

    //Una sección que define las herramientas para “autoinstalar” y poner en la PATH
    tools {
        jdk 'JDK8_Centos' //Preinstalada en la Configuración del Master
        gradle 'Gradle4.5_Centos' //Preinstalada en la Configuración del Master
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
                                url: 'https://github.com/juandiego1152/ceibaParqueadero.git'
                            ]]
                    ])

            }
        }
        stage('Compile & Unit Tests') {
            steps {
                echo "------------>Unit Tests<------------"
				 sh "${tool name: 'sbt', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt test"
                 sh "${tool name: 'sbt', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt scalastyle"
            }
        }
        stage('Static Code Analysis') {
            steps {
                echo '------------>Análisis de código estático<------------'
                withSonarQubeEnv('Sonar') {
                    sh "${tool name: 'SonarScanner',
					type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner-Dproject.settings=sonar-project.properties"
                }
				
				withSonarQubeEnv('Sonar') {
					sh "${tool name: 'SonarScanner',type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"
					
            }
        }
        stage('Build') {
            steps {
                echo "------------>Build<------------"
                Prácticas Técnicas(Gerencia Técnica)
			    sh "${tool name: 'sbt', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt coverage 'test-only * -- -F 4'"
                sh "${tool name: 'sbt', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt coverageReport"
                sh "${tool name: 'sbt', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt scalastyle || true"

            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
			junit 'build/test-results/test/*.xml'
        }
        failure {
            echo 'This will run only if failed'
			mail (to: 'juan.zapata@ceiba.com.co',subject: "FailedPipeline:${currentBuild.fullDisplayName}",body: "Something is wrongwith ${env.BUILD_URL}")
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed' echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
