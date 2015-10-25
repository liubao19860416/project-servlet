@start javaw -cp "../WebRoot/WEB-INF/lib/h2-1.3.154.jar;%H2DRIVERS%;%CLASSPATH%" org.h2.tools.Console %*
@if errorlevel 1 pause