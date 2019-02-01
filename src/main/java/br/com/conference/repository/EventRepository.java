package br.com.conference.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import br.com.conference.value.Event;

public class EventRepository {

	private EventRepository() {
	}

	public static EventRepository build() {
		return new EventRepository();
	}

	public List<Event> getEvents(final String fileName) throws IOException {
		List<String> texts = getEventsFromFile(fileName);
		return getEvents(texts);
	}

	public List<Event> getEvents(final List<String> lines) {
		List<Event> events = new ArrayList<Event>();
		for (String line : lines) {
			int duration = EventUtil.getTime(line);
			EventUtil.checkTime(duration);
			events.add(Event.build(line, EventUtil.getTime(line)));
		}
		return events;
	}

	public List<String> getEventsFromFile(final String fileName) throws IOException {
		EventUtil.checkFileExist(fileName);
		List<String> lines = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(lines::add);
		} catch (IOException e) {
			System.err.println("Cannot read from input file.");
			e.printStackTrace();
			throw e;
		}
		EventUtil.checkFileContend(lines);
		return lines;
	}

}
