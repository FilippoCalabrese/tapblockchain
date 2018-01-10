package fcwebdev.com.tapblockchain;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

public class BlockTest {
	
	private Block testBlock;
	
	@Before
	public void init() {
		String[] algorithm = {"SHA-256", "UTF-8"};
		testBlock = new Block("1/1/2018", "some data", algorithm);
	}
	
	@Test
	public void testIndexGetter() {
		assertEquals(0, testBlock.getIndex());
	}

	@Test
	public void testIndexSetter() {
		testBlock.setIndex(1);
		assertEquals(1, testBlock.getIndex());
	}
	
	@Test
	public void testDefaultNounValue() {
		assertEquals(0, testBlock.getNoun());
	}
	
	@Test
	public void testNounIncrease() {
		int previousNoun = testBlock.getNoun();
		testBlock.increaseNoun();
		assertEquals(previousNoun+1, testBlock.getNoun());
	}
	
	@Test
	public void testDataGetter() {
		assertEquals("some data", testBlock.getData());
	}
	
	@Test
	public void testHashValidation() {
		assertNotEquals("", testBlock.getHash());
	}
	
	@Test
	public void testNoSuchAlgorithmExceptionHandling() {
		String[] algorithm = {"", "UTF-8"};
		validateBlockHashWithCustomAlgorithm(false, algorithm);
	}
	
	@Test
	public void testUnsupportedEncodingExceptionHandling() {
		String[] algorithm = {"SHA-256", "fake encoding"};
		validateBlockHashWithCustomAlgorithm(false, algorithm);
	}
	
	@Test
	public void testDefaultPreviousHashGetter() {
		assertEquals("", testBlock.getPreviousHash());
	}
	
	@Test
	public void assertCorrectHashNotNull() {
		assertNotNull(testBlock.getHash());
	}
	@Test
	public void testPreviousHashSetter() {
		testBlock.setPreviousHash("1234");
		assertEquals("1234", testBlock.getPreviousHash());
	}
	
	@Test
	public void testCorrectBlockHashValidation() {
		assertEquals(true, testBlock.isHashValid());
	}
	
	private void validateBlockHashWithCustomAlgorithm(boolean supposed, String[] algorithm) {
		testBlock = new Block("1/1/2018", "some data", algorithm);
		assertEquals(supposed, testBlock.isHashValid());
	}

}
