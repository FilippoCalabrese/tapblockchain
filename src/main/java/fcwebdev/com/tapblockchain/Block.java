package fcwebdev.com.tapblockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	private String data;
	private String timestamp;
	private String previousHash;
	private String hash;
	
	private String[] algorithm;
	private boolean hasValidHash;
	private int index;
	private int noun = 0;

	public Block(String timestamp, String data, String[] algorithm) {
		this.timestamp = timestamp;
		this.data = data;
		this.algorithm = algorithm;
		this.previousHash = "";
		generateHash();
	}
	
	public boolean isHashValid() {
		return hasValidHash;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getNoun() {
		return this.noun;
	}

	public void increaseNoun() {
		this.noun = noun + 1;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public String getData() {
		return this.data;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getHash() {
		return hash;
	}

	public void generateHash() {
		try {
			this.hash = generate(this.index + this.timestamp + this.data + this.noun, algorithm);
		} catch (NoSuchAlgorithmException e) {
			declareBlockInvalidHash("error: Invalid algorithm");
		} catch (UnsupportedEncodingException e) {
			declareBlockInvalidHash("error: unsupported encoding");
		}
	}

	private void declareBlockInvalidHash(String message) {
		this.hash = message;
		this.hasValidHash = false;
	}

	private String generate(String input, String[] algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance(algorithm[0]);
		md.reset();
		
		byte[] buffer = input.getBytes(algorithm[1]);
		md.update(buffer);
		
		byte[] digest = md.digest();

		String hexStr = "";
		for (int i = 0; i < digest.length; i++) {
			hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
		}
		this.hasValidHash = true;
		return hexStr;
	}

}
