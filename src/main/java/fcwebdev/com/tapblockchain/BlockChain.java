package fcwebdev.com.tapblockchain;

import java.util.LinkedList;
import java.util.List;

public class BlockChain {
	private int difficulty;
	private static BlockChain instance = null;
	private List<Block> blocks;
	private static final String[] ALGORITHM = { "SHA-256", "UTF-8" };

	private BlockChain() {
		blocks = new LinkedList();
		this.difficulty = 1;
		createGenesisBlock();
	}

	public static BlockChain getInstance() {
		if (instance == null) {
			instance = new BlockChain();
		}
		return instance;
	}

	public void resetInstance() {
		instance = new BlockChain();
	}

	public List getBlocksList() {
		return this.blocks;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void increaseDifficulty() {
		this.difficulty = this.difficulty + 1;
	}

	private void createGenesisBlock() {
		Block genesisBlock = new Block("1/12/2017", "Genesis Block", ALGORITHM);
		genesisBlock.setIndex(0);
		genesisBlock.setPreviousHash("GENESIS");
		blocks.add(genesisBlock);
	}

	public Block getBlockFromIndex(int index) {
		return blocks.get(index);
	}

	public void addNewBlock(String timestamp, String data) {
		Block newBlock = new Block(timestamp, data, ALGORITHM);
		newBlock.setPreviousHash(blocks.get(blocks.size() - 1).getHash());
		newBlock.setIndex(blocks.size());

		while (!checkStringDifficulty(newBlock.getHash())) {
			newBlock.increaseNoun();
			newBlock.generateHash();
		}
		blocks.add(newBlock);
	}

	private boolean checkStringDifficulty(String hash) {
		boolean flag = true;
		for (int i = 0; i < difficulty; i++) {
			if (hash.charAt(i) != '0') {
				flag = false;
				break;
			}
		}
		return flag;
	}
}
