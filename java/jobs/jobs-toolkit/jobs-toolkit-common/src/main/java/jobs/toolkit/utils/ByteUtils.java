package jobs.toolkit.utils;

public class ByteUtils {
	
	private final static int BYTEMASK = 0xFF;
	
	private static int encodeIntegerBigEndian(byte[] dst, long val, int offset, int size){
		for(int i = 0; i < size; i++){
			dst[offset++] =  (byte) (val >> ((size - i - 1) * Byte.SIZE) & BYTEMASK);
		}
		return offset;
	}
	private static long decodeIntegerBigEndian(byte[] src, int offset, int size){
		long result = 0;
		for(int i = 0; i < size; i++){
			result = (result << Byte.SIZE) | (src[offset + i] & BYTEMASK);
		}
		return result;
	}
	/**
	 * 将整数val使用高位优先的字节顺序编码成字节序列, 并填充到目标字节数组中offset开始的位置
	 * 如果offset + 整数的字节长度超过dst的length, 会抛出数组下标越界异常
	 * @param val 需要编码的整数
	 * @param dst 目标字节数组
	 * @param offset 填充目标字节数组的开始下标
	 * @return
	 */
	public static int encodeIntBigEndian(int val, byte[] dst, int offset){
		return encodeIntegerBigEndian(dst, val, offset, Integer.BYTES);
	}
	/**
	 * 将长整数val使用高位优先的字节顺序编码成字节序列, 并填充到目标字节数组中offset开始的位置
	 * 如果offset + 整数的字节长度超过dst的length, 会抛出数组下标越界异常
	 * @param val 需要编码的整数
	 * @param dst 目标字节数组
	 * @param offset 填充目标字节数组的开始下标
	 * @return
	 */
	public static int encodeLongBigEndian(long val, byte[] dst, int offset){
		return encodeIntegerBigEndian(dst, val, offset, Long.BYTES);
	}
	/**
	 * 将短整数val使用高位优先的字节顺序编码成字节序列, 并填充到目标字节数组中offset开始的位置
	 * 如果offset + 整数的字节长度超过dst的length, 会抛出数组下标越界异常
	 * @param val 需要编码的整数
	 * @param dst 目标字节数组
	 * @param offset 填充目标字节数组的开始下标
	 * @return
	 */
	public static int encodeShortBigEndian(short val, byte[] dst, int offset){
		return encodeIntegerBigEndian(dst, val, offset, Short.BYTES);
	}
	/**
	 * 将src中从offset开始的字节 序列解码成为一个32位整数
	 * @param src
	 * @param offset
	 * @return
	 */
	public static int decodeIntBigEndian(byte[] src, int offset){
		return (int) decodeIntegerBigEndian(src, offset, Integer.BYTES);
	}
	/**
	 * 将src中从offset开始的字节 序列解码成为一个32位整数
	 * @param src
	 * @param offset
	 * @return
	 */
	public static long decodeLongBigEndian(byte[] src, int offset){
		return decodeIntegerBigEndian(src, offset, Long.BYTES);
	}
	/**
	 * 将src中从offset开始的字节 序列解码成为一个32位整数
	 * @param src
	 * @param offset
	 * @return
	 */
	public static short decodeShortBigEndian(byte[] src, int offset){
		return (short) decodeIntegerBigEndian(src, offset, Short.BYTES);
	}
}
