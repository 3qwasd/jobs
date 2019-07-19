package jobs.toolkit.secure;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 哈希加密算法工具集
 * @author jobs
 *
 */
public class SHATools {
	
	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA-1";
	public static final String SHA224 = "SHA-224";
	public static final String SHA256 = "SHA-256";
	public static final String SHA384 = "SHA-384";
	public static final String SHA512 = "SHA-512";
	/**
	 * 使用某个特定的SHA算法来计算某个文件的SHA值
	 * @param file
	 * @param algorithm
	 * @return SHA 16进制字串
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static String digestFileBySHA(File file, String algorithm) throws IOException{
		
		if(file == null)
			throw new FileNotFoundException();
		
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			MessageDigest sha = MessageDigest.getInstance(algorithm);
			in = new DigestInputStream(in, sha);
			while(in.read() != -1);
			byte[] digest = sha.digest();
			
			return DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	/**
	 * 使用MD5算法来计算某个文件的SHA值
	 * @param file
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileByMD5(File file) throws IOException{
		return digestFileBySHA(file, MD5);
	}
	/**
	 * 使用SHA-1算法来计算某个文件的SHA值
	 * @param file
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA1(File file) throws IOException{
		return digestFileBySHA(file, SHA1);
	}
	/**
	 * 使用SHA-256算法来计算某个文件的SHA值
	 * @param file
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA256(File file) throws IOException{
		return digestFileBySHA(file, SHA256);
	}
	/**
	 * 使用SHA-512算法来计算某个文件的SHA值
	 * @param file
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA512(File file) throws IOException{
		return digestFileBySHA(file, SHA512);
	}
	/**
	 * 使用某个特定的SHA算法来计算某个文件的SHA值
	 * @param filePath
	 * @param algorithm
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA(String filePath, String algorithm) throws IOException{
		File file = new File(filePath);
		return digestFileBySHA(file, algorithm);
	}
	/**
	 * 使用MD5算法来计算某个文件的SHA值
	 * @param filePath
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileByMD5(String filePath) throws IOException{
		return digestFileBySHA(filePath, MD5);
	}
	/**
	 * 使用SHA-1算法来计算某个文件的SHA值
	 * @param filePath
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA1(String filePath) throws IOException{
		return digestFileBySHA(filePath, SHA1);
	}
	/**
	 * 使用SHA-256算法来计算某个文件的SHA值
	 * @param filePath
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA256(String filePath) throws IOException{
		return digestFileBySHA(filePath, SHA256);
	}
	/**
	 * 使用SHA-512算法来计算某个文件的SHA值
	 * @param filePath
	 * @return SHA 16进制字串
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digestFileBySHA512(String filePath) throws IOException{
		return digestFileBySHA(filePath, SHA512);
	}
}
