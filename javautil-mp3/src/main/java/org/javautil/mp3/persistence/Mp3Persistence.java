package org.javautil.mp3.persistence;

import java.util.Collection;
import java.util.List;

import org.javautil.mp3.ArtistSuspect;
import org.javautil.mp3.Mp3;
import org.javautil.mp3.Mp3Metadata;

public interface Mp3Persistence {
	/**
	 * saves an MP3MetaData into the persistent storage.
	 * 
	 * @param mp3data
	 */
	public void save(Mp3Metadata mp3data);

	public void flushAndCommit();

	public Collection<Mp3> getByTitle(String songTitle);

	void dispose();

	public Collection<Mp3> getAll();

	public List<ArtistSuspect> getArtistSuspects();

	public boolean supportsStreamWriter();
}
