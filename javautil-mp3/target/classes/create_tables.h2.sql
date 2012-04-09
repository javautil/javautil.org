/*
create table mp3_stage (
        mp3_stage_id   number(9) not null,
        mp3_load_id    number(9) not null,
	file_name varchar2(255) not null,
	bit_Rate  int, 
	album_Title varchar2(60),
	album_Title_raw varchar2(60),
	song_title  varchar2(60),
	song_title_raw  varchar2(60),
	author      varchar2(60),
	author_raw      varchar2(60),
	artist_name varchar2(60), 
	artist_name_raw varchar2(60), 
	genre       varchar2(60), 
	genre_raw       varchar2(60), 
	song_comment varchar2(60),
	song_comment_raw varchar2(60),
	track        varchar2(8),
	track_raw        varchar2(8),
	year_released varchar2(8),
	artist_id    number(9)
    );
*/

create table mp3 (
        mp3_id   number(9) not null,
	file_name varchar2(255) not null,
	bit_Rate  int, 
	album_Title varchar2(60),
	song_title  varchar2(60),
	author      varchar2(60),
	artist_name varchar2(60), 
	genre       varchar2(60), 
	song_comment varchar2(60),
	track        varchar2(8),
	year_released varchar2(8),
	artist_id    number(9)
    );


alter table mp3 add constraint mp3_pk primary key (mp3_id);

alter table mp3 add constraint mp3_uk unique key (file_name);

create sequence mp3_id_seq;

create table artist (artist_id number(9) not null, artist_name varchar2(60), sort_name varchar2(60));

alter table artist add constraint artist_pk primary key (artist_id);

create unique index artist_uk on artist(artist_name);

create sequence artist_id_seq;

/* would be nice to have a unique index on artist_name upper */

create table artist_alias (artist_alias_id number(9) not null, 
                           artist_alias varchar2(60) not null, 
                           artist_id number(9), 
                           status_cd varchar2(1)
                          );
comment on table artist_alias is 'Contains authoritative and suspected aliases for an artist' ;                       
comment on column  artist_alias.status_cd is '"S" Suggested or Suspected, "A" Authoritative';
comment on column  artist_alias.artist_alias is 'Should be all upper case and stripped of leading and trailing spaces';
alter table artist_alias add constraint aa_ck_ac check (status_cd in ('S','A'));

alter table artist_alias add constraint artist_alias_pk primary key (artist_alias_id);

alter table artist_alias add constraint aa_a_fk foreign key (artist_id) references artist (artist_id);

create sequence artist_alias_id_seq;
