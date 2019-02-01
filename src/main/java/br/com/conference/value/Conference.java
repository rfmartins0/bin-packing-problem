package br.com.conference.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conference {
	private final List<Track> tracks;

	private Conference() {
		this.tracks = new ArrayList<Track>();
	}

	public static Conference build() {
		return new Conference();
	}

	public void addTrack(final Track track) {
		this.tracks.add(track);
	}

	public List<Track> getTracks() {
		return Collections.unmodifiableList(this.tracks);
	}

	public int getNumberOfTracks() {
		return this.tracks.size();
	}

	
}
