@echo off

docker-compose -f docker-compose-stressTest.yml up -d

echo Waiting for services to start...

:wait_loop
docker-compose -f docker-compose-stressTest.yml logs | findstr "Started"
if %ERRORLEVEL% neq 0 (
    timeout /t 5 >nul
    goto wait_loop
)

echo Services are up!

npx autocannon -c 1000 -d 30 http://localhost:8080/deck