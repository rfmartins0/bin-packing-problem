package br.com.conference.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.conference.service.ConferenceScheduler;

public class EventUtil {

	private final static int LIGHTNING = 5;
	private final static int HUM = 1;

	private EventUtil() {

	}

	public static void checkTime(final int timeDuration) {
		if (timeDuration > ConferenceScheduler.MAX_DURATION_EVENT) {
			throw new RuntimeException("The event shouldn't be bigger than " + ConferenceScheduler.MAX_DURATION_EVENT);
		}
		if (timeDuration < HUM) {
			throw new RuntimeException("The event shouldn't be smaller than " + HUM + " minute");
		}
	}

	public static int getTime(final String text) {
		// Allows import a negative number to alert with the correct message
		Pattern pattern = Pattern.compile("(-?[0-9]+)(min)$");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		return LIGHTNING;
	}

	public static void checkFileContend(final List<String> texts) {
		for (String text : texts) {
			checkContend(text);
		}
	}
	
	public static void checkContend(final String text) {
		Pattern pattern = Pattern.compile("([0-9]min|lightning){1}$");
		Matcher matcher = pattern.matcher(text);
		if (!matcher.find()) {
			throw new IllegalArgumentException("The input has a invalid inputs. Please check.");
		}
	}


	public static void checkFileExist(final String fileName) throws IOException {
		if (!new File(fileName).isFile()) {
			throw new IOException("The file not exist. Please put the file in " + Paths.get("").toAbsolutePath() + "\\"
					+ fileName);
		}
	}
}
