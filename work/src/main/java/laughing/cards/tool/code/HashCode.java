package laughing.cards.tool.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Hash 加密
 *
 * @author laughing
 * @date 2018-02-24 14:36:23
 */
public class HashCode {

    /**
     * 计算字符串的hash值
     *
     * @param string    明文
     * @param algorithm 算法名
     * @return 字符串的hash值
     */
    public static String hash(String string, String algorithm) throws Exception {
        if (string.isEmpty()) {
            return "";
        }
        MessageDigest hash = MessageDigest.getInstance(algorithm);
        byte[] bytes = hash.digest(string.getBytes("UTF-8"));
        String result = "";
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result += temp;
        }
        return result;
    }

    /**
     * 计算文件的hash值
     *
     * @param file      文件File
     * @param algorithm 算法名
     * @return 文件的hash值
     */
    public static String hash(File file, String algorithm) {
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[0124];
        int len;
        try {
            MessageDigest hash = MessageDigest.getInstance(algorithm);
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                hash.update(buffer, 0, len);
            }
            byte[] bytes = hash.digest();

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        String hashStrMD5 = HashCode.hash("b59c67bf196a4758191e42f76670ceba", "MD5");
        System.out.println(hashStrMD5);
    }
}
