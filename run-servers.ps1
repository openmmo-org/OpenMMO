# run-servers.ps1
$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$projectRoot'; ./gradlew :server.login:run"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$projectRoot'; ./gradlew :server.game:run"