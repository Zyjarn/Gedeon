package com.vten.gedeon.connector.content.accessor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.vten.gedeon.api.admin.Storage;
import com.vten.gedeon.exception.GedeonErrorCode;
import com.vten.gedeon.exception.GedeonIOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FTPContentAccessor implements GedeonContentAccessor {

	private FTPClient ftpClient;

	@Override
	public void init(Storage storage) throws GedeonIOException {
		// SET THESE TO MATCH YOUR FTP SERVER //
		String server = "www.server.com";
		int port = 21;
		String user = "username";
		String pass = "password";

		ftpClient = new FTPClient();

		try {
			ftpClient.connect(server, port);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				throw new IOException(String.format("Server response code : %d", replyCode));
			}
			if (!ftpClient.login(user, pass)) {
				throw new GedeonIOException(GedeonErrorCode.OE0141, storage.getId().toString());
			} else {
				log.debug("Connected to ftp server for storage {}", storage.getId().toString());
			}
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0140, e, storage.getId().toString());
		}

	}

	@Override
	public InputStream get(String path) throws GedeonIOException {
		try {
			// Retrieve file
			InputStream streamContent = ftpClient.retrieveFileStream(path);
			// Complete file retrieval
			if (!ftpClient.completePendingCommand()) {
				throw new IOException("Unable to complete transfer");
			}
			return streamContent;
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0143, e, path);
		}
	}

	@Override
	public void add(String path, InputStream content) throws GedeonIOException {
		try {
			ftpClient.storeFile(path, content);
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0144, e);
		}

	}

	@Override
	public void delete(String path) throws GedeonIOException {
		try {
			// send delete command
			if (!ftpClient.deleteFile(path)) {
				// delete did not complete
				throw new IOException("Unable to delete file.");
			}
		} catch (IOException e) {
			throw new GedeonIOException(GedeonErrorCode.OE0143, e, path);
		}

	}

	@Override
	public void close() throws GedeonIOException {
		// Check if client exist and connected
		if ((ftpClient != null) && ftpClient.isConnected()) {
			try {
				// then logout
				ftpClient.logout();
			} catch (IOException e) {
				throw new GedeonIOException(GedeonErrorCode.OE0142, e);
			}
		}

	}

}
