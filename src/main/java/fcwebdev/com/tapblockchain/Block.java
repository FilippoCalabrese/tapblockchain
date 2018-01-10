package fcwebdev.com.tapblockchain;

public interface Block {

	String getBlockTimestamp();

	boolean isHashValid();

	int getIndex();

	void setIndex(int index);

	int getNoun();

	void increaseNoun();

	String getPreviousHash();

	String getData();

	void setPreviousHash(String previousHash);

	String getHash();

	void generateHash();

}