package bowling;

import java.util.HashMap;
import java.util.Map;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {

	private Map<String, Joueur> joueurs;  // Map pour garder une trace des joueurs et de leurs scores
	private String joueurActuel;  // Le joueur qui doit jouer
	private int numeroTour;  // Le numéro du tour en cours
	private int prochainLancer;  // Le numéro du prochain lancer dans le tour
	private String[] listeJoueurs;  // Liste des joueurs pour alterner entre eux
	private int joueurIndex;  // Index du joueur actuel dans la liste des joueurs
	private Tour tourActuel;  // Tour actuel (peut être un tour normal ou un dernier tour)

	public PartieMultiJoueurs() {
		this.joueurs = new HashMap<>();
	}

	/**
	 * Démarre une nouvelle partie avec les joueurs spécifiés.
	 *
	 * @param joueurs Un tableau de noms des joueurs.
	 */
	@Override
	public void demarreNouvellePartie(String[] joueurs) {
		if (joueurs == null || joueurs.length < 2) {
			throw new IllegalArgumentException("La partie doit avoir au moins deux joueurs.");
		}

		this.listeJoueurs = joueurs;
		this.joueurIndex = 0;
		this.joueurActuel = joueurs[joueurIndex];
		this.numeroTour = 1;
		this.prochainLancer = 1;

		// Initialiser les joueurs et leur score
		for (String joueur : joueurs) {
			this.joueurs.put(joueur, new Joueur(joueur));
		}

		// Démarrer la partie avec le premier tour
		this.tourActuel = new Tour();
	}

	/**
	 * Enregistre un lancer pour le joueur actuel et gère l'alternance des joueurs.
	 * @param quillesAbattues Le nombre de quilles abattues par le joueur lors de ce lancer.
	 * @return Un message indiquant quel est le prochain tir.
	 */
	@Override
	public String enregistreLancer(int quillesAbattues) {
		if (joueurs.isEmpty()) {
			throw new IllegalStateException("La partie n'est pas encore commencée.");
		}

		// Enregistrer le lancer pour le joueur actuel
		Joueur joueur = joueurs.get(joueurActuel);
		boolean tourContinuer = tourActuel.enregistreLancer(quillesAbattues);

		// Passer au tour suivant si le tour est terminé
		if (tourActuel.estTermine()) {
			joueur.ajouterScore(tourActuel.scoreCumule());
			numeroTour++;
			prochainLancer = 1;

			// Passer au joueur suivant
			joueurIndex = (joueurIndex + 1) % listeJoueurs.length;
			joueurActuel = listeJoueurs[joueurIndex];

			// Passer au tour suivant, ou dernier tour si on est au 10ème tour
			if (numeroTour <= 9) {
				tourActuel = new Tour();
			} else {
				tourActuel = new DernierTour();
			}
		} else {
			prochainLancer++;
		}

		// Retourner l'information sur le prochain tir
		return prochainTir();
	}

	/**
	 * Retourne le score actuel d'un joueur donné.
	 * @param joueur Le nom du joueur dont on souhaite connaître le score.
	 * @return Le score du joueur.
	 * @throws IllegalArgumentException Si le joueur est inconnu.
	 */
	@Override
	public int scorePour(String joueur) {
		Joueur joueurInfo = joueurs.get(joueur);
		if (joueurInfo == null) {
			throw new IllegalArgumentException("Joueur inconnu");
		}
		return joueurInfo.getScore();
	}

	/**
	 * Retourne une chaîne indiquant quel est le prochain tir (joueur, tour et boule).
	 * @return Un message avec les informations sur le prochain tir.
	 */
	@Override
	public String prochainTir() {
		return String.format("Prochain tir : joueur %s, tour n° %d, boule n° %d",
			joueurActuel, numeroTour, prochainLancer);
	}
}
