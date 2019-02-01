package br.com.conference.value;

public class Event {

	private final String title;
	private final int duration;

	private Event(final String title, final int duration) {
		this.title = title;
		this.duration = duration;
	}

	public static Event build(final String title, final int time) {
		return new Event(title, time);
	}

	@Override
	public String toString() {
		return this.title;
	}

	public int getDuration() {
		return this.duration;
	}

	public String getTitle() {
		return this.title;
	}
}
