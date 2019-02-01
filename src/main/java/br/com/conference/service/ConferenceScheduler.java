package br.com.conference.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import br.com.conference.repository.EventRepository;
import br.com.conference.value.Bucket;
import br.com.conference.value.Conference;
import br.com.conference.value.Event;
import br.com.conference.value.Track;

public final class ConferenceScheduler {

	private static final int LUNCH_DURATION = 60;
	private static final int AFTERNOON_DURATION = 240;
	private static final int MORNING_START_TIME = 540;
	private static final int MORNING_DURATION = 180;
	private static final int LUNCH_START_TIME = MORNING_START_TIME + MORNING_DURATION;
	private static final int AFTERNOON_SLOT_START_TIME = LUNCH_START_TIME + LUNCH_DURATION;
	private static final String LUNCH_NAME = "Lunch";
	private static final String NETWORKING_EVENT_NAME = "Networking Event";
	private static final int NETWORKING_EVENT_DURATION = 60;
	private static final int NETWORKING_EVENT_MIN_START_TIME = 1020;
	public static final int MAX_DURATION_EVENT = Math.max(MORNING_DURATION, AFTERNOON_DURATION);

	private ConferenceScheduler() {
	}

	public static ConferenceScheduler build() {
		return new ConferenceScheduler();
	}
	
	public Conference schedule(final String fileName) throws IOException {
		List<Event> events = EventRepository.build().getEvents(fileName);
		return ConferenceScheduler.build().scheduleAll(events);
	}
	
	public Conference schedule(final List<String> texts) throws IOException {
		List<Event> events = EventRepository.build().getEvents(texts);
		return ConferenceScheduler.build().scheduleAll(events);
	}

	public Conference scheduleAll(final List<Event> events) {
		Conference conference = Conference.build();
		events.sort((p1, p2) -> (p1.getDuration() - p2.getDuration()) * -1);
		while (!events.isEmpty()) {
			conference.addTrack(scheduleTrack(conference, events));
		}
		return conference;
	}

	private Track scheduleTrack(final Conference conference, final List<Event> events) {
		Bucket morningBucket = Bucket.build(MORNING_DURATION, MORNING_START_TIME);
		fillBucketWithEvents(morningBucket, events);
		Bucket lunchBucket = Bucket.build(LUNCH_DURATION, LUNCH_START_TIME);
		lunchBucket.addEvent(Event.build(LUNCH_NAME, LUNCH_DURATION));
		Bucket afternoonBucket = Bucket.build(AFTERNOON_DURATION, AFTERNOON_SLOT_START_TIME);
		fillBucketWithEvents(afternoonBucket, events);
		Event networkingEvent = Event.build(NETWORKING_EVENT_NAME, NETWORKING_EVENT_DURATION);
		Bucket networkingBucket = Bucket.build(networkingEvent.getDuration(), NETWORKING_EVENT_MIN_START_TIME);
		networkingBucket.addEvent(networkingEvent);
		afternoonBucket.addNewBucket(networkingBucket);
		Track track = Track.build();
		track.addBucket(morningBucket);
		track.addBucket(lunchBucket);
		track.addBucket(afternoonBucket);
		return track;
	}

	private void fillBucketWithEvents(final Bucket bucket, final List<Event> events) {
		Iterator<Event> iter = events.iterator();
		while (iter.hasNext()) {
			Event event = iter.next();
			if (bucket.hasRoomFor(event)) {
				bucket.addEvent(event);
				iter.remove();
			}
		}
	}

}
