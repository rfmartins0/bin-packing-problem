package br.com.conference.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import br.com.conference.repository.EventUtil;

public class EventUtilTest {

	@Test
	public void testValidLines() {
		List<String> texts = new ArrayList<String>();
		texts.add("Overdoing it in Python 45min");
		texts.add("Lua for the Masses 30min");
		texts.add("Common Ruby Errors 45min");
		EventUtil.checkFileContend(texts);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidValidLines() {
		List<String> texts = new ArrayList<String>();
		texts.add("Overdoing it in Python");
		texts.add("Lua for the Masses 30min");
		texts.add("Common Ruby Errors 45min");
		EventUtil.checkFileContend(texts);
	}

	@Test
	public void testValidLinesWithLightning() {
		List<String> texts = new ArrayList<String>();
		texts.add("Overdoing it in Python 45min");
		texts.add("Lua for the Masses 30min");
		texts.add("Rails for Python Developers lightning");
		EventUtil.checkFileContend(texts);
	}

	@Test
	public void testListEmpty() {
		List<String> texts = new ArrayList<String>();
		EventUtil.checkFileContend(texts);
	}

	@Test(expected = IOException.class)
	public void testCheckFileExist() throws IOException {
		EventUtil.checkFileExist("C:\\");
	}

	@Test
	public void testEventParseMin() {
		int time = EventUtil.getTime("Learning Test 600min");
		assertEquals(time, 600);
	}

	@Test
	public void testEventParseLightning() {
		int time = EventUtil.getTime("Learning Test lightning");
		assertEquals(time, 5);
	}

	@Test
	public void testEventParseCheckTime() {
		EventUtil.checkTime(100);
	}

	@Test(expected = RuntimeException.class)
	public void testEventParseCheckNegativeTime() {
		EventUtil.checkTime(-100);
	}

}
