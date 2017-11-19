package inscriptions;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class PersonneTest {

	private Inscriptions inscriptions;
	private Personne personne = new Personne(inscriptions, "nomtest", "prenomtest", "mailtest");
	private Set<Equipe> equipes = new TreeSet<Equipe>();
	

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrenom() {
		assertEquals(personne.getPrenom(),"prenomtest");
	}

	@Test
	public void testGetMail() {
		assertEquals(personne.getMail(),"mailtest");
	}

	@Test
	public void testGetEquipes() {
		assertEquals(personne.getEquipes(),equipes);
	}

	@Test
	public void testAddEquipe() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveEquipe() {
		fail("Not yet implemented");
	}

}
