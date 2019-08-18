package laughing.my.utils.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA utils
 *
 * @author laughing
 * @create 2019-03-25 15:34:00
 * @desc
 **/
@Slf4j
public class RSAUtils {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String privateKeyName = "/rsa_private_key.pem";
    public static final String publicKeyName = "/rsa_public_key.crt";

    public static RSAPrivateKey rsaPrivateKey;
    public static RSAPublicKey rsaPublicKey;

    static {
        try {
            log.info("---init rsaPrivateKey---");
            rsaPrivateKey = loadPrivateKey("");
            log.info("---init rsaPublicKey---");
            rsaPublicKey = loadPublicKey("");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);

    }

    public static String privateDecrypt(String data) throws Exception {

        return privateDecrypt(data, rsaPrivateKey);

    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));

    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) throws Exception {

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);

    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return resultDatas;
    }


    private static String readKeyStr(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder buffer = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.charAt(0) != '-') {
                buffer.append(line);
            }
        }
        return buffer.toString();
    }

    public static RSAPublicKey loadPublicKey(String fileName) throws Exception {
        InputStream inputStream;
        if (StringUtils.isNotBlank(fileName)) {
            inputStream = new FileInputStream(new File(fileName));
        } else {
            inputStream = RSAUtils.class.getResourceAsStream(publicKeyName);
        }
        byte[] bytes = java.util.Base64.getDecoder().decode(readKeyStr(inputStream));
        inputStream.close();
        KeyFactory factory = KeyFactory.getInstance("rsa");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return (RSAPublicKey) factory.generatePublic(spec);
    }

    public static RSAPrivateKey loadPrivateKey(String fileName) throws Exception {
        InputStream inputStream;
        if (StringUtils.isNotBlank(fileName)) {
            inputStream = new FileInputStream(new File(fileName));
        } else {
            inputStream = RSAUtils.class.getResourceAsStream(privateKeyName);
        }
        byte[] bytes = java.util.Base64.getDecoder().decode(readKeyStr(inputStream));
        inputStream.close();
        KeyFactory factory = KeyFactory.getInstance("rsa");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    public static void main(String[] args) throws Exception {
        RSAUtils rsaUtils = new RSAUtils();
        String teet = "blog.yoodb.com";

//        String aaa = publicEncrypt(teet, rsaUtils.loadPublicKey(""));
//        System.out.println("");
        String bbb = privateDecrypt("1oYPwvhX0Dqln8wz9pdjeyq6E04IiJFcmh44GS7buHEJcVEH2SDOW6e4e5UInbInEW0iyiKazt1FMYnIcqged565wJsVpbFkTFadR5ZXemqcquSCJZcptD6VquIBXRDZ0IYiXuDza17+fXTKkk453fzyUdkw1ImlEP7W93QnjgA=", rsaPrivateKey);
        System.out.println("---" + bbb);

    }


    public static PrivateKey get(String filename) throws Exception {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }


}
