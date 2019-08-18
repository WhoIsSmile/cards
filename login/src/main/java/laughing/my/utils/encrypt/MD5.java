package laughing.my.utils.encrypt;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author laughing
 * @create 2019-03-17 16:25:04
 * @desc md5
 **/
public class MD5 {
    /**
     * MD5方法
     *
     * @param text 明文
     * @param key  密钥 可以为null
     * @return 密文
     * @throws Exception
     */
    public static String md5Encrypt(String text, String key) {
        if (key == null) {
            key = "";
        }
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(new StringBuilder(text).append(key).toString());
//        System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5)  {
        //根据传入的密钥进行验证
        String md5Text = md5Encrypt(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }

        return false;
    }
}
