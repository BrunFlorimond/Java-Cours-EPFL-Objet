/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/

class OptionVoyage{
	
	private final String nom;
	private final double prix;
	
	public OptionVoyage(String nom, double prix){
		
		this.nom = nom;
		this.prix = prix;
		
	}
	
	public String getNom(){
		
		return this.nom;
		
	}
	
	public double getPrix(){
		
		return this.prix;
		
	}
	
        @Override
	public String toString(){
		
		String str = this.nom + " -> " + this.prix + " CHF";
		return str;
	}
	
}


class Sejour extends OptionVoyage {
	
	private final int nbNuit;
	private final double prixNuit;
	
	public Sejour(String nom,double prix,int nbNuit, double prixNuit){
		
		super(nom,prix);
		this.nbNuit = nbNuit;
		this.prixNuit = prixNuit;
		
	}
	
        @Override
	public double getPrix(){
		
		return this.nbNuit * this.prixNuit + super.getPrix();
		
	}
	
        @Override
	public String toString(){
		
		String str = super.getNom() + " -> " + this.getPrix() + " CHF";
		return str;
	}
	
}

class Transport extends OptionVoyage{
	
	private final boolean longueur;
	public static final double TARIF_LONG = 1500.0;
	public static final double TARIF_BASE = 200.0;
	
	public Transport(String nom, double prix, boolean longueur){
		
		super(nom,prix);
		this.longueur = longueur;
		
	}
	
	public Transport(String nom, double prix){
		
		super(nom,prix);
		this.longueur = false;
		
	}
	
        @Override
	public double getPrix(){
		
		double prixFinal = 0;
		
		if(this.longueur){
			
			prixFinal = Transport.TARIF_LONG;
			
		} else {
			
			prixFinal = Transport.TARIF_BASE;
			
		}
		
		return prixFinal + super.getPrix();
		
		
	}
	
        @Override
	public String toString(){
		
		String str = super.getNom() + " -> " + this.getPrix() + " CHF";
		return str;
	}
	
}





/*******************************************
 * Ne pas modifier apres cette ligne
 * pour pr�server les fonctionnalit�s et
 * le jeu de test fourni.
 * Votre programme sera test� avec d'autres
 * donn�es.
 *******************************************/

public class Voyage {
    public static void main(String args[]) {

        // TEST 1
        System.out.println("Test partie 1 : ");
        System.out.println("----------------");
        OptionVoyage option1 = new OptionVoyage("S�jour au camping", 40.0);
        System.out.println(option1.toString());

        OptionVoyage option2 = new OptionVoyage("Visite guid�e : London by night" , 50.0);
        System.out.println(option2.toString());
        System.out.println();

        // FIN TEST 1

        
        // TEST 2
        System.out.println("Test partie 2 : ");
        System.out.println("----------------");

        Transport transp1 = new Transport("Trajet en car ", 50.0);
        System.out.println(transp1.toString());

        Transport transp2 = new Transport("Croisi�re", 1300.0);
        System.out.println(transp2.toString());

        Sejour sejour1 = new Sejour("Camping les flots bleus", 20.0, 10, 30.0);
        System.out.println(sejour1.toString());
        System.out.println();

        // FIN TEST 2

        /*
        // TEST 3
        System.out.println("Test partie 3 : ");
        System.out.println("----------------");

        KitVoyage kit1 = new KitVoyage("Zurich", "Paris");
        kit1.ajouterOption(new Transport("Trajet en train", 50.0));
        kit1.ajouterOption(new Sejour("Hotel 3* : Les amandiers ", 40.0, 5, 100.0));
        System.out.println(kit1.toString());
        System.out.println();
        kit1.annuler();

        KitVoyage kit2 = new KitVoyage("Zurich", "New York");
        kit2.ajouterOption(new Transport("Trajet en avion", 50.0, true));
        kit2.ajouterOption(new Sejour("Hotel 4* : Ambassador Plazza  ", 100.0, 2, 250.0));
        System.out.println(kit2.toString());
        kit2.annuler();
*/
        // FIN TEST 3
    }
}

