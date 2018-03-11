package dialogue;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import commandLineMenus.*;
import commandLineMenus.examples.employees.core.Department;
import commandLineMenus.examples.employees.core.Employee;
import commandLineMenus.examples.employees.core.ImpossibleToSaveException;
import commandLineMenus.examples.employees.core.ManageEmployees;
import commandLineMenus.examples.employees.userDialog.PersonnelConsole;
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.*;

public class Dialogue
{
	private Inscriptions inscriptions;
	
	public Dialogue(Inscriptions inscriptions)
	{
		this.inscriptions = inscriptions;
	}
	
	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Menu principal");
		menu.add(menuCompetitions());
		menu.add(menuEquipes());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		//menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Menu menuCompetitions()
	{
		Menu menu = new Menu("Gérer les compétitions", "c");
		menu.add(afficherCompetitions());
		menu.add(ajouterCompetition());
		menu.add(selectionnerCompetition());
		menu.addBack("q");
		return menu;
	}
	
	private Menu menuEquipes()
	{
		Menu menu = new Menu("Gérer les équipes", "e");
		menu.add(afficherEquipes());
		menu.add(ajouterEquipe());
		menu.add(selectionnerEquipe());
		menu.addBack("q");
		return menu;
	}

	private Option afficherCompetitions()
	{
		return new Option("Afficher les compétitions", "l", () -> {System.out.println(inscriptions.getCompetitions());});
	}
	
	private Option afficherEquipes()
	{
		return new Option("Afficher les équipes", "l", () -> {System.out.println(inscriptions.getEquipes());});
	}
	
	private Option afficherCandidats(final Competition competition)
	{
		return new Option("Afficher la compétition", "l", 
				() -> 
				{
					System.out.println(competition.getCandidats());
				}
		);
	}

	private Option afficher(final Employee employe)
	{
		return new Option("Afficher l'employe", "l", () -> {System.out.println(employe);});
	}

	private Option ajouterCompetition()
	{
		return new Option("Ajouter une compétition", "a", () -> {inscriptions.createCompetition(getString("nom : "),LocalDate.now(), false);});
	}
	
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une équipe", "a", () -> {inscriptions.createEquipe(getString("nom : "));});
	}
	
	private Option ajouterEmploye(final Department ligue)
	{
		return new Option("ajouter un employe", "a",
				() -> 
				{
					ligue.addEmploye(getString("nom : "), 
						getString("prenom : "), getString("mail : "), 
						getString("password : "));
				}
		);
	}
	
	private Menu editerCompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficherCandidats(competition));
		menu.add(selectionnerPersonne(competition));
		menu.add(selectionnerEquipe(competition));
		//menu.add(supprimerCandidat());
		menu.add(changerNom(competition));
		menu.add(supprimer(competition));
		menu.addBack("q");
		return menu;
	}
	
	private Menu selectionnerPersonne(Competition competition)
	{
		return new List<Personne>("Ajouter une personne", "o", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonneCompetition(competition,element)
				);
	}
	
	private Menu editerPersonneCompetition(Competition competition,Personne personne)
	{
		Menu menu = new Menu("Ajouter " + personne.getNom());
		menu.add(ajouterPersonne(competition,personne));
		menu.addBack("q");
		return menu;
		
	}
	
	private Option ajouterPersonne(Competition competition, Personne personne)
	{
		return new Option("Valider", "a", () -> {competition.add(personne);});
	}
	
	private Menu selectionnerEquipe(Competition competition)
	{
		return new List<Equipe>("Ajouter une équipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> editerEquipeCompetition(competition,element)
				);
	}
	
	private Menu editerEquipeCompetition(Competition competition,Equipe equipe)
	{
		Menu menu = new Menu("Ajouter " + equipe.getNom());
		menu.add(ajouterEquipe(competition,equipe));
		menu.addBack("q");
		return menu;
		
	}
	
	private Option ajouterEquipe(Competition competition, Equipe equipe)
	{
		return new Option("Valider", "a", () -> {competition.add(equipe);});
	}
	
	private Menu editerEquipe(Equipe equipe)
	{
		Menu menu = new Menu("Editer " + equipe.getNom());
		menu.add(changerNom(equipe));
		menu.add(supprimer(equipe));
		menu.add(membresEquipe(equipe));
		menu.addBack("q");
		return menu;
	}

	private Menu editerEmploye(Employee employe)
	{
		Menu menu = new Menu("Gerer le compte " + employe.getLastName(), "c");
		menu.add(afficher(employe));
		menu.add(changerNom(employe));
		menu.add(changerPrenom(employe));
		menu.add(changerMail(employe));
		menu.add(changerPassword(employe));
		menu.addBack("q");
		return menu;
	}


	private List<Employee> modifierEmploye(final Department ligue)
	{
		return new List<>("Modifier un employÃ©", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {editerEmploye(element);}
				);
	}
	
	private List<Employee> supprimerEmploye(final Department ligue)
	{
		return new List<>("Supprimer un employÃ©", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}
	
	/*private Option ajouterCandidat(Competition competition)
	{
		return new Option("Ajouter un candidat", "a", () -> {competition.add(getString("nom : "));});
	}*/
	
	private List<Employee> changerAdministrateur(final Department ligue)
	{
		return new List<Employee>("Changer d'administrateur", "c", 
				() -> new ArrayList<>(ligue.getEmployes()), 
				(index, element) -> {ligue.setAdministrator(element);}
				);
	}		
	
	/*on peut ici renommer, supprimmer ou selectionner une compétition.*/
	private Option changerNom(final Competition competition)
	{
		return new Option("Renommer", "r", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}

	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Sélectionner une compétition", "e", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editerCompetition(element)
				);
	}
	
	private Option supprimer(Competition competition)
	{
		return new Option("Supprimer", "d", () -> {competition.delete();});
	}
	
	
	/*on peut ici renommer, supprimmer ou selectionner une équipe.*/
	private Option changerNom(final Equipe equipe)
	{
		return new Option("Renommer", "r", 
				() -> {equipe.setNom(getString("Nouveau nom : "));});
	}

	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Sélectionner une equipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> editerEquipe(element)
				);
	}
	
	private Option supprimer(Equipe equipe)
	{
		return new Option("Supprimer", "d", () -> {equipe.delete();});
	}
	
	private Option membresEquipe(Equipe equipe)
	{
		return new Option("Supprimer", "d", () -> {equipe.delete();});
	}
	
	/*on peut ici renommer, supprimmer ou selectionner un employe.*/
	private Option changerNom(final Employee employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setLastName(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employee employe)
	{
		return new Option("Changer le prÃ©nom", "p", () -> {employe.setFirstName(getString("Nouveau prÃ©nom : "));});
	}
	
	private Option changerMail(final Employee employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employee employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}

	
	public static void main(String[] args)
	{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Dialogue personnelConsole = new Dialogue(inscriptions);
		//if (personnelConsole.verifiePassword())
			personnelConsole.start();
		
	}
}