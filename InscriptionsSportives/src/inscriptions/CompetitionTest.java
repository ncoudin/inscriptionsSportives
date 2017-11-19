package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class CompetitionTest {

	private Inscriptions inscriptions;
	private Competition competition = new Competition(inscriptions, "nomtest", LocalDate.now(), false);
	
	@Test
	public void testGetNom() {
		assertEquals(competition.getNom(),"nomtest");
	}

	@Test
	public void testInscriptionsOuvertes() {
		assertTrue(competition.inscriptionsOuvertes());
	}

	@Test
	public void testGetDateCloture() {
		assertEquals(competition.getDateCloture(),LocalDate.now());
	}

	@Test
	public void testEstEnEquipe() {
		assertFalse(competition.estEnEquipe());
	}

}