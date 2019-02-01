package br.com.conference.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Track {
	private final List<Bucket> buckets;

	private Track() {
		this.buckets = new ArrayList<Bucket>();
	}

	public static Track build() {
		return new Track();
	}

	public void addBucket(final Bucket bucket) {
		this.buckets.add(bucket);
	}

	public List<Bucket> getBuckets() {
		return Collections.unmodifiableList(this.buckets);
	}

	public int getNumberOfBuckets() {
		return buckets.size();
	}

	
}
