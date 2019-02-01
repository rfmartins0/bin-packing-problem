package br.com.conference.main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.conference.repository.EventUtil;
import br.com.conference.service.ConferenceScheduler;
import br.com.conference.value.Bucket;
import br.com.conference.value.Conference;
import br.com.conference.value.Track;

public class ConferenceMain {

	private static final String TRACK = "Track ";
	private static final String COLON = ":";
	private static final String NEW_LINE = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException {
		System.out.println("Conference Track Management");
		System.out.println("Press 1 for type all datas");
		System.out.println("Press 2 for input file content datas");
		// we can do this because the scanner has "autocloser". Scanner will be
		// close
		try (Scanner scanner = new Scanner(System.in)) {
			int number = scanner.nextInt();
			if (number == 1) {
				inputType();
			} else if (number == 2) {
				inputFile();
			}
			System.out.println("No options chosen");
		}
	}

	public static void inputType() throws IOException {
		System.out.println("Type all the data.");
		System.out.println("When finished, type \"ready\" (without the quotation marks)");
		List<String> texts = new ArrayList<String>();
		String line = "";
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				if (!line.isEmpty()) {
					EventUtil.checkContend(line);
					texts.add(line);
				}
				line = scanner.nextLine();
			} while (!line.equalsIgnoreCase("ready"));
		}
		Conference conference = ConferenceScheduler.build().schedule(texts);
		show(conference);
	}

	public static void inputFile() throws IOException {
		System.out.println("Please, put the file containing the data in " + Paths.get("").toAbsolutePath());
		System.out.println("Please, type the name of file:");
		String fileName;
		try (Scanner scanner = new Scanner(System.in)) {
			fileName = scanner.nextLine();
		}
		Conference conference = ConferenceScheduler.build().schedule(fileName);
		show(conference);
	}

	public static void show(final Conference conference) {
		System.out.println("------------Result------------");
		StringBuilder str = new StringBuilder();
		for (int x = 0; x < conference.getNumberOfTracks(); x++) {
			str.append(TRACK + (x + 1) + COLON + NEW_LINE);
			Track track = conference.getTracks().get(x);
			str.append(displayTrack(track));
			str.append(NEW_LINE);
		}
		System.out.println(str.toString());
	}

	public static String displayTrack(final Track track) {
		StringBuilder str = new StringBuilder();
		for (Bucket bucket : track.getBuckets()) {
			str.append(bucket);
		}
		return str.toString();
	}

}
