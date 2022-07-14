package com.shinhan.market.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class AES256Cipher {
	private static volatile AES256Cipher INSTANCE;
	static final String secretKey = "shinan147!902753";
	static String IV = "";

	public static AES256Cipher getInstatnce() {
		if (INSTANCE == null) {
			synchronized (AES256Cipher.class) {
				if (INSTANCE == null)
					INSTANCE = new AES256Cipher();
			}
		}
		return INSTANCE;
	}

	private AES256Cipher() {
		IV = "shinan147!902753".substring(0, 16);
	}

	public static String AES_Encode(String str) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(
				"shinan147!902753".getBytes(), "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(1, skeySpec);
		byte[] encrypted = c.doFinal(str.getBytes());
		String enStr = new String(Base64.encode(encrypted));

		return enStr;
	}

	public static String AES_Decode(String str) throws Exception {
		byte[] keyData = "shinan147!902753".getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(2, secureKey);

		byte[] byteStr = Base64.decode(str.getBytes());

		return new String(c.doFinal(byteStr), "UTF-8");
	}

	public static void main(String[] args) {
		if ((args == null) || (args.length < 1)) {
			System.err.println("Argument Error!!");
			System.err.println("Usage Argument 1 : Password");
			System.exit(-1);
		}
		try {
			AES256Cipher a256 = getInstatnce();
			String id = args[0];
			String enId = AES_Encode(id);
//			System.out.println("Security Password:" + enId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
