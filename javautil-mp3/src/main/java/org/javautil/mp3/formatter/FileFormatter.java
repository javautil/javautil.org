package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3Metadata;

/**
 * Attempts to replicate the format of the linux File command
 * 
 * @author jjs
 * 
 */
public class FileFormatter extends Mp3BaseMetadataFormatter {

	public String format(final Mp3Metadata meta) {
		final StringBuilder sb = new StringBuilder();
		sb.append(meta.getFileName());
		sb.append(":");
		sb.append("Audio file with ID3 version 2.3.0"); // TODO probably not all
														// files
		sb.append(", contains MPEG ADTS, layer III, v1"); // TDO
		sb.append(meta.getBitRate());
		sb.append("kbps,");
		// sb.append(meta.getSamplingRate());
		sb.append("kHz, JntStereo");
		return sb.toString();

		// /ByArtist/(Unfiled - MP3s)/The Spartan Dischords - Mam.mp3: Audio
		// file with ID3 version 2.3.0, contains: MPEG ADTS, layer III, v1, 128
		// kbps, 44.1 kHz, JntStereo

	}

}
