package laughing.my.utils.sys;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: huang.xiaolong
 * Date: 2017/8/31
 * Time: 16:59
 */
public class LocalIpAddressUtil {
    public static String ip;

    /**
     * 获取本地ip地址，有可能会有多个地址, 若有多个网卡则会搜集多个网卡的ip地址
     */
    public static Set<InetAddress> resolveLocalAddresses() {
        Set<InetAddress> addrs = new HashSet<InetAddress>();
        Enumeration<NetworkInterface> ns = null;
        try {
            ns = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            // ignored...
        }
        while (ns != null && ns.hasMoreElements()) {
            NetworkInterface n = ns.nextElement();
            Enumeration<InetAddress> is = n.getInetAddresses();
            while (is.hasMoreElements()) {
                InetAddress i = is.nextElement();
                if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
                        && !isSpecialIp(i.getHostAddress())) {
                    addrs.add(i);
                }
            }
        }
        return addrs;
    }

    public static Set<String> resolveLocalIps() {
        Set<InetAddress> addrs = resolveLocalAddresses();
        Set<String> ret = new HashSet<>();
        for (InetAddress addr : addrs) {
            ret.add(addr.getHostAddress());
        }

        return ret;
    }

    public static String getHostAddress() {
        if (ip != null && ip.length() > 0) {

        } else {
            Set<String> set = resolveLocalIps();
            for (String address : set) {
                ip = address;
                break;
            }
        }
        return ip;
    }

    private static boolean isSpecialIp(String ip) {
        if (ip.contains(":")){return true;}
        if (ip.startsWith("127.")) {return true;}
        if (ip.startsWith("169.254.")) {return true;}
        if ("255.255.255.255".equals(ip)) {return true;}
        return false;
    }

    public static void main(String[] args) {
        Set<String> ip = resolveLocalIps();
        System.out.println(getHostAddress());
    }

}
