package dialogue;

import commandLineMenus.Action;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.*;

public class Dialogue {

	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel des ligues");
		menu.add(menuCompetition()));
		menu.add(menuEquipe());
		menu.add(menuPersonne());
		return menu;
	}
	
	private Menu menuCompetition()
	{
		Menu menu = new Menu("Gérer les compétitions", "c");
		menu.add(afficherCompetitions());
		menu.add(ajouterCompétition());
		menu.add(selectionnerCompétition());
		menu.addBack("q");
		return menu;
	}
	
	private Menu menuEquipe()
	{
		Menu menu = new Menu("Gérer les équipes", "e");
		menu.add(afficherEquipes());
		menu.add(ajouterEquipe());
		menu.add(selectionnerEquipe());
		menu.addBack("q");
		return menu;
	}
	
	private Menu menuPersonne()
	{
		Menu menu = new Menu("Gérer les personnes", "p");
		menu.add(afficherPersonnes());
		menu.add(ajouterPersonne());
		menu.add(selectionnerPersonne());
		menu.addBack("q");
		return menu;
	}
	
	private Option afficherCompetitions()
	{
		return new Option("Afficher les compétitions", "l", () -> {System.out.println(getCompetitions());});
	}
	
}
