package br.com.conference.value;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
	private List<Event> events;
	private int remainingDuration;
	private final int startTime;
	private Bucket supplement;
	private static final int MAX_TIME_IN_MINUTES = 1440;
	private final static String NOT_ENOUGHT_ROOM_EVENT = "Not enough room in this bucket to fit the event:";

	private Bucket(final int duration, final int startTime) {
		this.remainingDuration = duration;
		this.startTime = startTime;
		this.events = new ArrayList<Event>();
	}

	public static Bucket build(final int duration, final int startTime) {
		return new Bucket(duration, startTime);
	}

	public int getNumberOfEvents() {
		return this.events.size();
	}

	private Bucket getSupplement() {
		return this.supplement;
	}

	private void setSupplement(Bucket supplement) {
		this.supplement = supplement;
	}

	public void addEvent(final Event event) {
		if (remainingDuration < event.getDuration()) {
			throw new IllegalStateException(NOT_ENOUGHT_ROOM_EVENT + event.getTitle());
		}
		this.events.add(event);
		this.remainingDuration -= event.getDuration();
	}

	public boolean hasRoomFor(final Event event) {
		return this.remainingDuration >= event.getDuration();
	}

	public void addNewBucket(final Bucket bucket) {
		this.setSupplement(bucket);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int nextEventStartTime = addEventsSchedule(events, this.startTime, str);
		if (this.getSupplement() != null) {
			int supplementStartTime = this.getSupplement().startTime;
			if (nextEventStartTime > this.getSupplement().startTime) {
				supplementStartTime = nextEventStartTime;
			}
			nextEventStartTime = this.addEventsSchedule(this.getSupplement().events, supplementStartTime, str);
		}
		return str.toString();
	}

	private static final String NEW_LINE = System.getProperty("line.separator");

	private int addEventsSchedule(final List<Event> events, final int startTime, final StringBuilder str) {
		int nextEventStartTime = startTime;
		for (Event event : events) {
			str.append(displayTime(nextEventStartTime) + " " + event + NEW_LINE);
			nextEventStartTime += event.getDuration();
		}

		return nextEventStartTime;
	}

	public static String displayTime(int minutes) {
		int maxTimeInMinutes = MAX_TIME_IN_MINUTES;
		if (minutes > maxTimeInMinutes) {
			throw new IllegalArgumentException(
					"Time in minutes cannot be greater than" + maxTimeInMinutes + " minutes.");
		}

		int hours = minutes / 60;
		String hoursToDisplay = Integer.toString(hours);
		if (hours > 12) {
			hoursToDisplay = Integer.toString(hours - 12);
		}
		if (hoursToDisplay.length() == 1) {
			hoursToDisplay = "0" + hoursToDisplay;
		}

		minutes = minutes - (hours * 60);
		String minutesToDisplay = null;
		if (minutes == 0) {
			minutesToDisplay = "00";
		} else if (minutes < 10) {
			minutesToDisplay = "0" + minutes;
		} else {
			minutesToDisplay = "" + minutes;
		}

		String displayValue;
		if (hours < 12) {
			displayValue = " AM";
			if (hoursToDisplay.equals("00")) {
				hoursToDisplay = "12";
			}
		} else {
			displayValue = " PM";
		}
		displayValue = hoursToDisplay + ":" + minutesToDisplay + displayValue;

		return displayValue;
	}
}
