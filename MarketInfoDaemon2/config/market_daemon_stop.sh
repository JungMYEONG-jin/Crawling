# Licensed to the Apache Software Foundation (ASF) under one or more
#  contributor license agreements.  The ASF licenses this file to You
# under the Apache License, Version 2.0 (the "License"); you may not
#1 1 * * * /home/oper/l2d/run.sh 1> ~/run.log 2>&1

pid=`ps -ef|grep MARKETINFO_DAEMON | grep -v 'grep' | awk '{print $2}'`
kill -9 $pid
