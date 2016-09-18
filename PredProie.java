import java.util.Scanner;

class PredProie {

    private final static double TAUX_ATTAQUE_INIT = 0.01;
    private final static double TAUX_CROISSANCE_LAPINS = 0.3;
    private final static double TAUX_CROISSANCE_RENARDS = 0.008;
    private final static double TAUX_MORTALITE = 0.1;
    private final static int DUREE = 50;

    public static void afficheStatus(int temps, double lapins, double renards) {
        System.out.print("Après ");
        System.out.print(temps);
        System.out.print(" mois, il y a ");
        System.out.format("%.3f", lapins );
        System.out.print(" lapins et ");
        System.out.format("%.3f", renards );
        System.out.println(" renards");
    }

    public static void afficheTauxDAttaque(double tauxAttaque) {
        System.out.println("");
        System.out.print("***** Le taux d'attaque vaut ");
        System.out.format("%.2f", tauxAttaque * 100);
        System.out.println("%");
    }

    /*****************************************************
     * Compléter le code à partir d'ici
     *****************************************************/

    /*
    System.out.print("Combien de ");
    System.out.print(" au départ (>=");
    System.out.print(") ? ");
    System.out.println(" ont été en voie d'extinction");
    System.out.println("mais la population est remontée ! Ouf !");
    System.out.println(" ont disparus :-(");
    System.out.println("Les lapins et les renards ont des populations stables.");
    */
	public static double entrerPopulation(String animal, double nombreMin, Scanner clavier) {
            // A COMPLETER
		int nbAnimal;
		double txInit = 0.01;
		do{
		    // completer en utilisant cette ligne sans la modifier
			System.out.print("Combien de " + animal +  "  au depart (>=" + nombreMin + ") ? ");
			nbAnimal = clavier.nextInt();
			
		} while (nbAnimal < nombreMin);

			
			// A modifier pour un retour correct:
			return nbAnimal;
			
	}
		
	public static void afficheResultat(String animal, boolean menacesExtinction, boolean disparus, boolean remonte) {
		
		if (menacesExtinction == true){
			
			System.out.println("Les " + animal + " ont été en voie d'extinction");
			
		}
		
		if (menacesExtinction == true && remonte == true){
			
			System.out.println("mais la population est remontée ! Ouf !");
			
		} 
		
		if ( disparus == true) {
			
			System.out.println("et les " + animal +" ont disparus :-(");
			
		}
		

		
	}
	
	public static void stabilitePopulation(boolean disparition[], boolean voieExtinction[], boolean reprise[]){
		
		boolean total = false;
		
		for (int i =0 ; i < disparition.length; i++){
			
			total = total || disparition[i] || voieExtinction[i] || reprise[i];
			
			
		}
		
		if(total == false){
			
		    System.out.println("Les lapins et les renards ont des populations stables.");
			
		}
		
	}
	
	public static double calculerLapins(double lapins, double renards, double tauxAttaque) {
		// A COMPLETER
		return plafonner(lapins * (1.0 + TAUX_CROISSANCE_LAPINS - tauxAttaque * renards ));
		// A modifier pour un retour correct: 
			
	}
	
	public static double calculerRenards(double lapins, double renards, double tauxAttaque) {
		// A COMPLETER
		return plafonner(renards * (1.0 + tauxAttaque * lapins * TAUX_CROISSANCE_RENARDS - TAUX_MORTALITE));
		// A modifier pour un retour correct: 

	}
	
	public static double plafonner(double value){
		
		if (value > 0) {return value;} else {return 0;}
		
	}
	
	
	public static void simule(double renardsInit, double lapinsInit, double tauxInit, double tauxFin)	{
		double renards = renardsInit;
		double lapins = lapinsInit;
		
		
		
		for (double j = tauxInit; j <= tauxFin; j = j+0.01){
			int periode = 0;		
			afficheTauxDAttaque(j);
			
			boolean voieExtinction[] = {false,false};
			boolean disparition[] = {false,false};
			boolean reprise[] = {false,false};
			double qty[] = {lapins,renards};
			double qtyAv[] = {lapins,renards};
			String[] nomAnim = {"renards","lapins"};
			
			
			do{
				
				qtyAv[0] = renards;
				qtyAv[1] = lapins;
				//afficheStatus(periode, lapins, renards);
				periode++;
				double renards2 = calculerRenards(lapins,renards,j);
				double lapins2 = calculerLapins(lapins,renards,j);
				
				
				qty[0] = renards2;
				qty[1] = lapins2;
				
				for(int i = 0; i < voieExtinction.length; i++){
					
					if(qtyAv[i] >= 5 && qty[i] < 2){
						
						disparition[i] = disparition[i] || true;
						voieExtinction[i] = voieExtinction[i] || true;
						
					}
					
				}
			
				renards = renards2;
				lapins = lapins2;
				
				qty[0] = renards;
				qty[1] = lapins;
				
				for (int i = 0; i < voieExtinction.length; i++){
					
					voieExtinction[i] = voieExtinction[i] || inf5(qty[i]);
					reprise[i] = reprise[i] || setReprise(qty[i],voieExtinction[i]);
					disparition[i] = disparition[i] || disparu(qty[i]);
															
				}
								
				
				
				if(renards < 2) 
				{
					renards = 0;
					
					
				} else if (lapins < 2)
				{
					
					lapins = 0;	
					
					
				}
				
				if (renards < 2 && lapins < 2){break;}
								

				
				
			} while (periode<DUREE);
			
			afficheStatus(periode, lapins, renards);
			stabilitePopulation(disparition,voieExtinction,reprise);
			//System.out.println("disparition lapin : " + disparition[1]);
			for (int i = 0; i < nomAnim.length; i++){
				
				afficheResultat(nomAnim[i],voieExtinction[i],disparition[i],reprise[i]);
				
			}
			
			periode = 0;
			lapins = lapinsInit;
			renards = renardsInit;
			
		}
		

	}
	
	
	public static boolean disparu(double qty){
		
		return qty<2;
		
	}
	
	public static boolean setReprise(double qty, boolean extinction){
		

		return qty > 5 && extinction == true;

		
	}
	
	
	public static boolean inf5(double qty){
		
		return qty<5;
		
	}
	
	
    /*******************************************
     * ne rien modifier après cette ligne
     *******************************************/

    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);

        // Saisie des populations initiales
        double renardInit = entrerPopulation("renards", 2.0, clavier);
        double lapinsInit = entrerPopulation("lapins", 5.0, clavier);

        // ===== PARTIE 1 =====
        // Première simulation
        // Evolution de la population avec les paramètres initiaux

        simule(renardInit, lapinsInit, TAUX_ATTAQUE_INIT, TAUX_ATTAQUE_INIT);

        // ===== PARTIE 2 =====
        // Variation du taux d'attaque
        System.out.println("");

        double tauxInit = 0.0;
        double tauxFin = 0.0;

        do {
            System.out.print("taux d'attaque au départ en % (entre 0.5 et 6) ? ");
            tauxInit = clavier.nextDouble();
        } while ((tauxInit < 0.5) || (tauxInit > 6.0));

        do {
            System.out.print("taux d'attaque à la fin  en % (entre ");
            System.out.print(tauxInit);
            System.out.print(" et 6) ? ");
            tauxFin = clavier.nextDouble();
        } while ((tauxFin < tauxInit) || (tauxFin > 6.0));

        tauxInit /= 100.0;
        tauxFin  /= 100.0;


        simule(renardInit, lapinsInit, tauxInit, tauxFin);
    }
}
