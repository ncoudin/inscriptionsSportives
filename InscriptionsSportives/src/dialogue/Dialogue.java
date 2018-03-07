package dialogue;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
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
		Menu menu = new Menu("G�rer les comp�titions", "c");
		menu.add(afficherCompetitions());
		menu.add(ajouterCompetition());
		menu.add(selectionnerCompetition());
		menu.addBack("q");
		return menu;
	}
	
	private Menu menuEquipes()
	{
		Menu menu = new Menu("G�rer les �quipes", "e");
		menu.add(afficherEquipes());
		menu.add(ajouterEquipe());
		//menu.add(selectionnerEquipe());
		menu.addBack("q");
		return menu;
	}

	private Option afficherCompetitions()
	{
		return new Option("Afficher les comp�titions", "l", () -> {System.out.println(inscriptions.getCompetitions());});
	}
	
	private Option afficherEquipes()
	{
		return new Option("Afficher les �quipes", "l", () -> {System.out.println(inscriptions.getEquipes());});
	}
	
	private Option afficherCandidats(final Competition competition)
	{
		return new Option("Afficher la comp�tition", "l", 
				() -> 
				{
					System.out.println(competition.getCandidats());
				}
		);
	}

	private Option afficher(final Employee employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	private Option ajouterCompetition()
	{
		return new Option("Ajouter une comp�tition", "a", () -> {inscriptions.createCompetition(getString("nom : "),LocalDate.now(), false);});
	}
	
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une �quipe", "a", () -> {inscriptions.createEquipe(getString("nom : "));});
	}
	
	private Option ajouterEmploye(final Department ligue)
	{
		return new Option("ajouter un employé", "a",
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
		//menu.add(ajouterCandidat());
		//menu.add(supprimerCandidat());
		menu.add(changerNom(competition));
		menu.add(supprimer(competition));
		menu.addBack("q");
		return menu;
	}
	

	private Menu editerEmploye(Employee employe)
	{
		Menu menu = new Menu("Gérer le compte " + employe.getLastName(), "c");
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
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {editerEmploye(element);}
				);
	}
	
	private List<Employee> supprimerEmploye(final Department ligue)
	{
		return new List<>("Supprimer un employé", "s", 
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
	
	private Option changerNom(final Competition competition)
	{
		return new Option("Renommer", "r", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}

	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("S�lectionner une comp�tition", "e", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editerCompetition(element)
				);
	}
	
	private Option supprimer(Competition competition)
	{
		return new Option("Supprimer", "d", () -> {competition.delete();});
	}
	
	private Option changerNom(final Employee employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setLastName(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employee employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setFirstName(getString("Nouveau prénom : "));});
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