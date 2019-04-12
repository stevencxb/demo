package com.test;



import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;


/**
 * 安全相关的工具类,包括以下工具
 * 1、加密和解密
 * 2、签名和验签
 * 3、Base64Encode和Base64Decode
 * 4、URL特殊字符替换
 * @version 1.0
 * @author zhangyf
 */
public class SecurityUtil {

    public static BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();

	/**
	 * 公钥加密(默认UTF-8)
	 * @param publicKey 公钥
	 * @param data 待加密数据
	 * @return BASE64格式的加密数据
	 * @throws Exception
	 */
	public static String encryptByPublicKey(PublicKey publicKey, String data) throws Exception {
		return encryptByPublicKey(publicKey, data, "UTF-8");
	}
	
	/**
	 * 公钥加密
	 * @param publicKey 公钥
	 * @param data 待加密数据
	 * @param charsetName 编码格式
	 * @return BASE64格式的加密数据
	 * @throws Exception
	 */
	public static String encryptByPublicKey(PublicKey publicKey, String data,String charsetName) throws Exception {
		byte[] dataByte = data.getBytes(charsetName);
		return encryptByPublicKey(publicKey, dataByte);
	}
	
	/**
	 * 公钥加密
	 * @param publicKey 公钥
	 * @param data 待加密数据
	 * @return BASE64格式的加密数据
	 * @throws Exception
	 */
	public static String encryptByPublicKey(PublicKey publicKey, byte[] data) throws Exception {
		byte[] encryptByte = encrypt(publicKey, data);
		return new BASE64Encoder().encodeBuffer(encryptByte);
	}
	
	/**
	 * 加密
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(Key key, byte[] data) throws Exception {
		try {
//			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", bouncyCastleProvider);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",bouncyCastleProvider);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();//获得加密块大小，如：加密前数据为128个byte，而key_size=1024 加密块大小为127 byte,加密后为128个byte;因此共有2个加密块，第一个127 byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);//获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				//这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 私钥解密(默认UTF-8)
	 * @param privateKey 私钥
	 * @param data 加密数据
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(PrivateKey privateKey, String data) throws Exception {
		return decryptByPrivateKey(privateKey, data, "UTF-8");
	}
	
	/**
	 * 私钥解密
	 * @param privateKey 私钥
	 * @param data 加密数据
	 * @param charsetName 编码格式
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(PrivateKey privateKey, String data,String charsetName) throws Exception {
		return new String(decryptBASE64(privateKey, data),charsetName);
	}
	
	/**
	 * 私钥解密
	 * @param privateKey 私钥
	 * @param data 加密数据
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(PrivateKey privateKey, String data) throws Exception {
		byte[] dataByte = new BASE64Decoder().decodeBuffer(data);
		return decrypt(privateKey, dataByte);
	}

	/**
	 * 解密
	 * @param key
	 * @param raw
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(Key key, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", bouncyCastleProvider);
			cipher.init(Cipher.DECRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw e;
		}
	}
	

	/**
	 * 私钥签名(默认UTF-8)
	 * @param privateKey 私钥
	 * @param data 待签名数据
	 * @return BASE64格式的签名
	 * @throws Exception
	 */
	public static String signByPrivateKey(PrivateKey privateKey, String data) throws Exception {
		return signByPrivateKey(privateKey, data, "UTF-8");
	}
	
	/**
	 * 私钥签名
	 * @param privateKey 私钥
	 * @param data 待签名数据
	 * @param charsetName 编码格式
	 * @return BASE64格式的签名
	 * @throws Exception
	 */
	public static String signByPrivateKey(PrivateKey privateKey, String data,String charsetName) throws Exception {
		return signByPrivateKey(privateKey, data.getBytes(charsetName));
	}
	
	/**
	 * 私钥签名
	 * @param privateKey 私钥
	 * @param data 待签名数据
	 * @return BASE64格式的签名
	 * @throws Exception
	 */
	public static String signByPrivateKey(PrivateKey privateKey, byte[] data) throws Exception {
		Signature rsa = Signature.getInstance("SHA1withRSA");
		rsa.initSign(privateKey);
		rsa.update(data);
		return new BASE64Encoder().encodeBuffer(rsa.sign());
	}
	
	/**
	 * 公钥验签(默认UTF-8)
	 * @param publicKey 公钥
	 * @param data 原始数据
	 * @param signData 签名数据
	 * @return 验签结果
	 * @throws Exception
	 */
	public static boolean verifyByPublicKey(PublicKey publicKey,String data,String signData) throws Exception {
		return verifyByPublicKey(publicKey, data, signData,"UTF-8");
	}
	
	/**
	 * 公钥验签
	 * @param publicKey 公钥
	 * @param data 原始数据
	 * @param signData 签名数据
	 * @param charsetName 编码格式
	 * @return 验签结果
	 * @throws Exception
	 */
	public static boolean verifyByPublicKey(PublicKey publicKey,String data, String signData,String charsetName) throws Exception {
		return verifyByPublicKey(publicKey, data.getBytes(charsetName), signData);
	}
	
	/**
	 * 公钥验签
	 * @param publicKey 公钥
	 * @param data 原始数据
	 * @param signData 签名数据
	 * @return 验签结果
	 * @throws Exception
	 */
	public static boolean verifyByPublicKey(PublicKey publicKey,byte[] data, String signData) throws Exception {
		boolean verifyFlag = false;
		Signature rsa = Signature.getInstance("SHA1withRSA");
		rsa.initVerify(publicKey);
		rsa.update(data);
		verifyFlag = rsa.verify(new BASE64Decoder().decodeBuffer(signData));
		return verifyFlag;
	}
	
	/**
	 * 对数据进行BASE64编码(默认UTF-8)
	 * @param data 待编码数据
	 * @return 编码后数据
	 * @throws Exception
	 */
	public static String Base64Encode(String data) throws Exception {
		return Base64Encode(data, "UTF-8");
	}
	
	/**
	 * 对数据进行BASE64编码
	 * @param data 待编码数据
	 * @param charsetName 编码格式
	 * @return 编码后数据
	 * @throws Exception
	 */
	public static String Base64Encode(String data,String charsetName) throws Exception {
		return new BASE64Encoder().encodeBuffer(data.getBytes(charsetName));
	}
	
	/**
	 * 对数据进行BASE64解码(默认UTF-8)
	 * @param data 待解码数据
	 * @return 解码后数据
	 * @throws IOException
	 */
	public static String Base64Decode(String data) throws IOException {
		return Base64Decode(data, "UTF-8");
	}
	
	/**
	 * 对数据进行BASE64解码
	 * @param data 待解码数据
	 * @param charsetName 编码格式
	 * @return 解码后数据
	 * @throws IOException
	 */
	public static String Base64Decode(String data,String charsetName) throws IOException {
		return new String(new BASE64Decoder().decodeBuffer(data),charsetName);
	}
	
	/**
	 * 获取公钥
	 * @param publicKeyFile p7b file
	 * @return 公钥
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String publicKeyFile) throws Exception {
		InputStream inputStream = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			inputStream = new FileInputStream(publicKeyFile);
			Certificate cert = cf.generateCertificate(inputStream);
			PublicKey publicKey = cert.getPublicKey();

			return publicKey;
		} catch (Exception e) {
			throw e;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获取私钥
	 * @param privateKeyFile jks file
	 * @param storePass store password
	 * @param keyAlias key alias
	 * @param keyPass key password
	 * @return 私钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String privateKeyFile, String storePass,
			String keyAlias, String keyPass) throws Exception {
		InputStream inputStream = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			inputStream = new FileInputStream(privateKeyFile);
			keyStore.load(inputStream, storePass.toCharArray());
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
					keyPass.toCharArray());

			return privateKey;
		} catch (Exception e) {
			throw e;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	/**
	 * 验签数据字符串替换
	 * @param data
	 * @return
	 */
	public static String verifyStrReplace(String data) {
		if (data != null || !"".equals("")) {
			data = data.replaceAll("%2B", "\\+");
			data = data.replaceAll("%2F", "\\/");
			data = data.replaceAll("%3D", "\\=");
		} else {
			data = "";
		}
		return data;
	}

	/**
	 * 签名数据字符串替换
	 * @param data
	 * @return
	 */
	public static String signStrReplace(String data) {
		if (data != null || !"".equals("")) {
			data = data.replaceAll("\\+", "%2B");
			data = data.replaceAll("\\/", "%2F");
			data = data.replaceAll("\\=", "%3D");
		} else {
			data = "";
		}

		return data;
	}
	
	/**
	 * 处理特殊字符(HTTP response splitting)
	 * 在HTTP响应头文件中包含未经过校验的数据会导致cache-poisoning，cross-site scripting，cross-user
	 * defacement或者page hijacking攻击。
	 * 应用程序应该屏蔽任何肯定要出现在HTTP响应头中、含有特殊字符的输入，特别是CR（回车符，也可由%0d或\r提供）和LF（换行符，也可由%0a或\n提供）字符，将它们当作非法字符。
	 * 
	 * @param str
	 * @return
	 */
	public static String fixHttpRS(String str) {
		if (str == null) {
			return null;
		}
		String str_temp = str;

		while (true) {
			if ((str_temp.indexOf("\r") == -1)
					&& (str_temp.indexOf("\n") == -1)) {
				break;
			}
			if (str_temp.indexOf("\r") != -1) {
				str_temp = str_temp.replaceAll("\r", "");
			}
			if (str_temp.indexOf("\n") != -1) {
				str_temp = str_temp.replaceAll("\n", "");
			}
		}

		return str_temp.toString();
	}
}