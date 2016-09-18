import java.util.ArrayList;
import java.util.Collections;


class Cellule {
    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/

	private String nom;
	private double taille;
	private int energie;
	private String couleur;
	
	public Cellule() {
		
		nom = "Pyrobacculum";
		taille = 10;
		energie = 5;
		couleur = "verte";
		
	}
	
	public Cellule (String n, double t, int e, String c){
		
		this.nom = n;
		this.taille = t;
		this.energie = e;
		this.couleur = c;
		
	}
	
	public Cellule (Cellule cell){
		
		this.nom = cell.nom;
		this.taille = cell.taille;
		this.energie = cell.energie;
		this.couleur = cell.couleur;
		
		
	}
	
	public int getEnergie(){
		
		return this.energie;
		
	}
	
	public void setCouleur(String coul){
		
		this.couleur = coul;
		
	}
	
	public String getCouleur(){
		
		return this.couleur;
		
	}
	
	public void affiche(){
		System.out.println(this.nom+ ", taille = " + this.taille + " microns, énergie = " + this.energie + ", couleur = " + this.couleur);
	}
	
	public Cellule division(){
		
		Cellule cell = new Cellule (this);
		this.energie = this.energie - 1;
		changeColor(cell);
		return cell;
		
	}
	
	public static void changeColor(Cellule cell){
		
		if (cell.getCouleur().equals("verte")){cell.setCouleur("bleue");}
		else if (cell.getCouleur().equals("bleue")){cell.setCouleur("rouge");}
		else if (cell.getCouleur().equals("rouge")){cell.setCouleur("rose bonbon");}
		else if (cell.getCouleur().equals("violet")){cell.setCouleur("verte");}
		else {cell.setCouleur(new String(cell.getCouleur() + " fluo"));}

	}
	
	
}

class Petri
{
	
	ArrayList<Cellule> population;
	
	public Petri(){
		
		this.population = new ArrayList<Cellule>();
		
	}
	
	public void ajouter(Cellule cell){
		
		this.population.add(cell);
		
	}
	
	public void affiche(){
		
		for(int i = 0 ; i < this.population.size(); i++){
			
			this.population.get(i).affiche();
			
		}
		
	}
	
	public void evolue(){
		
		ArrayList<Cellule> cells = new ArrayList<Cellule>();
		int size = this.population.size();
		for (int i = 0; i < size; i++){
			
			Cellule newCell = this.population.get(i).division();
			if(newCell.getEnergie() > 0){
				
				cells.add(newCell);
				
			}
		}
		
		
		this.population.removeIf(m -> m.getEnergie()< 1);
		Collections.reverse(cells);
		this.population.addAll(cells);
		

		
	}
	
	
	
}
	



/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/
class SimulationPetri {
    public static void main(String[] args) {
        Petri assiette = new Petri();

        assiette.ajouter(new Cellule());
        assiette.ajouter(new Cellule("PiliIV", 4, 1, "jaune"));
        System.out.println("Population avant évolution :");
        assiette.affiche();

        assiette.evolue();
        System.out.println("Population après évolution :");
        assiette.affiche();
    }
}

