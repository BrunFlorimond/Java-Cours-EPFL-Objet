/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
class Patient{
	
	private double hauteur;
	private double masse;
	
	public void init(double poids, double taille){
		
		if(poids > 0 && taille >0){
			
			this.hauteur = taille;
			this.masse = poids;
			
		} else {
			
			this.hauteur = 0;
			this.masse = 0;
			
		}
		
				
	}
	
	public void afficher(){
		
		System.out.printf("Patient : %.1f kg pour %.1f m",this.masse,this.hauteur);
		System.out.println();
	}
	
	public double poids(){
		
		return this.masse;
		
	}
	
	public double taille(){
		
		return this.hauteur;
		
	}
	
	public double imc(){
		
		if (this.hauteur == 0){
			
			return 0;
		}else {
			
			return this.masse/Math.pow(this.hauteur, 2);

			
		}
		
	}
	
	
	
}

/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
class Imc {
    public static void main(String[] args) {

        Patient quidam = new Patient();
        quidam.init(74.5, 1.75);
        quidam.afficher();
        System.out.println("IMC : " + quidam.imc());
        quidam.init( -2.0, 4.5);
        quidam.afficher();
    }
}