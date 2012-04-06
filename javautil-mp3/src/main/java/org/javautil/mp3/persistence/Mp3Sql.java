package org.javautil.mp3.persistence;

public class Mp3Sql {
	String populateArtistAlias = "insert into artist_alias(artist_alias_id, artist_alias) "
			+ "select artist_alias_id_seq.nextval, artist_alias "
			+ "from (select distinct upper(artist) artist_alias from mp3 where artist is not null "
			+ "minus select distinct upper(artist_alias) from artist_alias) ";

	// create table artist_suspect (artist_suspect_id number(9) not null,
	// artist_name varchar2(60), artist_alias varchar2(60), artist_name_count
	// number(9));
	//
	// create constraint artist_suspect_id_pk primary key on
	// artist_suspect(artist_suspect_id);
}
