@echo off

REM 
set JAVA_HOME=C:\Program^ Files\Java\jdk1.8.0_77
REM 
set M2_HOME=C:\WORKBENCHS\apache-maven-3.3.3
REM 
set M2=%M2_HOME%\bin
REM 
set PATH=%M2%;%JAVA_HOME%\bin

REM 
REM 
call %M2%\mvn clean install package -Dmaven.test.skip=true
REM mvn mvn clean install package -Dmaven.test.skip=true

pause
@echo on
