package fcwebdev.com.tapblockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StandardBlock implements Block {
	private String data;
	private String timestamp;
	private String previousHash;
	private String hash;
	
	private String[] algorithm;
	private boolean hasValidHash = true;
	private int index;
	private int noun = 0;

	public StandardBlock(String data, String[] algorithm) {
		this.timestamp = calculateTimeStamp();
		this.data = data;
		this.algorithm = algorithm;
		this.previousHash = "";
		generateHash();
	}
	
	private String calculateTimeStamp() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = Calendar.getInstance().getTime();        
		String reportDate = df.format(today);
		return reportDate;
	}
	
	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#getBlockTimestamp()
	 */
	@Override
	public String getBlockTimestamp() {
		return this.timestamp;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#isHashValid()
	 */
	@Override
	public boolean isHashValid() {
		return hasValidHash;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#getIndex()
	 */
	@Override
	public int getIndex() {
		return this.index;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#setIndex(int)
	 */
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#getNoun()
	 */
	@Override
	public int getNoun() {
		return this.noun;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#increaseNoun()
	 */
	@Override
	public void increaseNoun() {
		this.noun = noun + 1;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#getPreviousHash()
	 */
	@Override
	public String getPreviousHash() {
		return previousHash;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#getData()
	 */
	@Override
	public String getData() {
		return this.data;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#setPreviousHash(java.lang.String)
	 */
	@Override
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#getHash()
	 */
	@Override
	public String getHash() {
		return hash;
	}

	/* (non-Javadoc)
	 * @see fcwebdev.com.tapblockchain.BlockInterface#generateHash()
	 */
	@Override
	public void generateHash() {
		try {
			this.hash = generate(this.index + this.timestamp + this.data + this.noun, algorithm);
		} catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
			declareBlockInvalidHash();
		}
	}

	private void declareBlockInvalidHash() {
		this.hash = null;
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
