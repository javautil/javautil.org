package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3Metadata;

// TODO need interface?
public class Mp3MetadataFormatter extends Mp3BaseMetadataFormatter {
	static final String newline = System.getProperty("line.separator");

	public String toLongString(final Mp3Metadata meta) {
		final StringBuilder b = new StringBuilder();
		b.append(asTripletLine("albumTitle", meta.getAlbumTitle1(),
				meta.getAlbumTitle2()));
		b.append(" ");
		b.append(asTripletLine("songTitle", meta.getSongTitle1(),
				meta.getSongTitle2()));
		b.append(" ");
		b.append(asTripletLine("authorComposer", meta.getAuthorComposer1(),
				meta.getAuthorComposer2()));
		b.append(" ");
		b.append(asTripletLine("leadArtist", meta.getLeadArtist1(),
				meta.getLeadArtist2()));
		b.append(" ");
		b.append(asTripletLine("genre", meta.getGenre1(), meta.getGenre2()));
		b.append(" ");
		b.append(asTripletLine("songComment", meta.getSongComment1(),
				meta.getSongComment2()));
		b.append(" ");
		b.append(asTripletLine("songLyric", meta.getSongLyric1(),
				meta.getSongLyric2()));
		b.append(" ");
		b.append(asTripletLine("track", meta.getTrack1(), meta.getTrack2()));
		b.append(" ");
		b.append(asTripletLine("yearReleased", meta.getYearReleased1(),
				meta.getYearReleased2()));
		b.append(" ");
		return b.toString();
	}

	public String asString(final Mp3Metadata meta) {
		final Mp3MetadataAccess accessor = new Mp3MetadataAccess();
		final StringBuilder b = new StringBuilder();
		append(b, asPair("albumTitle", accessor.getAlbumTitle()));
		append(b, asPair(" songTitle", accessor.getSongTitle()));
		append(b, asPair(" authorComposer", accessor.getAuthorComposer()));
		append(b, asPair(" leadArtist", accessor.getLeadArtist()));
		append(b, asPair(" genre", accessor.getGenre()));
		append(b, asPair(" songComment", accessor.getComment()));
		append(b, asPair(" songLyric", accessor.getSongLyric()));
		append(b, asPair(" track", accessor.getTrack()));
		append(b, asPair(" yearReleased", accessor.getYearReleased()));
		return b.toString();
	}

	void append(final StringBuilder b, final String text) {
		if (text != null) {
			b.append(text);
		}
	}

	private String asTripletLine(final String left, final String middle,
			final String right) {
		String returnValue = "";
		if ((middle != null) || (right != null)) {
			final StringBuilder b = new StringBuilder();
			b.append(left);
			b.append(":");
			b.append(middle == null ? "null " : "'" + middle + "' ");
			// TODO Wtf b is never referenced
			returnValue = left + ": '" + middle + "' '" + right + "'" + newline;
		}
		return returnValue;
	}

}
