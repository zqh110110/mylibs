package com.kfd.common;

import java.security.MessageDigest;

/**
 * MD5 加密
 * 
 * @author 朱继洋 2013-3-22 QQ 7617812
 */
public class MD5Encoder {
	private static final char[] HEX_DIGITS;

	static {
		char[] arrayOfChar = new char[36];
		arrayOfChar[0] = 48;
		arrayOfChar[1] = 49;
		arrayOfChar[2] = 50;
		arrayOfChar[3] = 51;
		arrayOfChar[4] = 52;
		arrayOfChar[5] = 53;
		arrayOfChar[6] = 54;
		arrayOfChar[7] = 55;
		arrayOfChar[8] = 56;
		arrayOfChar[9] = 57;
		arrayOfChar[10] = 65;
		arrayOfChar[11] = 66;
		arrayOfChar[12] = 67;
		arrayOfChar[13] = 68;
		arrayOfChar[14] = 69;
		arrayOfChar[15] = 70;
		arrayOfChar[16] = 71;
		arrayOfChar[17] = 72;
		arrayOfChar[18] = 73;
		arrayOfChar[19] = 74;
		arrayOfChar[20] = 75;
		arrayOfChar[21] = 76;
		arrayOfChar[22] = 77;
		arrayOfChar[23] = 78;
		arrayOfChar[24] = 79;
		arrayOfChar[25] = 80;
		arrayOfChar[26] = 81;
		arrayOfChar[27] = 82;
		arrayOfChar[28] = 83;
		arrayOfChar[29] = 84;
		arrayOfChar[30] = 85;
		arrayOfChar[31] = 86;
		arrayOfChar[32] = 87;
		arrayOfChar[33] = 88;
		arrayOfChar[34] = 89;
		arrayOfChar[35] = 90;
		HEX_DIGITS = arrayOfChar;
	}

	public static String toHexString(byte[] paramArrayOfByte) {
		StringBuilder localStringBuilder = new StringBuilder(
				2 * paramArrayOfByte.length);
		for (int i = 0; i < paramArrayOfByte.length; i++) {
			localStringBuilder
					.append(HEX_DIGITS[((0xF0 & paramArrayOfByte[i]) >>> 4)]);
			localStringBuilder.append(HEX_DIGITS[(0xF & paramArrayOfByte[i])]);
		}
		return localStringBuilder.toString();
	}

	public static String toMd5(byte[] paramArrayOfByte) {
		Object localObject1 = null;
		try {
			localObject1 = MessageDigest.getInstance("MD5");
			((MessageDigest) localObject1).update(paramArrayOfByte);
			localObject1 = toHexString(((MessageDigest) localObject1).digest());

			return (String) localObject1;
		} catch (Exception localObject2) {
			// while (true)
			{
				LogUtils.e("test", "", localObject2);
				// Object localObject2 = null;
			}
		}
		return (String) localObject1;
	}
}
