package fcwebdev.com.tapblockchain;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;

public class BlockChainTest {

	private BlockChain instance;

	@Before
	public void init() {
		this.instance = BlockChain.getInstance();
		instance.resetInstance();
	}

	@Test
	public void testGetInstanceCorrectClass() {
		assertTrue(BlockChain.getInstance() instanceof BlockChain);
	}

	@Test
	public void testGetInstanceNotNull() {
		assertNotNull(BlockChain.getInstance());
	}

	@Test
	public void testBlockListGetter() {
		assertTrue(instance.getBlocksList() instanceof LinkedList);
	}

	@Test
	public void testDifficultyGetter() {
		assertEquals(1, instance.getDifficulty());
	}

	@Test
	public void testDifficultyIncrease() {
		int initialDifficulty = instance.getDifficulty();
		instance.increaseDifficulty();
		assertEquals(initialDifficulty + 1, instance.getDifficulty());
	}

	@Test
	public void testPresenceOfGenesisBlock() throws Exception {
		Block genesis = instance.getBlockFromIndex(0);
		assertEquals("GENESIS", genesis.getPreviousHash());
	}

// JaCoCo bug with this test
	
//	@Test(expected = Exception.class)
//	public void testGetFromIndexWithWrongIndex() throws Exception {
//		int i = 0;
//		Block wrongBlock = instance.getBlockFromIndex(2);
//	}

	@Test
	public void testAddNewBlock() {
		instance.addNewBlock("1/12/2018", "some fake data");
		assertEquals(2, instance.getBlocksList().size());
	}

	@Test
	public void testNewBlockHashDifficultyOne() {
		Block inserted = insertDummyBlock();
		assertEquals('0', inserted.getHash().charAt(0));
	}

	@Test
	public void testNewBlockHashDifficultyTwo() throws Exception {
		instance.increaseDifficulty();
		Block inserted = insertDummyBlock();
		assertEquals("00", inserted.getHash().substring(0, 2));
	}

	private Block insertDummyBlock() {
		instance.addNewBlock("1/12/2018", "some fake data");
		Block inserted = instance.getBlockFromIndex(1);
		return inserted;
	}

}
