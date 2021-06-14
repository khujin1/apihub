import org.apache.commons.codec.DecoderException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 데이터 암/복호화 해싱 처리
 */
public class CryptoUtil {
	// 암호화 변환 규칙(암호화알고리즘/운용모드/패딩)
	private static String transformationRule = "AES/CBC/PKCS5Padding";
	private static String IV = "ABCDEFGHIJKLMNOP";

	/**
	 * AES 복호화 메소드
	 * @param cipherText
	 * @param encryptionKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
		// 복호화된 텍스트
		String decryptedText = null;
		// 암호화 객체 생성
		Cipher cipher = Cipher.getInstance(transformationRule);
		// Initialization Vector 객체 생성
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
		// AES 알고리즘에 사용할 비밀키(SecretKey) 를 생성
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		// 암호화 객체 '복호화 모드'로 초기화
		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
		// 복호화 수행
		byte[] decryptedByteArray = cipher.doFinal(cipherText);
		decryptedText = new String(decryptedByteArray, "UTF-8");
		return decryptedText;
	}

	/**
	 * AES 복호화 메소드
	 *
	 * @param cipherText
	 * @param encryptionKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cipherText, String encryptionKey) throws Exception {
		return decrypt(CryptoUtil.decodeHex(cipherText.toCharArray()), encryptionKey);
	}

	/**
	 * Converts an array of characters representing hexadecimal values into an array
	 * of bytes of those same values. The returned array will be half the length of
	 * the passed array, as it takes two characters to represent any given byte. An
	 * exception is thrown if the passed char array has an odd number of elements.
	 *
	 * @param data An array of characters containing hexadecimal digits
	 * @return A byte array containing binary data decoded from the supplied char
	 *         array.
	 * @throws DecoderException Thrown if an odd number or illegal of characters is
	 *                          supplied
	 */
	public static byte[] decodeHex(char[] data) {
		int len = data.length;
		if ((len & 0x01) != 0) {
		}
		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	/**
	 * Converts a hexadecimal character to an integer.
	 *
	 * @param ch    A character to convert to an integer digit
	 * @param index The index of the character in the source
	 * @return An integer
	 * @throws DecoderException Thrown if ch is an illegal hex character
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
		}
		return digit;
	}

}
