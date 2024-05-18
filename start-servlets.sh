#!/bin/bash

# Definirea variabilelor de mediu (se presupune ca variabilele de mediu pentru fiecare server sunt puse in path)
PROJECT_ROOT="/c/Users/alice.hincu/Desktop/stickFacultate/anu1/tpjad/servlets-war-2"
PROJECT_ROOT_TOMCAT_EMBEDDED="/c/Users/alice.hincu/Desktop/stickFacultate/anu1/tpjad/servlets-tomcat-embedded-2"
PROJECT_ROOT_JETTY_EMBEDDED="/c/Users/alice.hincu/Desktop/stickFacultate/anu1/tpjad/servlets-jetty-embedded"
CURRENT_SERVER=""
CONTEXT_NAME="servlets_extern"
DOC_BASE="C:\\\\Users\\\\alice.hincu\\\\Desktop\\\\stickFacultate\\\\anu1\\\\tpjad\\\\servlets-war-2\\\\target\\\\exploded"

while true; do
    echo "Alegeți opțiunea: tomcat-war, jetty-war, wildfly-war, glassfish-war, tomcat-extern, jetty-extern, tomcat-embedded, jetty-embedded sau exit"
    read optiune

    case $optiune in
        tomcat-war)
	    CURRENT_SERVER="tomcat"
            # Maven build
            cd "$PROJECT_ROOT"
            ./mvnw install
            # Copierea WAR în Tomcat
            cp "$PROJECT_ROOT/target/servlets.war" "$CATALINA_HOME/webapps/"
            # Pornirea Tomcat
            "$CATALINA_HOME/bin/startup.bat"
            # Deschiderea browser-ului
            start chrome http://localhost:8080/servlets/home
            ;;
        jetty-war)
	    CURRENT_SERVER="jetty"
            # Maven build
            cd "$PROJECT_ROOT"
            ./mvnw install
            # Copierea WAR în Jetty
            cp "$PROJECT_ROOT/target/servlets.war" "$JETTY_BASE/webapps/"
            # Pornirea Jetty
            cd "$JETTY_BASE"
            java -jar "$JETTY_HOME/start.jar" & JETTY_PID=$!
            # Deschiderea browser-ului
            start chrome http://localhost:8080/servlets/home
            ;;
        wildfly-war)
	    CURRENT_SERVER="wildfly"
            # Maven build
            cd "$PROJECT_ROOT"
            ./mvnw install
            # Copierea WAR în WildFly
            cp "$PROJECT_ROOT/target/servlets.war" "$WILDFLY_HOME/standalone/deployments/"
            # Pornirea WildFly
            "$WILDFLY_HOME/bin/standalone.bat" & WILDFLY_PID=$!
            # Deschiderea browser-ului
            start chrome http://localhost:8080/servlets/home
            ;;
        glassfish-war)
	    CURRENT_SERVER="glassfish"
            # Maven build
            cd "$PROJECT_ROOT"
            ./mvnw install
            # Pornirea GlassFish
            "$GLASSFISH_HOME/bin/asadmin" start-domain
            # Deploy WAR în GlassFish
            "$GLASSFISH_HOME/bin/asadmin" deploy "$PROJECT_ROOT/target/servlets.war"
            # Deschiderea browser-ului
            start chrome http://localhost:8080/servlets/home
            ;;
        tomcat-extern)
            CURRENT_SERVER="tomcat-extern"
            # Maven build și explode WAR
            cd "$PROJECT_ROOT"
            ./mvnw clean package
            # Configurarea Tomcat pentru deploy cu context extern
            CONTEXT_FILE="$CATALINA_HOME/conf/Catalina/localhost/${CONTEXT_NAME}.xml"
            echo "<Context displayName=\"${CONTEXT_NAME}\" docBase=\"${DOC_BASE}\" reloadable=\"true\"/>" > "$CONTEXT_FILE"
            "$CATALINA_HOME/bin/startup.bat"
            start chrome http://localhost:8080/${CONTEXT_NAME}/home
            ;;
        jetty-extern)
            CURRENT_SERVER="jetty-extern"
            # Maven build și explode WAR
            cd "$PROJECT_ROOT"
            ./mvnw clean package
            # Configurarea Jetty pentru deploy cu context extern
            JETTY_CONTEXT_FILE="$JETTY_BASE/webapps/${CONTEXT_NAME}.xml"
            echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE Configure PUBLIC \"-//Jetty//Configure//EN\" \"https://www.eclipse.org/jetty/configure_10_0.dtd\"><Configure class=\"org.eclipse.jetty.webapp.WebAppContext\"><Set name=\"contextPath\">/${CONTEXT_NAME}</Set><Set name=\"war\">${DOC_BASE}</Set></Configure>" > "$JETTY_CONTEXT_FILE"
            cd "$JETTY_BASE"
            java -jar "$JETTY_HOME/start.jar" & JETTY_PID=$!
            start chrome http://localhost:8080/${CONTEXT_NAME}/home
            ;;
        tomcat-embedded)
            CURRENT_SERVER="tomcat-embedded"
            # Generarea jar-ului executabil
            cd "$PROJECT_ROOT_TOMCAT_EMBEDDED"
            ./mvnw clean compile assembly:single
            # Pornirea Tomcat embedded
            java -jar "$PROJECT_ROOT_TOMCAT_EMBEDDED/target/servlets-tomcat-embedded-2-jar-with-dependencies.jar" & TOMCAT_EMBEDDED_PID=$!
            echo "Tomcat embedded rulând..."
	    start chrome http://localhost:8080/home
            ;;
	jetty-embedded)
            CURRENT_SERVER="jetty-embedded"
            # Generarea jar-ului executabil
            cd "$PROJECT_ROOT_JETTY_EMBEDDED"
            ./mvnw clean compile assembly:single
            # Pornirea Jetty embedded
            java -jar "$PROJECT_ROOT_JETTY_EMBEDDED/target/servlets-jetty-embedded-jar-with-dependencies.jar" & JETTY_EMBEDDED_PID=$!
            echo "Jetty embedded rulând..."
	    start chrome http://localhost:8080/
            ;;

    	exit)
            echo "Oprirea serverului ${CURRENT_SERVER} și închiderea scriptului..."
            case $CURRENT_SERVER in
                tomcat-war|tomcat-extern)
                    "$CATALINA_HOME/bin/shutdown.bat"
                    ;;
		tomcat-embedded)
                    if [[ -n $TOMCAT_EMBEDDED_PID ]]; then
                        kill -SIGTERM $TOMCAT_EMBEDDED_PID
                        wait $TOMCAT_EMBEDDED_PID
                    fi
                    ;;    
                jetty-war|jetty-extern)
                    if [[ -n $JETTY_PID ]]; then
                        kill -SIGINT $JETTY_PID
                        wait $JETTY_PID
                    fi
                    ;;
		jetty-embedded)
                    if [[ -n $JETTY_EMBEDDED_PID ]]; then
                        kill -SIGTERM $JETTY_EMBEDDED_PID
                        wait $JETTY_EMBEDDED_PID
                    fi
                    ;;

                wildfly)
                    if [[ -n $WILDFLY_PID ]]; then
                        kill -SIGTERM $WILDFLY_PID
                        wait $WILDFLY_PID
                    fi
                    ;;
                glassfish)
                    "$GLASSFISH_HOME/bin/asadmin" stop-domain domain1
                    ;;
            esac
            break
            ;;
        *)
            echo "Opțiune invalidă. Încercați din nou."
            ;;
    esac
done

