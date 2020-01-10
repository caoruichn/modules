package com.example.modules.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA 加解密
 * @author lyf
 * @email: liuyf@gs-softwares.com
 * @date: 2018年8月9日 上午9:04:07
 */
public class RsaUtils {
	private static Logger logger = LoggerFactory.getLogger(RsaUtils.class);
	
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    
	private static String private_key =  "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALp/rqkFnvBLv54s4YOkgvPJF60SJv"
			+ "nfJxD+ui51uEIHicAu/OxybozKMBmhV/gD9XQ9TguOMhotBWRD+w72jFNQqqMLEJ2rWCjxMAsagpkqQ8OGejU6f+HuZLR5dOWy0ca3U2YNE"
			+ "+FPwCH5DnGYJGdMdZ9KocCVbYGR4x9b4as9AgMBAAECgYB53Ip9KQ4Ux/o6Wes1gJ6Muz58OVs8dKLrSPbQ+tzeUFyjNlDQE2nHFypc5P89"
			+ "MSEGxjQj+tMkJXzbakw3F8TSX6TubId6IDdYKZUC3IyJHQJ2zW0OA43qhH/r1JYwCh3IeAarefH81Fp0eFOWWGd7xYVznMCcUYUcL9sHiwWy"
			+ "4QJBAPrCfveA3r56LyL2ysLo2Xq3T4rc62cNjzQtKI30PQwojcdfqtpuvR0CTLgf4DN6lSj0JylW3PCzhe2Yysw5bPUCQQC+ZWfRQ9gPG1g8"
			+ "arYhmRrrkLXo858U+RAiicFfftZ0EEBbdhk6x0us7p8Ouj5elaxyX5i4rjV6EfnwLlUfb1gpAkEAz9hAhwORHM6HnXJzs8Ru5tFkI9RjZgKO"
			+ "LRA1KhxEMXy3r4azReQ96fqK/p6lJeY4Fj7w6sntzbDvlHVtuoLqtQJBAK0tW0KybOFAw/8gKM1ij2PYWj2q6fWPnSdtGSAdJD6pUYBfaQ9X"
			+ "mvOOj8o7iONLTHEzlpvUiPuhQi8+VVqkUhECQAlDGJ0KhIRdTm9Vabp9wO3CdiLFlQGTp04x/Yj7pVXW8X383JxFSRju26Ejot996ujX9xE0"
			+ "g1dJLt5O5w4sQVU=" ; 
			  

	
	private static String public_key =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6f66pBZ7wS7+eLOGDpILzyRetEib53ycQ/roudbh"
			+ "CB4nALvzscm6MyjAZoVf4A/V0PU4LjjIaLQVkQ/sO9oxTUKqjCxCdq1go8TALGoKZKkPDhno1On/h7mS0eXTlstHGt1NmDRPhT8Ah+Q5xm"
			+ "CRnTHWfSqHAlW2BkeMfW+GrPQIDAQAB" ;

	
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data),key);
    }
    
    public static String decryptByPrivateKey(String data)  {
        byte[] datas;
		try {
			datas = decryptByPrivateKey(decryptBASE64(data),private_key);

	        return new String(datas);
		} catch (Exception e) {
			logger.error("decrypt error.data:" + data , e); //e.printStackTrace();
		}
    
		return null;
    }
    
    public static String encryptByPublicKey(String data)  {
        byte[] datas;
		try {
			
			datas = encryptByPublicKey(data, public_key);
			  
	        return encryptBASE64(datas) ;
		} catch (Exception e) {
			logger.error("decrypt error.data:" + data , e); //e.printStackTrace();
		}
    
		return null;
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap<String,Key>(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }
    
    
    
    public static void mainbak(String[] args) throws Exception {
		 
    	/*
    	Map<String, Key> key_map = initKey()  ;
    	
    	String public_key = encryptBASE64(((PublicKey) key_map.get(PUBLIC_KEY)).getEncoded());
    	String private_key = encryptBASE64(((PrivateKey) key_map.get(PRIVATE_KEY)).getEncoded());
    	
    	
    	System.out.println("public_key:" +public_key); 
    	System.out.println("private_key:" +private_key);
    	
    	String data = "mrtest123" ;
    	
    	byte[] s1 = encryptByPublicKey(data , public_key);
    	String en_str = encryptBASE64(s1) ;
    	System.out.println(en_str);
    	
    	 
    	 byte[] s2 = decryptByPrivateKey(en_str , private_key);
    	 String de_str = new String(s2);
     	 System.out.println(de_str);
    	 
    	 */
    	
    	String data = "lyf-tr-test123";
    	String rsa_str = encryptByPublicKey(data);
    	
    	System.out.println("加密后:" + rsa_str);
    	
    	String encode_str = decryptByPrivateKey(rsa_str);
    	
    	System.out.println("解密后:" + encode_str);
    	 
	}
	
}
