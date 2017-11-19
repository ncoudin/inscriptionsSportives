package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class InscriptionsTest {

	private Inscriptions inscriptions = Inscriptions.getInscriptions();
	private SortedSet<Competition> competitions = new TreeSet<>();
	private SortedSet<Candidat> candidats = new TreeSet<>();
	
	@Test
	public void testGetCompetitions() 
	{
		Competition competition = new Competition(inscriptions, "Mondial de fléchettes", null, false);
		competitions.add(competition);
		assertEquals("testGetCompetition",inscriptions.getCompetitions(),competitions);
	}
	
	@Test
	public void testGetCandidats() 
	{
		//Candidat candidat = new Candidat(inscriptions, "truc");
		//candidats.add(candidat);
		assertEquals("testGetCandidats",inscriptions.getCandidats(),competitions);
	}
	
	@Test
	public void testGetPersonnes() 
	{
		Competition competition = new Competition(inscriptions, "Mondial de fléchettes", null, false);
		competitions.add(competition);
		assertEquals("testGetCompetition",inscriptions.getCompetitions(),competitions);
	}
}
