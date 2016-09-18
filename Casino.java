import java.util.ArrayList;

/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/

abstract class Mise {
	
	private final int jetonsMises;
	
	public Mise(int mise){
		
		this.jetonsMises = mise;
		
	}
	
	public final int getMise(){
		
		return this.jetonsMises;
		
	}
	
	abstract public int gain (int n);

}

class Pleine extends Mise{
	
	private final int numero;

	public final static int FACTEUR_GAIN = 35;
	
	public Pleine(int mise, int numero){
		super(mise);
		this.numero = numero;

	}

	@Override
	public int gain(int n) {
		
		if (this.numero == n){
			
			return super.getMise() * Pleine.FACTEUR_GAIN;
			
		} else {
			
			return 0;
		}
	}
	
}

class Rouges extends Mise{
	
	public final static int[] ROUGES =  {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34 , 36 };
	
	public Rouges(int mise){
		
		super(mise);
		
		
	}

	@Override
	public int gain(int n) {
		
		if (Rouges.checkNum(Rouges.ROUGES, n)){
		
			return super.getMise();
		
		} else {
			
			return 0;
			
		}
	}
	
	private static boolean checkNum (int[] tableau, int numero ){
		
		boolean result = false;
		
		for (int num : tableau){
			
			if (num == numero){
				
				result = result || true;
				
			} else {
				
				result = result || false;
				
			}
			
		}
		
		return result;
		
	}
	
}

class Joueur{
	
	private Mise mise;
	private final String nom;
	
	public Joueur (String nom){
		
		this.nom = nom;
		
	}
	
	public String getNom(){
		
		return this.nom;
		
	}
	
	public void setStrategie (Mise mise){
		
		this.mise = mise;
		
	}
	
	public int getMise(){
		
		if (this.mise == null){
			
			return 0;
			
		} else {
			
			return this.mise.getMise();
			
		}
		
	}
	
	public int gain (int n){
		
		if (this.mise == null){
			
			return 0;
			
			
		} else {
			
			
			return this.mise.gain(n);
			
		}
		
		
	}
	
	
}

abstract class Roulette {
	
	private ArrayList<Joueur> joueurs;
	private int gain;
	private int tire;
	
	public Roulette(){
		
		this.joueurs = new ArrayList<Joueur>();
		this.gain = 0;
		this.tire = 0;
		
	}
	
	public void participe(Joueur joueur){
		
		this.joueurs.add(joueur);
		
	}
	
	public ArrayList<Joueur> getJoueurs(){
		
		return (ArrayList<Joueur>) this.joueurs.clone();
		
	}
	
	public int getTirage(){
		
		return this.tire;
		
	}
	
	public int getGainMaison(){
		
		return this.gain;
		
	}
	
	public void setGainMaison(int gain){
		
		this.gain = gain;
		
	}
	
	public int getParticipants(){
		
		return this.joueurs.size();
		
	}
	
	public void rienNeVaPlus(int numero){
		
		this.tire = numero;
		
	}
	
	public abstract int perteMise (int miseDuJoueur);
	
	public void calculerGainMaison(){
		
		int totalGain = 0;
		
		for (Joueur joueur : this.getJoueurs()){
			
			int gain = joueur.gain(this.getTirage());
			
			if(gain > 0) {
				
				 totalGain -= gain;
				
			} else {
				
				totalGain+= this.perteMise(joueur.getMise());
				
			}
			
		}
		
		this.setGainMaison(totalGain);
		
	}
	
	public String toString(){
		
		String str = "";
		
		str += "Croupier : le numéro du tirage est le " + this.getTirage();
		
		for (Joueur joueur : this.joueurs){
			
			str += "\n  Le joueur " + joueur.getNom() + " mise ";
			str += joueur.getMise();
			
			if (joueur.gain(tire) > 0){
				
				str += " et gagne " + joueur.gain(tire);
				
			} else {
				
				str += " et perd ";
				
			}
			
			
		}
		
		str += "\n Gain/perte du casino : " + this.getGainMaison();
		
		return str;
		
	}
	
}

class RouletteFrancaise extends Roulette{
	
	public RouletteFrancaise(){
		
		super();
		
	}

	@Override
	public int perteMise(int miseDuJoueur) {
		
		return miseDuJoueur;
	}
	
}

class RouletteAnglaise extends Roulette implements ControleJoueurs{
	
	public RouletteAnglaise(){
		
		super();
		
	}

	@Override
	public int perteMise(int miseDuJoueur) {
		
		return miseDuJoueur/2;
		
	}
	
	public boolean check(){
		
		return super.getParticipants()< 10;
		
	}
	
	public void participe (Joueur joueur){
		
		if (this.check()){
			
			super.participe(joueur);
			
		}
		
	}
	
}

interface ControleJoueurs{
	
	public boolean check();
	
}


/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
class Casino {

    private static void simulerJeu(Roulette jeu) {
        for (int tirage : new int [] { 12, 1, 31 }) {
            jeu.rienNeVaPlus(tirage);
            jeu.calculerGainMaison();
            System.out.println(jeu);
            System.out.println("");
        }
    }

    public static void main(String[] args) {

        Joueur joueur1 = new Joueur("Dupond");
        joueur1.setStrategie(new Pleine(100,1)); // miser 100 jetons sur le 1

        Joueur joueur2 = new Joueur("Dupont");
        joueur2.setStrategie(new Rouges(30)); // miser 30 jetons sur les rouges

        Roulette jeu1 = new RouletteAnglaise();
        jeu1.participe(joueur1);
        jeu1.participe(joueur2);

        Roulette jeu2 = new RouletteFrancaise();
        jeu2.participe(joueur1);
        jeu2.participe(joueur2);

        System.out.println("Roulette anglaise :");
        simulerJeu(jeu1);
        System.out.println("Roulette française :");
        simulerJeu(jeu2);
    }
}
