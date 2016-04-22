cp %CD%\..\target\ServerChangeGui-1.2.jar %CD%\plugins\ServerChangeGui-1.2.jar
"%CD%\..\..\..\Javas\Java\jdk1.8.0_73\bin\java.exe" -Dfile.encoding=UTF-8 -server -XX:+TieredCompilation -jar -XX:CompileThreshold=10 -XX:+UseG1GC -XX:MaxGCPauseMillis=1000 -Xmx256M -Xms256M spigot-1.8.8.jar nogui
