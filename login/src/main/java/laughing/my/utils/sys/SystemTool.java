package laughing.my.utils.sys;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取系统参数
 *
 * @author laughing
 * @date 2017-10-31 10:33:42
 */
@Slf4j
public class SystemTool {

    /**
     * 系统的HostName
     */
    private static String hostName;

    static {
        getHostName();
    }

    /**
     * 获取系统的HostName
     *
     * @return
     */
    public static String getHostName() {
        if (StringUtils.isNotEmpty(hostName)) {
            return hostName;
        }
        try {
            hostName = getSysHostName();
        } catch (Exception e) {
            log.error("error getHostName: {}", e.getMessage());
            hostName = "UNKNOWN";
        }
        return hostName;
    }

    /**
     * 获取系统的HostName
     *
     * @return
     */
    public static String getSysHostName() throws UnknownHostException {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }

    public static String getHostNameForLiunx() throws UnknownHostException {

        return (InetAddress.getLocalHost()).getHostName();

    }


}
