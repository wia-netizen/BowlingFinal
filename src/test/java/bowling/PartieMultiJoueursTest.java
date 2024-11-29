package bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartieMultiJoueursTest {

	@Test
	public void testDemarreNouvellePartie() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[] {"Pierre", "Paul"});

		assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 1", partie.prochainTir());
	}

	@Test
	public void testEnregistrerLancer() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[] {"Pierre", "Paul"});

		// Lancer 1 Pierre
		assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 2", partie.enregistreLancer(5));
		// Lancer 2 Pierre
		assertEquals("Prochain tir : joueur Paul, tour n° 1, boule n° 1", partie.enregistreLancer(3));
	}

	@Test
	public void testScorePour() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[] {"Pierre", "Paul"});

		partie.enregistreLancer(5);
		partie.enregistreLancer(3);

		assertEquals(8, partie.scorePour("Pierre"));
		assertEquals(3, partie.scorePour("Paul"));
	}

	@Test
	public void testJoueurInconnu() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[] {"Pierre", "Paul"});

		// Tester si une exception est bien levée lorsqu'un joueur inconnu est demandé
		assertThrows(IllegalArgumentException.class, () -> {
			partie.scorePour("Jacques");
		});
	}
}
