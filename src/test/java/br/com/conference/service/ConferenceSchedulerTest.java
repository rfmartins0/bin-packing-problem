package br.com.conference.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.conference.repository.EventRepository;
import br.com.conference.value.Bucket;
import br.com.conference.value.Conference;
import br.com.conference.value.Event;
import br.com.conference.value.Track;

public class ConferenceSchedulerTest {

	private List<String> texts = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		String line1 = "Writing Fast Tests Against Enterprise Rails 60min";
		String line2 = "Overdoing it in Python 45min";
		String line3 = "Lua for the Masses 30min";
		String line4 = "Ruby Errors from Mismatched Gem Versions 45min";
		String line5 = "Common Ruby Errors 45min";
		String line6 = "Rails for Python Developers lightning";
		String line7 = "Communicating Over Distance 60min";
		String line8 = "Accounting-Driven Development 45min";
		String line9 = "Woah 30min";
		String line10 = "Sit Down and Write 30min";
		String line11 = "Pair Programming vs Noise 45min";
		String line12 = "Rails Magic 60min";
		String line13 = "Ruby on Rails: Why We Should Move On 60min";
		String line14 = "Clojure Ate Scala (on my project) 45min";
		String line15 = "Programming in the Boondocks of Seattle 30min";
		String line16 = "Ruby vs. Clojure for Back-End Development 30min";
		String line17 = "Ruby on Rails Legacy App Maintenance 60min";
		String line18 = "A World Without HackerNews 30min";
		String line19 = "User Interface CSS in Rails Apps 30min";
		texts.add(line1);
		texts.add(line2);
		texts.add(line3);
		texts.add(line4);
		texts.add(line5);
		texts.add(line6);
		texts.add(line7);
		texts.add(line8);
		texts.add(line9);
		texts.add(line10);
		texts.add(line11);
		texts.add(line12);
		texts.add(line13);
		texts.add(line14);
		texts.add(line15);
		texts.add(line16);
		texts.add(line17);
		texts.add(line18);
		texts.add(line19);
	}

	@Test
	public void testTracksOrder() {
		List<Event> events = EventRepository.build().getEvents(texts);
		events.sort((p1, p2) -> (p1.getDuration() - p2.getDuration()));
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		assertEquals(conference.getNumberOfTracks(), 2);
	}
	
	@Test
	public void testTracksInverseOrder() {
		List<Event> events = EventRepository.build().getEvents(texts);
		events.sort((p1, p2) -> (p1.getDuration() - p2.getDuration()) *-1);
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		assertEquals(conference.getNumberOfTracks(), 2);
	}

	@Test
	public void testTracks1() {
		List<Event> events = EventRepository.build().getEvents(texts);
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		assertEquals(conference.getNumberOfTracks(), 2);
	}

	@Test
	public void testTracks2() {
		List<String> textsModified = new ArrayList<String>(texts);
		textsModified.add("Maratone 180min");
		List<Event> events = EventRepository.build().getEvents(textsModified);
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		assertEquals(conference.getNumberOfTracks(), 3);

	}

	@Test
	public void testTracks3() {
		List<String> textsModified = new ArrayList<String>(texts);
		textsModified.add("Marathon 240min");
		textsModified.add("Good Morning 180min");
		List<Event> events = EventRepository.build().getEvents(textsModified);
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		assertEquals(conference.getNumberOfTracks(), 3);

	}

	@Test
	public void testNumberOfBuckets() {
		List<Event> events = EventRepository.build().getEvents(texts);
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		int i = 0;
		for (Track tracks : conference.getTracks()) {
			i = i + tracks.getNumberOfBuckets();
		}
		assertEquals(i, 6);
	}

	@Test
	public void testNumberOfEvents() {
		List<Event> events = EventRepository.build().getEvents(texts);
		Conference conference = ConferenceScheduler.build().scheduleAll(events);
		int i = 0;
		for (Track tracks : conference.getTracks()) {
			for (Bucket bucket : tracks.getBuckets()) {
				i = i + bucket.getNumberOfEvents();
			}
		}
		assertEquals(i, 21);
	}

}
