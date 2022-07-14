# Licensed to the Apache Software Foundation (ASF) under one or more
#  contributor license agreements.  The ASF licenses this file to You
# under the Apache License, Version 2.0 (the "License"); you may not
#1 1 * * * /home/oper/l2d/run.sh 1> ~/run.log 2>&1

#DEV
#export DAEMON_PATH="/sfgweb/mbmwork/marketinfo_daemon"

#REAL
export DAEMON_PATH="/home/mbmwork/marketinfo_daemon"
export CLASSPATH="$CLASSPATH:$DAEMON_PATH/lib/marketinfo_daemon.jar:$DAEMON_PATH/lib/antlr-2.7.6.jar:$DAEMON_PATH/lib/asm.jar:$DAEMON_PATH/lib/bcprov-jdk15-146.jar:$DAEMON_PATH/lib/commons-logging.jar:$DAEMON_PATH/lib/gcm-server.jar:$DAEMON_PATH/lib/JavaPNS_2.2.jar:$DAEMON_PATH/lib/json-simple-1.1.1.jar:$DAEMON_PATH/lib/log4j-1.2.11.jar:$DAEMON_PATH/lib/spring-beans-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-context-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-context-support-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-core-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-jdbc-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-tx-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/standard.jar:$DAEMON_PATH/lib/jta-1.1.jar:$DAEMON_PATH/lib/commons-dbcp-1.4.jar:$DAEMON_PATH/lib/commons-pool-1.5.4.jar:/syspkg/bea11/wlserver_10.3/server/lib/weblogic.jar:/syspkg/bea11/wlserver_10.3/server/lib/webservices.jar:$DAEMON_PATH/lib/aopalliance-1.0.jar:$DAEMON_PATH/lib/spring-aop-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-asm-3.1.1.RELEASE.jar:$DAEMON_PATH/lib/spring-expression-3.1.1.RELEASE.jar"

nohup /opt/java6/bin/java -Dproject=MARKETINFO_DAEMON -Dfile.encoding=KSC5601  -Ddefault.client.encoding=KSC5601 -Dweblogic.security.SSL.ignoreHostnameVerification=true -DUseSunHttpHandler=true com.shinhan.market.daemon.DaemonStarter &
