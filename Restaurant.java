import java.util.ArrayList;

/*****************************************************
 * Compléter le code à partir d'ici
 *****************************************************/
class Produit{
	private final String nom;
	private final String unite;
	
	public Produit(String nom){
		
		this.nom = nom;
		this.unite = "";
	}
	
	public Produit(String nom, String unite){
		
		this.nom = nom;
		this.unite = unite;
		
	}
	
	public Produit (Produit p){
		
		this.nom = p.getNom();
		this.unite = p.getUnite();
		
	}
	
	
	public Produit getProduit(){
		
		return this;
		
	}
	
	
	public String getNom(){
		
		return this.nom;
	}
	
	public String getUnite(){
		
		return this.unite;
		
	}
	
	public String toString(){
		
		return this.getNom();
		
	}
	
	
	public Produit adapter(double n){
		
		return this;
		
	}
	
	public double quantiteTotale(String nomProduit){
		
		if(nomProduit.equals(this.nom)){
			
			return 1;
			
		} else {
			
			return 0;
			
		}
		
	}
	
	
}

class ProduitCuisine extends Produit{
	
	private Recette recette;
	
	public ProduitCuisine(String nom){
		
		super(nom,"portion(s)");
		this.recette = new Recette(super.getNom());
		
	}
	
	public ProduitCuisine(ProduitCuisine pc){
		
		super(pc.getNom(),"portion(s)");
		this.recette = pc.recette;
		
	}
	
	public void ajouterARecette(Produit p, double q){
		
		recette.ajouter(p, q);
		
	}
	
	public void ajouterARecette(ProduitCuisine p, double q){
		
		recette.ajouter(p, q);
		
	}
	
	public void setRecette(Recette recette){
		
		this.recette = recette;
		
	}
	
	public ProduitCuisine adapter(double q){
		
		ProduitCuisine p = new ProduitCuisine(super.getNom());
		
		p.setRecette(this.recette.adapter(q));
		
		return p;
		
	}
	
	public ProduitCuisine getProduit(){
		
		return this;
		
	}
	
	public String toString(){
		
		String str = super.toString() + "\n";
		str = str + this.recette;
		
		return str;
		
	}
	
	
	public double quantiteTotale(String nomProduit){
		
		if(nomProduit.equals(super.getNom())){
			
			return 1;
			
		} else {
			
			return this.recette.quantiteTotale(nomProduit);
			
		}
		
	}
	
	
}

class Ingredient{
	
	private double quantite;
	private Produit produit;
	
	public Ingredient(String nom,double quantite){
		
		this.produit = new Produit(nom);
		this.quantite = quantite;
		
	}
	
	public Ingredient(String nom, String unite, double quantite){
		
		this.produit = new Produit(nom,unite);
		this.quantite = quantite;
		
	}
	
	public Ingredient(Produit produit,double quantite){
		
		this.produit = produit;
		this.quantite = quantite;
		
	}
	
	public Ingredient(ProduitCuisine produit,double quantite){
		
		this.produit = new ProduitCuisine(produit);
		this.quantite = quantite;
		
	}
	
	public Produit getProduit(){
		
		return this.produit;
		
	}
	
	public double getQuantite(){
		
		return this.quantite;
		
	}
	
	public String toString(){
		
		String result = this.quantite + " " + this.produit.getUnite() + " de " ;
		result = result + this.produit;
		return result;
	}
	
	public double quantiteTotale(String nomProduit){
		
		return this.produit.quantiteTotale(nomProduit);
		
	}
	
}

class Recette{
	
	private String nom;
	private double realisation;
	private ArrayList<Ingredient> ingredients;
	
	public Recette(String nom,double realisation){
		
		this.nom = nom;
		this.realisation = realisation;
		ingredients = new ArrayList<Ingredient>();
		
	}
	
	public Recette(String nom){
		
		this.nom = nom;
		this.realisation = 1;
		ingredients = new ArrayList<Ingredient>();
		
	}
	
	public void ajouter(Produit p, double quantite){
		
		
		ingredients.add(new Ingredient(p,quantite * this.realisation));
		
	}
	
	public void ajouter(ProduitCuisine p, double quantite){
		
		
		Ingredient ing = new Ingredient(p,quantite * this.realisation);
		System.out.println("TEST AJOUT INGREDIENT avec PRODUIT CUISINE : " + ing + " \n FIN TEST");
		ingredients.add(ing);
		
		
	}
	
	public Recette adapter(double n){
		
		Recette recette = new Recette(this.nom,this.realisation*n);
		
		for (Ingredient ingredient : this.ingredients){
			
			if(ingredient.getProduit() instanceof ProduitCuisine){
				
				ProduitCuisine newProduit = new ProduitCuisine((ProduitCuisine)ingredient.getProduit());
				recette.ajouter(newProduit,this.realisation);
				System.out.println("ADAPTATION : TEST AJOUT DE PRODUIT CUISINE : " + recette + " \n FIN ADAPTATION TEST AJOUT DE PRODUIT CUISINE");

			} else {
				
				Produit newProduit = new Produit(ingredient.getProduit());
				recette.ajouter(newProduit,this.realisation);
				
				
				
			}
		}
		
		
		return recette;
		
	}
	
	public String toString(){
		
		String str = " Recette " + this.nom + " x " + (double)this.realisation + ":";
		
		int j = 0;
		
		
		for(Ingredient ingredient : this.ingredients){
			j++;			
			str = str + "\n" +" "+ j +". " + ingredient.getProduit().adapter(this.realisation);
			
		}
		return str;
		
	}
	
	public double quantiteTotale(String nomProduit){
		
		double totalProduit = 0;
		
		for (Ingredient i: this.ingredients){
			
			totalProduit = totalProduit + i.quantiteTotale(nomProduit);
			
		}
		
		return totalProduit;
		
	}
	
	
}

/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/

class Restaurant {
    public static void main(String[] args) {

        // quelques produits de base
        Produit oeufs          = new Produit("oeufs");
        Produit farine         = new Produit("farine", "grammes");
        Produit beurre         = new Produit("beurre", "grammes");
        Produit sucreGlace     = new Produit("sucre glacé", "grammes");
        Produit chocolatNoir   = new Produit("chocolat noir", "grammes");
        Produit amandesMoulues = new Produit("amandes moulues", "grammes");
        Produit extraitAmandes = new Produit("extrait d'amandes", "gouttes");

        ProduitCuisine glacage = new ProduitCuisine("glaçage au chocolat");
        // recette pour une portion de glaçage :
        glacage.ajouterARecette(chocolatNoir, 200);
        glacage.ajouterARecette(beurre,        25);
        glacage.ajouterARecette(sucreGlace,   100);

        System.out.println(glacage);
        System.out.println();

        ProduitCuisine glacageParfume = new ProduitCuisine("glaçage au chocolat parfumé");
        // besoin de 1 portions de glaçage au chocolat et de 2 gouttes
        // d'extrait d'amandes pour 1 portion de glaçage parfumé
        glacageParfume.ajouterARecette(extraitAmandes, 2);
        glacageParfume.ajouterARecette(glacage,        1);

        System.out.println(glacageParfume);
        System.out.println();
        
        
        Recette recette = new Recette("tourte glacée au chocolat");
        // recette pour une portion de tourte glacée :
        recette.ajouter(oeufs,           5);
        recette.ajouter(farine,        150);
        recette.ajouter(beurre,        100);
        recette.ajouter(amandesMoulues, 50);
        recette.ajouter(glacageParfume,  2);

        System.out.println("===  Recette finale  ======\n");
        System.out.println(recette);
        System.out.println();

        afficherQuantiteTotale(recette, beurre);
        System.out.println();

        Recette doubleRecette = recette.adapter(2);
        System.out.println("===  Recette finale x 2 ===\n");
        System.out.println(doubleRecette);
        System.out.println();

        afficherQuantiteTotale(doubleRecette, beurre);
        afficherQuantiteTotale(doubleRecette, oeufs);
        afficherQuantiteTotale(doubleRecette, extraitAmandes);
        afficherQuantiteTotale(doubleRecette, glacage);
        System.out.println();

        System.out.println("===========================\n");
        System.out.println("Vérification que le glaçage n'a pas été modifié :\n");
        System.out.println(glacage);
    }

    private static void afficherQuantiteTotale(Recette recette, Produit produit) {
        String nom = produit.getNom();
        System.out.println("Cette recette contient " +
                           recette.quantiteTotale(nom) + " " + produit.getUnite() + " de " + nom);
    }
}
