package com.vten.gedeon.security.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonRuntimeException;
import com.vten.gedeon.security.file.configuration.FileSecurityProperties;

import lombok.NoArgsConstructor;

@Service
@EnableAutoConfiguration
@NoArgsConstructor
public class GedeonEncryptionService {

	private static final String ENCRYPTION_PASSWORD = "ENCRYPTION_PASSWORD";
	private static final String KEYSTORE_KEY_ENCRYPTION = "GEDEON_ENCRYPTION_KEY";

	@Autowired
	private FileSecurityProperties securityConfig;

	private KeyStore store;

	/**
	 * Initialize keystore and secret key for document encryption
	 */
	@PostConstruct
	public void init() {
		// load text encryptor to decrypt keystore pass from property
		AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
		textEncryptor.setPassword(System.getProperty(ENCRYPTION_PASSWORD));

		// Create keystore file if doesn't exist
		File keystoreFile = new File(securityConfig.getKeystore().getLocation());
		if (!keystoreFile.exists()) {
			try {
				Files.createFile(keystoreFile.toPath());

				KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
				ks.load(null, textEncryptor.decrypt(securityConfig.getKeystore().getKey()).toCharArray());

				// Store away the keystore.
				try (FileOutputStream fos = new FileOutputStream(keystoreFile)) {
					ks.store(fos, textEncryptor.decrypt(securityConfig.getKeystore().getKey()).toCharArray());
				}

			} catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
				throw new GedeonRuntimeException(GedeonErrorCode.OE0100, e);
			}
		}
		// Load keystore
		try (InputStream is = new FileInputStream(keystoreFile)) {
			store = KeyStore.getInstance(securityConfig.getKeystore().getType());
			store.load(is, textEncryptor.decrypt(securityConfig.getKeystore().getKey()).toCharArray());

			// Check if secret exists
			if (!store.isKeyEntry(KEYSTORE_KEY_ENCRYPTION)) {
				// if no, generate a new one
				KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(128);
				// store key to keystore
				store.setKeyEntry(ENCRYPTION_PASSWORD, keyGenerator.generateKey(),
						System.getProperty(ENCRYPTION_PASSWORD).toCharArray(), null);
			}
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE0100, e);
		}
	}

	/**
	 * Get encrypt or decrypt cipher
	 * 
	 * @param mode Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
	 * @return new cipher instance initialized with application encryption key
	 */
	private Cipher getCipher(int mode) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(securityConfig.getAlgorithm());
			cipher.init(mode,
					store.getKey(KEYSTORE_KEY_ENCRYPTION, System.getProperty(ENCRYPTION_PASSWORD).toCharArray()),
					generateIv());
			return cipher;
		} catch (InvalidKeyException | UnrecoverableKeyException | InvalidAlgorithmParameterException
				| KeyStoreException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new GedeonRuntimeException(GedeonErrorCode.OE0101, e);
		}
	}

	/**
	 * Generate new salt for encryption/decryption
	 * 
	 * @return new random IvParameterSpec
	 */
	private IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}

	/**
	 * Transform the given inputstream to a decrypted input stream
	 * 
	 * @param stream
	 * @return a cipherinputstream instance initialized with appropriate key
	 */
	public InputStream decodeInputStream(InputStream stream) {
		return new CipherInputStream(stream, getCipher(Cipher.DECRYPT_MODE));
	}

	/**
	 * Transform the given outputstream to an encrpyt stream
	 * 
	 * @param stream outputstream to convert
	 * @return a cipheroutputstream instance initialized with appropriate key
	 */
	public OutputStream decodeOutputStream(OutputStream stream) {
		return new CipherOutputStream(stream, getCipher(Cipher.ENCRYPT_MODE));
	}

}
