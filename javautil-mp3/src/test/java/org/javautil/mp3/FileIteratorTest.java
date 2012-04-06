package org.javautil.mp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class FileIteratorTest {

	@Test
	public void test() {
		final File[] directories = new File[] { new File("src/test/resources") };
		final FileIterator iterator = new FileIterator(directories);
		File file = iterator.next();
		assertEquals("Aerosmith - Angel.mp3", file.getName());
		file = iterator.next();
		assertEquals("Aerosmith - I Don't Want To Miss A Thing.mp3",
				file.getName());
		file = iterator.next();
		assertEquals("Aerosmith - Janies Got A Gun.mp3", file.getName());
		file = iterator.next();
		assertEquals("Aerosmith - Sweet Emotion.mp3", file.getName());
		file = iterator.next();
		assertEquals("MM Jukebox Plus Upgrade.mp3", file.getName());
		file = iterator.next();
		assertEquals("Theme - Fat Albert.mp3", file.getName());
		file = iterator.next();
		assertEquals("Theme - HeMan.mp3", file.getName());
		file = iterator.next();
		assertEquals("Theme - Macguyver.mp3", file.getName());
		file = iterator.next();
		assertNull(file);
	}

	@Ignore
	@Test
	public void test2() {
		final File[] directories = new File[] { new File("tunes") };
		final FileIterator iterator = new FileIterator(directories);
		File file = iterator.next();
		assertEquals("Elbow_Room.mp3", file.getName());
		file = iterator.next();
		assertEquals("It's Heatin' Up.mp3", file.getName());
		file = iterator.next();
		assertEquals("Momma.mp3", file.getName());
		file = iterator.next();
		assertNull(file);
	}
}
