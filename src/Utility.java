public class Utility {
    /**
     * try to get port from port
     * if host is  192.168.0.1:1998
     * then return 1998
     * if host is 192.168.0.1
     * then return -1
     *
     * @param host host is ur ip
     * @return port or -1
     */
    public static int getPort(String host) {
        try {
            String port = host.split(":")[1];
            String none = "";
            if (none.equals(port)) {
                return -1;
            } else {
                return Integer.parseInt(port);
            }
        } catch (Exception e) {
            return -1;
        }

    }

    /**
     * get ip from 192.168.0.1:12312 and then return 192.168.0.1
     *
     * @param host host is ur ip
     * @return the host's ip
     */

    public static String getIp(String host) {
        try {
            return host.split(":")[0];
        } catch (Exception e) {
            return host;
        }
    }
}
