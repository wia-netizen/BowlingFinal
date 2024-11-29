package bowling;

public class Joueur {

	private String nom;
	private int score;

	public Joueur(String nom) {
		this.nom = nom;
		this.score = 0;
	}

	public void ajouterScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}

	public String getNom() {
		return nom;
	}
}
