package org.javautil.mp3;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javautil.mp3.persistence.Mp3Persistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// TODO write or use as data or delete
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ExtractorTestH2HibernateApplicationContext.xml" })
public class ArtistGrouperTest {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private Mp3Persistence persistence;
	private final ArtistGrouper matcher = new ArtistGrouper();

	private final ArtistSuspect beatles1 = new ArtistSuspect("The Beatles", 561);
	private final ArtistSuspect beatles2 = new ArtistSuspect("Beatles", 81);
	private final ArtistSuspect beatles3 = new ArtistSuspect("Beatlesles", 1);
	private final ArtistSuspect beatles4 = new ArtistSuspect("Beatles, The", 28);
	private final ArtistSuspect ultrabeat = new ArtistSuspect("Ultrabeat", 1);
	private final ArtistSuspect beat = new ArtistSuspect("The Beat", 1);
	private final ArtistSuspect cultureBeat = new ArtistSuspect("Culture Beat",
			1);
	private final ArtistSuspect bronskiBeats = new ArtistSuspect(
			"BRONSKI BEAT", 1);
	private final ArtistSuspect easyBeats = new ArtistSuspect("The Easybeats",
			2);
	private final ArtistSuspect londonBeats = new ArtistSuspect("Londonbeat", 1);

	private final List<ArtistSuspect> suspects = new ArrayList<ArtistSuspect>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(beatles1);
			add(beatles2);
			add(beatles3);
			add(beatles4);
			add(ultrabeat);
			add(beat);
			add(cultureBeat);
			add(bronskiBeats);
			add(easyBeats);
			add(londonBeats);

		}
	};

	public List<ArtistSuspect> getArtistSuspects() {
		return persistence.getArtistSuspects();
	}

	@Test
	public void doNothing() {
		final List<ArtistSuspect> suspects = getArtistSuspects();
		matcher.setSuspects(suspects);
		final Map<String, ArtistSuspectGroup> map = matcher
				.getSuspectGroupByTokenStringMap();
		assertNotNull(map);
		if (logger.isDebugEnabled()) {
			logger.debug(map.toString());
		}
		// TODO do more
	}

	// @Test
	// public void test1() {
	// ArtistGrouper grouper = new ArtistGrouper();
	// grouper.setSuspects(suspects);
	// grouper.populateSuspectsByTokens();
	// Map<String[], Map<String,ArtistSuspect> >suspectsByTokens =
	// grouper.getSuspectsByTokens();
	// String suspectsByTokensText = grouper.dumpSuspectsByTokens();
	// logger.debug(suspectsByTokensText);
	// Map<String,ArtistSuspect> beatlesSuspects = suspectsByTokens.get(new
	// String[] {"BEATLES"});
	// assertEquals(3,beatlesSuspects.size());
	// assertNotNull(beatlesSuspects.get(beatles1.getRawName()));
	// assertNotNull(beatlesSuspects.get(beatles2.getRawName()));
	// assertNotNull(beatlesSuspects.get(beatles4.getRawName()));
	// }

	// TODO clean
	// @Test
	// public void getMatchTest() {
	// assertEquals("BEATLES", matcher.getMatchName("Beatles, The"));
	// assertEquals("BEATLES", matcher.getMatchName("BEATLES,  THE"));
	// assertEquals("BEATLES", matcher.getMatchName("The Beatles"));
	// assertEquals("BEATLES", matcher.getMatchName(" The Beatles "));
	//
	// }
}
