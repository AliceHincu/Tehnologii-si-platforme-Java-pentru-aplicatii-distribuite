@echo off
SETLOCAL EnableExtensions

:: Set project roots
set PROJECT_ROOT_WILDFLY="C:\Users\alice.hincu\Desktop\stickFacultate\anu1\tpjad\EjbJpa\wf\ejb-jpa"
set PROJECT_ROOT_GLASSFISH="C:\Users\alice.hincu\Desktop\stickFacultate\anu1\tpjad\EjbJpa\gs\ejb-jpa"

:: Set java path variable
set "JAVA_HOME=%JAVA_HOME_8%"

:: Ask the user which AS to deploy to
echo Choose the Application Server to deploy to:
echo [1] WildFly 20
echo [2] GlassFish
echo [3] Stop WildFly 20
echo [4] Stop GlassFish
set /p USER_CHOICE="Enter your choice: "

:: Deploy the WAR to the selected AS
if "%USER_CHOICE%"=="1" (
    echo Building the project and create the WAR file
    call "%PROJECT_ROOT_WILDFLY%\gradlew" war
    echo Starting WildFly 20...
    start "" "%WILDFLY_HOME_20%\bin\standalone.bat"
    echo Deploying to WildFly 20...
    del "%WILDFLY_HOME_20%\standalone\deployments\*.war"
    copy "%PROJECT_ROOT_WILDFLY%\build\libs\*.war" "%WILDFLY_HOME_20%\standalone\deployments"
    echo Waiting for deployment...
    timeout /t 5 /nobreak > NUL
    start http://localhost:8080/ejb-jpa-1.0

) else if "%USER_CHOICE%"=="2" (
    echo Building the project and create the WAR file
    call "%PROJECT_ROOT_GLASSFISH%\gradlew" war
    echo Starting Glassfish...
    call "%GLASSFISH_HOME_5%\bin\asadmin.bat" start-domain
    
    :: Checking if GlassFish Server is ready
    :CheckServer
    echo Checking if GlassFish Server is ready...
    "%GLASSFISH_HOME_5%\bin\asadmin.bat" list-domains
    if ERRORLEVEL 1 (
        echo GlassFish Server is not ready yet...
        timeout /t 5 /nobreak > NUL
        goto CheckServer
    )
    echo Path to WAR file: %PROJECT_ROOT_GLASSFISH%\build\libs
    echo Deploying to GlassFish...
    call "%GLASSFISH_HOME_5%\bin\asadmin.bat" deploy "%PROJECT_ROOT_GLASSFISH%\build\libs\ejb-jpa-1.0.war"
    echo Waiting for deployment...
    timeout /t 5 /nobreak > NUL
    start http://localhost:8080/ejb-jpa-1.0
) else if "%USER_CHOICE%"=="3" (
    echo Stopping WildFly 20...
    call "%WILDFLY_HOME_20%\bin\jboss-cli.bat" --connect command=:shutdown
    echo WildFly 20 has been stopped.
) else if "%USER_CHOICE%"=="4" (
    echo Stopping GlassFish...
    call "%GLASSFISH_HOME_5%\bin\asadmin.bat" stop-domain
    echo GlassFish has been stopped.
) else (
    echo Invalid choice. Exiting...
    goto end
)
set "JAVA_HOME=%JAVA_HOME_11%"
echo Deployment completed. Check the server console for details.

:end
ENDLOCAL
pause

