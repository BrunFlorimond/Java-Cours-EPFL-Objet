import java.util.ArrayList;

class Time
/* Représente le jour et l'heure d'un évènement.
 * Les heures sont représentées en double depuis minuit.
 * Par exemple 14:30 est représenté 14.5.
 */
{
    private final String day_;
    private final double hour_;

    // Constructeur à partir du jour et de l'heure
    public Time(String jour, double heure) {
        day_ = jour;
        hour_ = heure;
    }

    // Affichage
    public void print() {
        System.out.print(day_ + " à ");
        Time.printTime(hour_);
    }

    // Pour connaître le jour
    public String day() {
        return day_;
    }

    // Pour connaître l'heure
    public double hour()  {
        return hour_;
    }

    // Affiche un double en format heures:minutes
    public static void printTime(double hour) {
        int hh = (int)hour;
        int mm = (int)(60. * (hour - hh));
        System.out.format("%02d:%02d", hh, mm);
    }
}


/*******************************************
 * Complétez le programme à partir d'ici.
 *******************************************/
class Activity
{
	
	private final String lieu;
	private final double duree;
	private final Time horaire;
	
	public Activity(String l, String jour, double debut,double d){
		
		this.lieu = l;
		this.duree = d;
		this.horaire = new Time(jour,debut);
		
	}
	
	public Activity(Activity activite){
		
		this.lieu = activite.getLocation();
		this.duree = activite.getDuration();
		this.horaire = new Time(activite.getTime().day(),activite.getTime().hour());
		
	}
	
	//TODO GetLocation
	
	public String getLocation(){
		
		return this.lieu;
		
	}
	
	//TODO GetTime
	
	public Time getTime(){
		
		return this.horaire;
	}
	
	//TODO GetDuration
	
	public double getDuration(){
		
		return this.duree;
		
	}
	
	public String getDay(){
		
		return this.horaire.day();
		
	}
	
	//TODO
	//une méthode conflicts prenant en argument une activité ; cette méthode retourne true si l'activité courante est en conflit horaire avec l'activité passée en paramètre, et false sinon ;
	//deux activités sont en conflit si elles ont lieu le même jour et que leurs plages horaires se chevauchent ; deux activités telles que l'heure de fin de l'une est identique à l'heure de début de l'autre ne sont pas en conflit ;
	
	public boolean conflicts(Activity activite){
		
		return checkDay(activite) && checkHour(activite);
		
		
	}
	
	public void print(){
		
		System.out.print("le ");
		this.horaire.print();
		System.out.print(" en " + this.getLocation());
		System.out.print(", durée ");
		Time.printTime(this.getDuration());
	}
	
	private boolean checkDay(Activity activite){
		
		return this.horaire.day().equals(activite.getDay());
		
	}
	
	private  boolean checkHour(Activity activite){
		
		ArrayList<Double> intervalCourse1 = intervalCourse(this);
		ArrayList<Double> intervalCourse2 = intervalCourse(activite);
		double debutCours1 = intervalCourse1.get(0);
		double debutCours2 = intervalCourse2.get(0);
		double finCours1 = intervalCourse1.get(1);
		double finCours2 = intervalCourse2.get(1);
		
		
		
		//Le debut du cours 1 est compris entre le debut et la fin du cours 2
		boolean debutGene = (debutCours1 >= debutCours2 && debutCours1 < finCours2) || (debutCours1 == debutCours2);
		
		//La fin du cours 1 est comprise entre le debut et la fin du cours 2
		boolean finGene = (finCours1 > debutCours2 && finCours1 <= finCours2) || (finCours1 == finCours2);
		
		boolean isInside = (debutCours1 >= debutCours2 && finCours1 <= finCours2) || (debutCours2 >= debutCours1 && finCours2 <= finCours1);
		
		return debutGene || finGene || isInside;
		
	
	}
	
	private static ArrayList<Double> intervalCourse(Activity activite){
		
		ArrayList<Double> intervalCourse = new ArrayList<Double>();
		
		intervalCourse.add(activite.getTime().hour());
		
		intervalCourse.add(activite.getTime().hour() + activite.getDuration());
		
		return intervalCourse;
		
	}
	
};

class Course
{
	private final String id;
	private final String nom;
	private final Activity amphi;
	private final Activity td;
	private final int credits;
	
	public Course(String id,String nom, Activity amphi,Activity td, int credits )
	{
		this.id = id;
		this.nom = nom;
		this.amphi = amphi;
		this.td = td;
		this.credits = credits;	
		
		System.out.println("Nouveau cours : " +id);
	}
	
	public String getId(){
		
		return this.id;
	}
	
	public String getTitle(){
		
		return this.nom;
	}
	
	public int getCredits(){
		
		return this.credits;
	}
	
	public double workload(){
		
		return this.amphi.getDuration() + this.td.getDuration();
		
	}
	
	public boolean conflicts(Activity activite){
		
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(this.amphi);
		activities.add(this.td);
		boolean result = false;
		
		for (Activity activity : activities){
			
				result = result || activite.conflicts(activity);
			
		}
		
		return result;
		
	}
	
	public boolean conflicts(Course cours){
		
		boolean result = false;
		
		return result || cours.conflicts(this.amphi) || cours.conflicts(this.td);
	}
	
	public void print(){
		System.out.print(this.id +": " + this.nom + " - cours ");
		this.amphi.print();
		System.out.print(", exercices ");
		this.td.print();
		System.out.println(". crédits : " + this.credits);
	}
		
};

class StudyPlan implements Cloneable
{
	
	private ArrayList<Course> cours;
	
	public StudyPlan(){
		
		this.cours = new ArrayList<Course>();
		
	}
	
	public StudyPlan(StudyPlan plan){
		
		this.cours = (ArrayList<Course>) plan.getCours().clone();
		
	}
	
	public ArrayList<Course> getCours(){
		
		return this.cours;
		
	}
	
	public void addCourse(Course cours){
		
		this.cours.add(cours);
		
	}
	
	public boolean conflicts(String id, ArrayList<String> listId){
		
		boolean conflict = false;
		
		Course cours1 = getCourse(id);
		
		if (cours1 != null){
		
			for (String ident : listId){
				
				Course cours2 = getCourse(ident);
				
				if(cours2 != null){
					
					conflict = conflict || cours1.conflicts(cours2);
					
					
				}
			}
		}
		return conflict;
		
	}
	
	public void print(String id){
		
		Course cours = getCourse(id);
		
		if (cours != null){
			
			cours.print();
			
		} else {
			
			System.out.print("");
		}
		
	}
	
	public int credits(String id){
		
		Course cours = getCourse(id);
		int credits = 0;
		
		if (cours != null){
			
			credits = cours.getCredits();
			
		}
		
		return credits;
		
	}
	
	public double workload(String id){
		
		Course cours = getCourse(id);
		double workload = 0;
		
		if (cours != null){
			
			workload = cours.workload();
			
		}
		
		return workload;
		
	}
	
	public void printCourseSuggestions(ArrayList<String> listeCours){
		int nbCours = 0;
		
		for (Course coursPlan : this.cours){

				
				boolean result = false;
				
				
				for (String coursId : listeCours){
					
					Course cours = getCourse(coursId);
					
					if(cours != null){
						
						result = result || cours.conflicts(coursPlan);
						
					}
					
					
					
					/*
					System.out.println("cours comparés : ");
					cours.print();
					coursPlan.print();
					System.out.println(cours.conflicts(coursPlan));
					System.out.println("fin comparaison");
					
					*/
				}
				
				if (result == false){
					
					coursPlan.print();
					nbCours++;
					
				}
				
			
		
			
		}
		
		if (nbCours == 0){
			
			System.out.println("Aucun cours n'est compatible avec la sélection de cours.");
		}
		
		
		
	}
	
	public Course getCourse(String id){
		
		Course coursTrouve = null;
		
		for (Course cours : this.cours){
			
			if (cours.getId().equals(id)){
				
				coursTrouve = cours;
				break;
				
			}
			
		}
		
		return coursTrouve;
		
	}
	
	
	
};

class Schedule
{
	
	private ArrayList<String> coursChoisis;
	private StudyPlan planEtude;
	
	public Schedule(StudyPlan plan){
			
		
		this.planEtude = new StudyPlan(plan);
		this.coursChoisis = new ArrayList<String>();
		
	}
	
	public Schedule(Schedule schedule){
		
		StudyPlan newStudyPlan = new StudyPlan(schedule.getStudyPlan());
		
		this.coursChoisis = (ArrayList<String>) schedule.getSelectedCourses().clone();
		this.planEtude = new StudyPlan(newStudyPlan);
		
	}
	
	public StudyPlan getStudyPlan(){
		
		return this.planEtude;
		
	}
	
	public ArrayList<String> getSelectedCourses(){
		
		return this.coursChoisis;
		
	}
	
	public boolean addCourse(String cours){
		
		boolean added = false;
		
		if(!this.planEtude.conflicts(cours, coursChoisis)){
			
			this.coursChoisis.add(cours);
			
			added = true;
			
		}
		
		return added;
		
	}
	
	public double computeDailyWorkload(){
		
		double workload = 0;
		
		for (String coursID : this.coursChoisis){
			
			workload = workload + this.planEtude.workload(coursID);
			
		}
		
		workload = workload/5;
		
		return workload;
		
	}
	
	public int computeTotalCredits(){
		
		int credits = 0;
		
		for (String coursID : this.coursChoisis){
			
			credits = credits + this.planEtude.credits(coursID);
			
		}
		
		return credits;
		
	}
	
	public void print(){
		for(String coursID : this.coursChoisis){
			
			if(this.planEtude.getCourse(coursID)!= null){this.planEtude.getCourse(coursID).print();}
			
		}
		System.out.println("Total de crédits   : " + this.computeTotalCredits());
		System.out.print("Charge journalière : "); Time.printTime(this.computeDailyWorkload());System.out.println();
		System.out.println("Suggestions :");
		this.planEtude.printCourseSuggestions(this.coursChoisis);
		System.out.println();
		
	}
	
	
};

/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/
class Planning {
    public static void main(String[] args) {
        // Quelques activités
        Activity physicsLecture  = new Activity("Central Hall", "lundi",  9.25, 1.75);
        Activity physicsExercises = new Activity("Central 101" , "lundi", 14.00, 2.00);

        Activity historyLecture  = new Activity("North Hall", "lundi", 10.25, 1.75);
        Activity historyExercises = new Activity("East 201"  , "mardi",  9.00, 2.00);

        Activity financeLecture  = new Activity("South Hall",  "vendredi", 14.00, 2.00);
        Activity financeExercises = new Activity("Central 105", "vendredi", 16.00, 1.00);

        // On affiche quelques informations sur ces activités
        System.out.print("L'activité physicsLecture a lieu ");
        physicsLecture.print();
        System.out.println(".");

        System.out.print("L'activité historyLecture a lieu ");
        historyLecture.print();
        System.out.println(".");

        if (physicsLecture.conflicts(historyLecture)) {
            System.out.println("physicsLecture est en conflit avec historyLecture.");
        } else {
            System.out.println("physicsLecture n'est pas en conflit avec historyLecture.");
        }
        System.out.println();

        // Création d'un plan d'étude
        StudyPlan studyPlan = new StudyPlan();
        Course physics = new Course("PHY-101", "Physique", physicsLecture, physicsExercises, 4);
        studyPlan.addCourse(physics);
        Course history = new Course("HIS-101", "Histoire", historyLecture, historyExercises, 4);
        studyPlan.addCourse(history);
        Course finance = new Course("ECN-214", "Finance" , financeLecture, financeExercises, 3);
        studyPlan.addCourse(finance);

        // Première tentative d'emploi du temps
        Schedule schedule1 = new Schedule(studyPlan);
        schedule1.addCourse(finance.getId());
        System.out.println("Emploi du temps 1 :");
        schedule1.print();
        /* On ne sait pas encore très bien quoi faire : on essaye donc
         * sur une copie de l'emploi du temps précédent.
         */
        Schedule schedule2 = new Schedule(schedule1);
        schedule2.addCourse(history.getId());
        System.out.println("Emploi du temps 2 :");
        schedule2.print();

        // Un troisième essai
        Schedule schedule3 = new Schedule(studyPlan);
        schedule3.addCourse(physics.getId());
        System.out.println("Emploi du temps 3 :");
        schedule3.print();
    }
}

