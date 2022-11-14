package com.vten.gedeon.security.file;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;

public class GedeonEncryptionTest {

	@Test
	void test()
			throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
			BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException {
//
//		SecretKey key = GedeonEncryptionService.generateKey(128);
//		String algorithm = "AES/CBC/PKCS5Padding";
//		IvParameterSpec ivParameterSpec = GedeonEncryptionService.generateIv();
//		File inputFile = new File("src/test/resources/TEST.pdf");
//		File encryptedFile = new File("TEST.encrypted");
//		File decryptedFile = new File("document.decrypted");
//		File out = new File("OUT.pdf");
//
//		Cipher cipher = Cipher.getInstance(algorithm);
//		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
//
//		GedeonEncryptionService.encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
//		long start, end;
//		start = System.currentTimeMillis();
//		byte[] buffer = new byte[1024];
//		for (int i = 0; i < 100; i++) {
//			try (OutputStream os = new FileOutputStream(out, false); InputStream is = new FileInputStream(inputFile);) {
//
//				int length;
//				while ((length = is.read(buffer)) > 0) {
//					os.write(buffer, 0, length);
//				}
//			}
//
//		}
//		end = System.currentTimeMillis();
//		System.out.println(end - start);
//		start = System.currentTimeMillis();
//		for (int i = 0; i < 100; i++) {
//			try (OutputStream os = new FileOutputStream(out, false);
//					FileInputStream is = new FileInputStream("TEST.encrypted");
//					InputStream is2 = new CipherInputStream(is, cipher);) {
//
//				int length;
//				while ((length = is2.read(buffer)) > 0) {
//					os.write(buffer, 0, length);
//				}
//			}
//
//		}
//
//		// GedeonEncryption.decryptFile(algorithm, key, ivParameterSpec, encryptedFile,
//		// out);
//		end = System.currentTimeMillis();
//		System.out.println(end - start);
	}
}
