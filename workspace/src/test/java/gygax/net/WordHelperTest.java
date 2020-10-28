package gygax.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordHelperTest {

	@Test
	public void testDontWithApostrophe() {
		assertEquals("don't", WordHelper.cleanse("don't"));
	}
	
	@Test
	public void testAmpersand() {
		assertEquals("&", WordHelper.cleanse("&"));
	}
	
	@Test
	public void testFullstop() {
		assertEquals("morning", WordHelper.cleanse("morning."));
	}
	
	@Test
	public void testComma() {
		assertEquals("However", WordHelper.cleanse("However,"));
	}
	
	@Test
	public void testAsterisks() {
		assertEquals("", WordHelper.cleanse("**********"));
	}
	
	@Test
	public void testEllipses() {
		assertEquals("", WordHelper.cleanse("..."));
	}
}
