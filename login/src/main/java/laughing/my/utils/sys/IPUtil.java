package laughing.my.utils.sys;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class IPUtil {
    /**
     * 把ip字符串转换为long型
     *
     * @param ip
     * @return long
     */
    private static long ip2long(String ip) {
        String[] arr = ip.split("\\.");
        return (Long.valueOf(arr[0]) * 0x1000000 + Long.valueOf(arr[1])
                * 0x10000 + Long.valueOf(arr[2]) * 0x100 + Long.valueOf(arr[3]));
    }

    /**
     * 将ip long转换成String
     *
     * @param ip
     * @return String
     */
    private static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf((ip >>> 24)));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf((ip & 0x000000FF)));
        return sb.toString();
    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

//    public static String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }

    public static void main(String[] args) {
        System.out.println(ip2long("117.135.147.73"));
        System.out.println(long2ip(1971819337));
    }
}
