/**
 * @QiaoJian
 */
package cn.edu.bjtu.crawler.utils.sina;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author QiaoJian
 * 新浪微博的密码编码，需要调用新浪的给密码编码的javascript程序
 */
public class SinaPasswordEncoder {
	
	/**
	 *
	 * @param pubkey
	 * @param exponentHex 10001
	 * @param messageg
	 * @return
	 */
	public static String rsaCrypt(String pubkey, String exponentHex, String messageg) {
		KeyFactory factory=null;
		try {
			factory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			return "";
		}
		BigInteger publicExponent = new BigInteger(pubkey, 16); /* public exponent */
		BigInteger modulus = new BigInteger(exponentHex, 16); /* modulus */
		RSAPublicKeySpec spec = new RSAPublicKeySpec(publicExponent, modulus);
		RSAPublicKey pub=null;
		try {
			pub = (RSAPublicKey) factory.generatePublic(spec);
		} catch (InvalidKeySpecException e1) {
			return "";
		}
		Cipher enc=null;
		byte[] encryptedContentKey =null;
		try {
			enc = Cipher.getInstance("RSA");
			enc.init(Cipher.ENCRYPT_MODE, pub);
			encryptedContentKey = enc.doFinal(messageg.getBytes());
		} catch (NoSuchAlgorithmException e1) {
			System.out.println(e1.getMessage());
			return "";
		} catch (NoSuchPaddingException e1) {
			System.out.println(e1.getMessage());
			return "";
		} catch (InvalidKeyException e1) {
			System.out.println(e1.getMessage());
			return "";
		} catch (IllegalBlockSizeException e1) {
			System.out.println(e1.getMessage());
			return "";
		} catch (BadPaddingException e1) {
			System.out.println(e1.getMessage());
			return "";
		} 
		return new String(Hex.encodeHex(encryptedContentKey));
}
}
