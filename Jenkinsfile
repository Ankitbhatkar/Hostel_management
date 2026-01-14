pipeline {
    agent any

    options {
            buildDiscarder(logRotator(
                daysToKeepStr: '3',
                numToKeepStr: '1'
            ))
        }

    tools {
        jdk 'JDK21'
        maven 'Maven_3.9.6'
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['none', 'dev', 'prod'], description: 'Select target environment for deployment (none = build only)')
    }

    environment {
        PROJECT = "team2"
        APP_PORT = "8085"
    }

    stages {

        // ✔ Checkout with credentials
        stage('Checkout Code') {
            steps {
                echo "📦 Checking out branch: ${env.BRANCH_NAME}"
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "*/${env.BRANCH_NAME}"]],
                    userRemoteConfigs: [[
                        url: 'https://github.com/projectamitit-glitch/HostelManagement-Team02',
                        credentialsId: 'teams_credentials'
                    ]]
                ])
            }
        }

        // ✔ Fixes added here
        stage('Initialize Environment Variables') {
            steps {
                script {
                    env.SAFE_BRANCH = env.BRANCH_NAME.replaceAll('/', '-')
                    env.IMAGE_NAME = "${PROJECT}-${env.SAFE_BRANCH}-springboot-app".toLowerCase()  // 🔥 FIX: must be lowercase
                    env.CONTAINER_NAME = "${PROJECT}-${env.SAFE_BRANCH}-springboot-${params.ENVIRONMENT}"
                    def CRED_ID = ""   // 🔥 FIX: safe declare to avoid warning

                    if (params.ENVIRONMENT == 'prod') {
                        env.HOST_PORT = "8088"
                        env.DB_HOST = "team_2_prod_postgres"
                        env.DB_NAME = "team_2_prod_db"
                        CRED_ID = "team2_prod_credentials"
                    } else {
                        env.HOST_PORT = "8087"   // change if 8087 is blocked on host
                        env.DB_HOST = "team_2_dev_postgres"
                        env.DB_NAME = "team_2_db"
                        CRED_ID = "team2_dev_credentials"
                    }

                    env.CRED_ID = CRED_ID // 🔥 fix: make available in next stages
                    env.DB_URL = "jdbc:postgresql://${env.DB_HOST}:5432/${env.DB_NAME}"

                    echo """
                    🌿 Branch: ${env.BRANCH_NAME}
                    📦 Image: ${IMAGE_NAME}
                    🌍 Selected Environment: ${params.ENVIRONMENT}
                    🗄 DB_URL: ${env.DB_URL}
                    """
                }
            }
        }

        stage('Build JAR') {
            steps {
                echo "⚙️ Building Spring Boot JAR..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Archive Build Artifacts') {
            steps {
                echo "🗂 Archiving JAR..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // 🧹 Clean JAR after archive
        stage('Cleanup JAR Folder') {
            steps {
                echo "🧹 Cleaning local JAR files..."
                sh 'rm -f target/*.jar || true'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Building Docker image..."
                sh "docker build -t ${IMAGE_NAME}:${params.ENVIRONMENT == 'none' ? 'build' : params.ENVIRONMENT} ."
            }
        }

        stage('Stop Previous Container') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                sh "docker stop ${CONTAINER_NAME} || true"
                sh "docker rm ${CONTAINER_NAME} || true"
            }
        }

        stage('Free Port If Busy') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                script {
                    sh """
                        echo "🔍 Checking if port ${HOST_PORT} is in use by any container..."

                        CONTAINER_ID=\$(docker ps -a --filter "publish=${HOST_PORT}" --format "{{.ID}}")

                        if [ ! -z "\$CONTAINER_ID" ]; then
                            echo "⚠ Port ${HOST_PORT} is in use by container: \$CONTAINER_ID. Stopping it..."
                            docker stop \$CONTAINER_ID || true
                            docker rm \$CONTAINER_ID || true
                        else
                            echo "✔ Port ${HOST_PORT} is free."
                        fi
                    """
                }
            }
        }


        stage('Run New Container') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: env.CRED_ID,
                                                      usernameVariable: 'DB_USER',
                                                      passwordVariable: 'DB_PASS')]) {

                        // 🔑 Only change: escape DB_USER / DB_PASS so Groovy doesn't interpolate secrets
                        sh """
                            docker run -d \
                              --name ${CONTAINER_NAME} \
                              --network jenkins-net \
                              -p ${HOST_PORT}:${APP_PORT} \
                              -v /logs/log_team2/dev:/logs/log_team2/dev \
                              -v /logs/log_team2/prod:/logs/log_team2/prod \
                              -e SPRING_PROFILES_ACTIVE=${params.ENVIRONMENT} \
                              -e SPRING_DATASOURCE_URL=${DB_URL} \
                              -e SPRING_DATASOURCE_USERNAME=\$DB_USER \
                              -e SPRING_DATASOURCE_PASSWORD=\$DB_PASS \
                              -e SERVER_PORT=${APP_PORT} \
                              ${IMAGE_NAME}:${params.ENVIRONMENT}
                        """
                    }
                }
            }
        }

        stage('Verify Deployment') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                script {
                    timeout(time: 30, unit: 'SECONDS') {
                        sh """
                            echo '⏳ Waiting for Spring Boot to be ready...'
                            until docker exec ${CONTAINER_NAME} curl -fsS http://127.0.0.1:${APP_PORT}/actuator/health; do
                                echo '❗ Still starting... Trying again...'
                                sleep 3
                            done
                            echo '✔ Application is UP & HEALTHY!'
                        """
                    }
                }
            }
        }


        // 🧹 EXTRA CLEANUP FOR MEMORY
        stage('Docker Cleanup') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                sh """
                    docker container prune -f
                    docker image prune -f
                """
            }
        }

        stage('Summary') {
            steps {
                echo "🌍 Deployment URL: http://168.220.248.40:${env.HOST_PORT}"
            }
        }
    }

    post {
        success { echo "🎉 Pipeline SUCCESS for ${env.BRANCH_NAME}" }
        failure { echo "❌ Pipeline FAILED for ${env.BRANCH_NAME}" }
    }
}
