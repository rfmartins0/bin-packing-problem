package br.com.conference.value;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BucketTest {

	@Test
	public void testBucketHasntRoom() {
		Event event = Event.build("Junit ever", 100000);
		Bucket bucket = Bucket.build(100, 0);
		assertFalse(bucket.hasRoomFor(event));
	}

	@Test
	public void testBucketHasRoom() {
		Event event = Event.build("Event hi", 1);
		Bucket bucket = Bucket.build(100, 0);
		bucket.addNewBucket(bucket);
		assertTrue(bucket.hasRoomFor(event));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBucketNotDisplay() {
		Bucket.displayTime(1000000);
	}

	@Test
	public void testBucketDisplay() {
		Bucket.displayTime(10);
	}

}
