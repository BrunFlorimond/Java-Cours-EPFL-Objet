// Utility.distance(x,y) retourne la distance entre x et y
class Utility {
    static int distance(int x, int y) {
        return Math.abs(x - y);
    }
}

class Creature {
    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/
    
	final private String nom;
	private int niveau;
	private int pointsDeVie;
	private int force;
	private int position;
	
	public Creature(String nom, int niveau, int pointsDeVie, int force, int position){
		
		this.nom = nom;
		this.niveau = niveau;
		this.pointsDeVie = pointsDeVie;
		this.force = force;
		this.position = position;
		
	}
	
	public Creature(String nom, int niveau, int pointsDeVie, int force){
		
		this.nom = nom;
		this.niveau = niveau;
		this.pointsDeVie = pointsDeVie;
		this.force = force;
		
	}
	
	public int position(){
		
		return this.position;
		
	}
	
	public void setPosition(int pos){
		
		this.position = pos;
		
	}
	
	public boolean vivant(){
		
		return pointsDeVie > 0;
		
	}
	
	public void addNiveau(int niv){
		
		this.niveau = this.niveau + niv;
		
	}
	
	public int pointsAttaque(){
		
		if (this.vivant() == true){
			
			return this.niveau * this.force;
			
		} else {return 0;}
		
		
		
	}
	
	public void deplacer(int deplacement){
		
		if (this.vivant() == true){
			
			this.position = this.position + deplacement;
			
		}
	}
	
	public void adieux(){
		
		System.out.println(this.nom + " n'est plus !");
		
	}
	
	public void faiblir(int pdv){
		
		if (this.vivant() == true){
			
			this.pointsDeVie = this.pointsDeVie - pdv;
			
			if(this.vivant() != true){
				
				this.pointsDeVie = 0;
				this.adieux();
				
			}
			
		}
		
	}
	
	public String toString(){
		
		String result = this.nom + ", niveau: "+this.niveau+", points de vie: "+this.pointsDeVie+", force: " + this.force+", points d’attaque: "+ this.pointsAttaque() + ", position: "+ this.position(); 
		return result;
	}
	

}

class Dragon extends Creature{
	
	private int porteeFlamme;
	
	public Dragon(String nom, int niveau, int pointsDeVie, int force,int flamme, int position){
		
		super(nom, niveau, pointsDeVie, force, position);
		this.porteeFlamme = flamme;
		
	}
	
	public Dragon(String nom, int niveau, int pointsDeVie, int force,int flamme){
		
		super(nom, niveau, pointsDeVie, force);
		this.porteeFlamme = flamme;
		
	}
	
	public void voler(int pos){
		
		if(super.vivant() == true){
			
			super.setPosition(pos);
			
		}
		
	}
	
	public void souffleSur(Creature creature){
		
			
		if (creature != null && super.vivant() == true && creature.vivant() == true){
			
			if (Utility.distance(super.position(), creature.position()) <= this.porteeFlamme){
				
				int distance = Utility.distance(super.position(), creature.position());
				creature.faiblir(this.pointsAttaque());
				super.faiblir(distance);
				
				if(super.vivant() && !creature.vivant()){
					
					super.addNiveau(1);
					
				}
				
			}
			
		}
		
	}
	
	
	
}

class Hydre extends Creature{
	
	private int longueurCou;
	private int dosePoison;
	
	
	public Hydre(String nom, int niveau, int pointsDeVie, int force,int cou,int poison, int position){
		
		super(nom,niveau,pointsDeVie,force,position);
		this.longueurCou = cou;
		this.dosePoison = poison;
		
	}
	
	public Hydre(String nom, int niveau, int pointsDeVie, int force,int cou,int poison){
		
		super(nom,niveau,pointsDeVie,force);
		this.longueurCou = cou;
		this.dosePoison = poison;
		
	}
	
	public void empoisonne(Creature creature){
		
		if (creature != null && super.vivant() == true && creature.vivant() == true){	
		
			if(Utility.distance(super.position(), creature.position()) <= this.longueurCou ){
				
				creature.faiblir(super.pointsAttaque() + this.dosePoison);
				
				if(super.vivant() && !creature.vivant()){
					
					super.addNiveau(1);
					
				}
				
			}	
			
		}
	}
	
}

/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/
// ======================================================================
class Dragons {
    private static void combat(Dragon dragon, Hydre hydre) {
        hydre.empoisonne(dragon); // l'hydre a l'initiative (elle est plus rapide)
        dragon.souffleSur(hydre);
    }


    public static void main(String[] args) {
        Dragon dragon = new Dragon("Dragon rouge"   , 2, 10, 3, 20         );
        Hydre  hydre =  new Hydre ("Hydre maléfique", 2, 10, 1, 10, 1,  42 );

        System.out.println(dragon);
        System.out.println("se prépare au combat avec :");
        System.out.println(hydre);

        System.out.println();

        System.out.println("1er combat :");
        System.out.println("\t Les crétures ne sont pas à portée, donc ne peuvent pas s'attaquer.");
        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);

        System.out.println();

        System.out.println("Le dragon vole à proximité de l'hydre :");
        dragon.voler(hydre.position() - 1);
        System.out.println(dragon);

        System.out.println();

        System.out.println("L'hydre recule d'un pas :");
        hydre.deplacer(1);
        System.out.println(hydre);

        System.out.println();
        System.out.println("2e combat :");
        System.out.format("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n",
                          "+ l'hydre inflige au dragon une attaque de 3 points",
                          " [ niveau (2) * force (1) + poison (1) = 3 ] ;",
                          "+  le dragon inflige à l'hydre une attaque de 6 points",
                          " [ niveau (2) * force (3) = 6 ] ;",
                          " + pendant son attaque, le dragon perd 2 points de vie supplémentaires",
                          "[ correspondant à la distance entre le dragon et l'hydre : 43 - 41 = 2 ]." );

        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);

        System.out.println();
        System.out.println("Le dragon avance d'un pas :");
        dragon.deplacer(1);
        System.out.println(dragon);

        System.out.println();
        System.out.println("3e combat :");
        System.out.format("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n",

                          "+ l'hydre inflige au dragon une attaque de 3 points",
                          "[ niveau (2) * force (1) + poison (1) = 3 ] ;",
                          "+ le dragon inflige à l'hydre une attaque de 6 points",
                          "[ niveau (2) * force (3) = 6 ] ;",
                          "+ pendant son attaque, le dragon perd 1 point de vie supplémentaire",
                          "[ correspondant à la distance entre le dragon et l'hydre : 43 - 42 = 1 ] ;",
                          "+ l'hydre est vaincue et le dragon monte au niveau 3.");
        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);

        System.out.println();
        System.out.println("4e Combat :");
        System.out.println("\t quand une créature est vaincue, rien ne se passe.");
        combat(dragon, hydre);

        System.out.println();
        System.out.println("Après le combat :");
        System.out.println(dragon);
        System.out.println(hydre);
    }
}
