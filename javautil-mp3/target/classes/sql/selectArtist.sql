select upper(artist_name), count(*) 
from mp3 
group by upper(artist_name) 
order by upper(artist_name)