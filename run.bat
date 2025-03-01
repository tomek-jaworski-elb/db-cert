@echo off
REM Batch Script to Check Java Version and Run the db-access-service
REM java -jar dbcert-0.0.1-SNAPSHOT.jar --task.fixedRate=5000 --task.initialDelay=2000

REM Define environment variables
SET JAR_FILE_VERSION=1.1
SET JAVA_TOOL_OPTIONS=-Xms256m -Xmx512m
SET APP_FILE=dbcert
SET APP_PATH=C:\\Users\\Tomek\\IdeaProjects\\dbcert
SET FIXED_RATE=5000
SET INITIAL_DELAY=1000
SET FILE_PATH=C:\\Users\\Tomek\\db-app\\db\\kurs2002.mdb
SET RESTCLIENT_URL=http://localhost:8081
SET RETRY_COUNT=3
SET READ_TIMEOUT=60
SET CONNECT_TIMEOUT=60

echo =====================================
echo Checking for Java installation ...
echo =====================================
java -version

IF %ERRORLEVEL% NEQ 0 (
    echo Error: Java is not installed or not configured correctly.
    echo Please install Java or check your environment variables.
    pause
    exit /b 1
)

echo =====================================
echo Running %APP_FILE%-%JAR_FILE_VERSION% ...
echo =====================================
java -jar target/%APP_FILE%-%JAR_FILE_VERSION%.jar %JAVA_TOOL_OPTIONS% ^
--task.fixedRate=%FIXED_RATE% ^
--task.initialDelay=%INITIAL_DELAY% ^
--db.file.path=%FILE_PATH% ^
--rest.client.url=%RESTCLIENT_URL% ^
--task.retryCountMax=%RETRY_COUNT% ^
--rest.client.custom.readtimeout=%READ_TIMEOUT% ^
--rest.client.custom.connecttimeout=%CONNECT_TIMEOUT%

IF %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to start %APP_FILE%.
    echo Please check the jar file and try again.
    pause
    exit /b 1
)

echo =====================================
echo %APP_FILE% is running successfully.
echo =====================================
timeout /t 5
exit /b 0