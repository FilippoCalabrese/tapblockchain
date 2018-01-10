package fcwebdev.com.tapblockchain;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

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
	public void testBlockListIsInitizlized() {
		assertNotNull(instance.getBlocksList());
	}

	@Test
	public void testDifficultyGetterWithInitialValue() {
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
	
	@Test
	//Questo test ha senso se i componenti sono mokkati?
	public void testCorrectRelationBetweenFollowingBlocks() {
		insertDummyBlock();
		Block first = instance.getBlockFromIndex(1);
		String correctHash = first.getHash();
		
		insertDummyBlock();
		Block secondBlock = instance.getBlockFromIndex(2);
		String secondBlockHash = secondBlock.getPreviousHash();
		assertEquals(correctHash, secondBlockHash);
		
	}
	
	@Test
	public void verifyDifficultyCheckFailOnWrongString() {
		assertEquals(false, instance.checkStringDifficulty("1234"));
	}
	
	@Test
	public void verifyDifficultyCheckSuccessOnCorrectString() {
		assertEquals(true, instance.checkStringDifficulty("0123"));
	}
	
	@Test
	public void verifyDifficultyCheckSuccessOnDIfficultyTwo() {
		instance.increaseDifficulty();
		assertEquals(true, instance.checkStringDifficulty("00123"));
	}

// JaCoCo bug with this test
	
//	@Test(expected = Exception.class)
//	public void testGetFromIndexWithWrongIndex() throws Exception {
//		int i = 0;
//		Block wrongBlock = instance.getBlockFromIndex(2);
//	}

	@Test
	public void testAddNewBlock() {
		instance.addNewBlock(generateMockedBlock());
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
		Block mockedBlock = generateMockedBlock();
		instance.addNewBlock(mockedBlock);
		Block inserted = instance.getBlockFromIndex(1);
		return inserted;
	}
	
	private Block generateMockedBlock() {
		Block mockedBlock = mock(Block.class);
		when(mockedBlock.getHash()).thenReturn("00123");
		when(mockedBlock.getPreviousHash()).thenReturn("00123");
		return mockedBlock;
	}
	
	private void insertMockedBlockForAddingTest() {
		Block mockedBlock = mock(Block.class);
		when(mockedBlock.getHash()).thenReturn("123").thenReturn("0123");
		instance.addNewBlock(mockedBlock);
	}
	
	@Test
	public void testAddBlock() {
		insertMockedBlockForAddingTest();
		assertEquals(2, instance.getBlocksList().size());
	}

}
