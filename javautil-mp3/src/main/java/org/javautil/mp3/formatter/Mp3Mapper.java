package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3;
import org.javautil.mp3.Mp3Metadata;

public interface Mp3Mapper {
	Mp3 toMp3(Mp3Metadata meta);

}
