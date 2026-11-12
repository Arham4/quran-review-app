@echo off
where gradle >nul 2>nul
if %ERRORLEVEL%==0 (
  gradle %*
) else (
  echo gradle binary not found, please install Gradle 8.10.2 1>&2
  exit /b 1
)
