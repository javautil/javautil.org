package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3Metadata;
import org.javautil.text.CSVWriter;

public class CsvFormatter extends Mp3BaseMetadataFormatter {
	private final CSVWriter csvWriter = new CSVWriter();
	// TODO should be wired
	private final Mp3MetadataAccessor accessor = new Mp3MetadataAccess();

	public String format(final Mp3Metadata meta) {
		String returnValue = null;
		final Object[] attributes = getAsObjectArray(meta);
		returnValue = csvWriter.asString(attributes);
		return returnValue;
	}

	public String getTitles() {
		final String[] titles = new String[] { "Directory", //
				"File Name", //
				"Album Title", //
				"Song Title", //
				"Author", //
				"Lead Artist", //
				"Genre", //
				"Comment", //
				"Lyrics", //
				"Track", "Year" };
		final String titlesString = csvWriter.asString(titles);
		return titlesString;

	}

	Object[] getAsObjectArray(final Mp3Metadata meta) {
		accessor.setMetadata(meta);
		final Object[] retval = new Object[] { accessor.getAlbumTitle(),
				accessor.getSongTitle(), accessor.getAuthorComposer(),
				accessor.getLeadArtist(), accessor.getGenre(),
				accessor.getComment(), accessor.getSongLyric(),
				accessor.getTrack(), accessor.getYearReleased() };
		return retval;
	}

}
