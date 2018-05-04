//package tests;
//
//import static org.junit.Assert.*;
//
//import java.util.Date;
//
//
//import org.junit.Test;
//
//import inscriptions.Competition;
//import inscriptions.Equipe;
//import inscriptions.Inscriptions;
//import inscriptions.Personne;
//
//public class CompetitionTest {
//
//	private Inscriptions inscriptions = Inscriptions.getInscriptions();
//	private Competition competition = inscriptions.createCompetition("nomtest", (), false);
//	
//	@Test
//	public void testGetNom() {
//		assertEquals(competition.getNom(),"nomtest");
//	}
//	
//	@Test
//	public void testSetNom() {
//		competition.setNom("nomtest2");
//		assertEquals(competition.getNom(),"nomtest2");
//	}
//
//	@Test
//	public void testInscriptionsOuvertes() {
//		assertTrue(competition.inscriptionsOuvertes());
//	}
//
//	@Test
//	public void testGetDateCloture() {
//		assertEquals(competition.getDateCloture(),LocalDate.now());
//	}
//	
//	@Test
//	public void testSetDateCloture() {
//		LocalDate auj = LocalDate.now();
//		LocalDate hier = auj.minusDays(1);
//		competition.setDateCloture(hier);
//		assertEquals(competition.getDateCloture(),hier);
//	}
//
//	@Test
//	public void testAddPersonne() {
//		Personne testPersonne = inscriptions.createPersonne("testnom", "testprenom", "testmail");
//		assertTrue(competition.add(testPersonne));
//	}
//	
//	@Test
//	public void testAddEquipe() {
//		Competition competitionEquipe = inscriptions.createCompetition("nomtest", LocalDate.now(), true);
//		Equipe testEquipe = inscriptions.createEquipe("testnom");
//		assertTrue(competitionEquipe.add(testEquipe));
//	}
//	
//	@Test
//	public void testEstEnEquipe() {
//		assertFalse(competition.estEnEquipe());
//	}
//
//}