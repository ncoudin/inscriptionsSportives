package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class InscriptionsTest {

	private Inscriptions inscriptions = Inscriptions.getInscriptions();
	private SortedSet<Competition> competitions = new TreeSet<>();
	private SortedSet<Candidat> candidats = new TreeSet<>();
	Competition competition = inscriptions.createCompetition("Mondial de fléchettes", LocalDate.now(), false);
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty"),
			boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
	Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
	
	@Test
	public void testGetCompetitions() 
	{
		competitions.add(competition);
		assertEquals("testGetCompetition",inscriptions.getCompetitions(),competitions);
	}
	
	@Test
	public void testGetCandidats() 
	{
		assertEquals("testGetCandidats",inscriptions.getCandidats(),competitions);
	}
	
	@Test
	public void testGetPersonnes() 
	{
		assertEquals("testGetCompetition",inscriptions.getPersonnes(),inscriptions.getCandidats());
	}
}
