package fcwebdev.com.tapblockchain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AppTest {
	
	private App app;
	
	@Before
	public void initApp() {
		this.app = new App();
	}

	@Test
	public void test() {
		assertEquals(2, app.toBeTested());
	}
	
	@Test
	public void performImbardationTest() {
		assertEquals("ciao", "ciao");
	}

}
