package com.vanrui.app.user.util.rsa;
 
import java.math.BigInteger; 
import java.security.KeyFactory; 
import java.security.Provider;
import java.security.PublicKey;
import java.security.PrivateKey; 
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException; 
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
 
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ooh.bravo.core.util.PropertiesLoader;

import org.apache.commons.lang3.StringUtils;

/**
 * RSA算法加密/解密工具类。
 *
 * @author maji01
 * @date 2017年7月1日 下午12:22:46
 * @version V1.0.0
 * description：
 */

public class RsaEncryptDecryptUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(RsaEncryptDecryptUtils.class);

	/** 算法名称 */
	private static final String ALGORITHOM = "RSA";
	/*私钥字符串*/
	private static final String STR_PRIV_KEY = PropertiesLoader.getProperty("STR.PRIV.KEY");
	/*私钥字符串*/
    private static final String STR_PUBLIC_KEY = PropertiesLoader.getProperty("STR.PUBL.KEY");
 
	/** 默认的安全服务提供者 */
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
 
	private static KeyFactory keyFactory = null; 
	

	static {
		try {
			keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error(ex.getMessage());
		}
	}
 
	/**
	 * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param publicExponent
	 *            专用指数。
	 * @return RSA专用公钥对象。
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("RSAPublicKeySpec is unavailable.", ex);
		} catch (NullPointerException ex) {
			LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
		}
		return null;
	}

	/**
	 * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param privateExponent
	 *            专用指数。
	 * @return RSA专用私钥对象。
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("RSAPrivateKeySpec is unavailable.", ex);
		} catch (NullPointerException ex) {
			LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
		}
		return null;
	}

	/**
	 * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param privateExponent
	 *            专用指数。
	 * @return RSA专用私钥对象。
	 */
	public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
		if (StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPrivateExponent)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
			}
			return null;
		}
		byte[] modulus = null;
		byte[] privateExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
		} catch (DecoderException ex) {
			LOGGER.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
		}
		if (modulus != null && privateExponent != null) {
			return generateRSAPrivateKey(modulus, privateExponent);
		}
		return null;
	}

	/**
	 * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
	 * 
	 * @param modulus
	 *            系数。
	 * @param publicExponent
	 *            专用指数。
	 * @return RSA专用公钥对象。
	 */
	public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
		if (StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPublicExponent)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
			}
			return null;
		}
		byte[] modulus = null;
		byte[] publicExponent = null;
		try {
			modulus = Hex.decodeHex(hexModulus.toCharArray());
			publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
		} catch (DecoderException ex) {
			LOGGER.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
		}
		if (modulus != null && publicExponent != null) {
			return generateRSAPublicKey(modulus, publicExponent);
		}
		return null;
	}

	/**
	 * 使用指定的公钥加密数据。
	 * 
	 * @param publicKey
	 *            给定的公钥。
	 * @param data
	 *            要加密的数据。
	 * @return 加密后的数据。
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.ENCRYPT_MODE, publicKey);
		return ci.doFinal(data);
	}

	/**
	 * 使用指定的私钥解密数据。
	 * 
	 * @param privateKey
	 *            给定的私钥。
	 * @param data
	 *            要解密的数据。
	 * @return 原数据。
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		Cipher ci = null;
		try {
			ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER); 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
		} catch (NoSuchPaddingException e) { 
			e.printStackTrace();
		}
		try {//
			ci.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (InvalidKeyException e) { 
			e.printStackTrace();
		}
		try {
			return ci.doFinal(data);
		} catch (IllegalBlockSizeException e) { 
			e.printStackTrace();
		} catch (BadPaddingException e) { 
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用给定的公钥加密给定的字符串。
	 * <p />
	 * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null}
	 * 则返回 {@code
	 * null}。
	 * 
	 * @param publicKey
	 *            给定的公钥。
	 * @param plaintext
	 *            字符串。
	 * @return 给定字符串的密文。
	 */
	public static String encryptString(PublicKey publicKey, String plaintext) {
		if (publicKey == null || plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		try {
			byte[] en_data = encrypt(publicKey, data);
			return new String(Hex.encodeHex(en_data));
		} catch (Exception ex) {
			LOGGER.error(ex.getCause().getMessage());
		}
		return null;
	}

	/**
	 * 使用默认的公钥加密给定的字符串。
	 * <p />
	 * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
	 * 
	 * @param plaintext
	 *            字符串。
	 * @return 给定字符串的密文。
	 */
	public static String encryptString(String plaintext) {
		if (plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes(); 
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(STR_PUBLIC_KEY));
        RSAPublicKey publicKey = null;
        try {
            publicKey = (RSAPublicKey)keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) { 
            e.printStackTrace();
        }  
		try {
	 		byte[] en_data = encrypt(publicKey, data);
			return new String(Base64.getEncoder().encodeToString((en_data)));
		} catch (NullPointerException ex) {
			LOGGER.error("keyPair cannot be null.");
		} catch (Exception ex) {
			LOGGER.error(ex.getCause().getMessage());
		}
		return null;
	}

	/**
	 * 使用给定的私钥解密给定的字符串。
	 * <p />
	 * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回
	 * {@code null}。 私钥不匹配时，返回 {@code null}。
	 * 
	 * @param privateKey
	 *            给定的私钥。
	 * @param encrypttext
	 *            密文。
	 * @return 原文字符串。
	 */
	public static String decryptString(PrivateKey privateKey, String encrypttext) {
		if (privateKey == null || StringUtils.isBlank(encrypttext)) {
			return null;
		}
		try {
			byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt(privateKey, en_data);
			return new String(data);
		} catch (Exception ex) {
			LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
		}
		return null;
	}

 
	/**
	 * 使用默认的私钥解密给定的字符串。
	 * <p />
	 * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。 私钥不匹配时，返回
	 * {@code null}。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return 原文字符串。
	 * @throws Exception 
	 */ 
	public static String decryptString(String encrypttext) {
		if (StringUtils.isBlank(encrypttext)) {
			return null;
		} 
		PKCS8EncodedKeySpec priPKCS8 = null;
		priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(STR_PRIV_KEY));
		
		PrivateKey privKey = null;
		try {
			privKey = keyFactory.generatePrivate(priPKCS8);
			
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} 
		byte[] en_data = Base64.getDecoder().decode(encrypttext);
		byte[] data = decrypt((RSAPrivateKey) privKey, en_data);
		return new String(data);
	}

	/**
	 * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return {@code encrypttext} 的原文字符串。
	 */
	public static String decryptStringByJs(String encrypttext) {
		String text = null;
		try {
			text = decryptString(encrypttext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (text == null) {
			return null;
		}
		return StringUtils.reverse(text);
	}

	
	public static void main(String[] args)  { 
	    String str = "5哈哈哈哈643165";
	    String result = encryptString(str);
	    System.out.println("密文："+result);
	    String ess = decryptString(result);
        System.out.println("明文："+ess); 
    }

}