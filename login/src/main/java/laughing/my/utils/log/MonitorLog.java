package laughing.my.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author laughing
 * @create 2019-09-23 21:36:18
 * @desc 监控日志
 **/
public class MonitorLog {
    private static Logger MONITOR = LoggerFactory.getLogger("MONITOR");

    public static void wirterMoniterLog(String monitorLog) {
        MONITOR.info(monitorLog);
    }
}
