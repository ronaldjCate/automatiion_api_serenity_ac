import java.text.SimpleDateFormat

def defDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
def defDate = new Date()
def defTimestamp = defDateFormat.format(defDate).toString()


pipeline {

    agent any

    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }

    options {
		buildDiscarder(logRotator(numToKeepStr: '20'))
	    disableConcurrentBuilds()
	}

    stages {

        stage ('Build') {
                    steps {
                    	bat ("mvn clean install -DskipTests")
                        bat ("mvn clean verify")
                    }
        }

		stage ('Ejecutar Pruebas') {
                	steps {
                		script {

                			try {
                				bat ('mvn test -Dkarate.options="--tags @ValidarGeneracionIdSesion"')
                				//bat ("mvn serenity:aggregate")
                				echo 'Ejecucion de pruebas sin errores...'
                			}
                			catch (ex) {
                				echo 'Finalizo ejecucion con fallos...'
                				error ('Failed')
                            }
                        }
                   }
        }
                stage ('Reporte') {
                	steps {
                		script {
                             try {
                            	bat ("echo ${WORKSPACE}")
                            	bat ("echo ${defTimestamp}")
                            	publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: "${WORKSPACE}/target/karate-reports", reportFiles: 'karate-summary.html', reportName: 'Evidencias de Prueba', reportTitles: 'Reporte de Pruebas'])
                            	//saucePublisher()
                              echo 'Reporte realizado con exito'
                            }

                            catch (ex) {
                                echo 'Reporte realizado con Fallos'
                                error ('Failed')
                            }
                        }
                    }
                }
    }
}