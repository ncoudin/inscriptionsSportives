package dialogue;

import static commandLineMenus.rendering.examples.util.InOut.getString;
import static commandLineMenus.rendering.examples.util.InOut.getInt;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import commandLineMenus.*;
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
		menu.add(menuPersonnes());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
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
	
	private Menu menuPersonnes()
	{
		Menu menu = new Menu("Gérer les personnes","p");
		menu.add(afficherPersonnes());
		menu.add(ajouterPersonne());
		menu.add(selectionnerPersonne());
		menu.addBack("q");
		return menu;
	}
	
	
	
	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Sélectionner une compétition", "e", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editerCompetition(element)
				);
	}
	
	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Sélectionner une equipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> editerEquipe(element)
				);
	}
	
	private List<Personne> selectionnerPersonne()
	{
		return new List<Personne>("Sélectionner une personne", "e", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonne(element)
				);
	}
	
	
	
	private Menu editerCompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficherCandidats(competition));
		menu.add(ajouterPersonneCompetition(competition));
		menu.add(ajouterEquipeCompetition(competition));
		menu.add(supprimerCandidat(competition));
		menu.add(modifierCompetition(competition));
		menu.add(supprimer(competition));
		menu.addBack("q");
		return menu;
	}
	
	private Menu editerEquipe(Equipe equipe)
	{
		Menu menu = new Menu("Editer " + equipe.getNom());
		menu.add(afficherMembres(equipe));
		menu.add(ajouterMembre(equipe));
		menu.add(supprimerMembre(equipe));
		menu.add(modifierEquipe(equipe));
		menu.add(supprimer(equipe));
		menu.addBack("q");
		return menu;
	}
	
	private Menu editerPersonne(Personne personne)
	{
		Menu menu = new Menu("Editer " + personne.getNom());
		menu.add(modifierPersonne(personne));
		menu.add(supprimer(personne));
		menu.addBack("q");
		return menu;
	}
	
	
	
	private Option ajouterCompetition()
	{
		return new Option("Ajouter une compétition", "a", () -> {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateCloture = InOut.getString("Entrez la date de clôture 'yyyy-MM-dd' : ");
			Date localDate;
			try {
				localDate = formatter.parse(dateCloture);
				inscriptions.createCompetition(getString("nom : "),localDate,getInt("0 - Compétition de personnes \n1 - Compétition d'équipes : ")==0);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une équipe", "a", () -> {inscriptions.createEquipe(getString("nom : "));});
	}
	
	private Option ajouterPersonne()
	{
		return new Option("Ajouter une personne", "a", () -> {inscriptions.createPersonne(getString("nom : "),getString("prenom : "),getString("mail : "));});
	}
	
	private Menu ajouterPersonneCompetition(Competition competition)
	{
		return new List<Personne>("Ajouter une personne", "p", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private Menu ajouterEquipeCompetition(Competition competition)
	{
		return new List<Equipe>("Ajouter une équipe", "a", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private Menu ajouterMembre(Equipe equipe)
	{
		return new List<Personne>("Ajouter un membre", "a", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {equipe.add(element);}
				);
	}
	
	
	
	private Option afficherMembres(final Equipe equipe)
	{
		return new Option("Afficher l'équipe", "l", 
				() -> 
				{
					System.out.println(equipe.getMembres());
				}
		);
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

	private Option afficherPersonnes()
	{
		return new Option("Afficher les personnes", "l", () -> {System.out.println(inscriptions.getPersonnes());});
	}
	
	
	private Option modifierCompetition(final Competition competition)
	{
		return new Option("Modifier compétition", "r", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}
	
	/*on peut ici renommer, supprimmer ou selectionner une équipe.*/
	private Option modifierEquipe(final Equipe equipe)
	{
		return new Option("Renommer équipe", "r", 
				() -> {equipe.setNom(getString("Nouveau nom : "));});
	}

	private Option modifierPersonne(final Personne personne)
	{
		return new Option("Modifier personne", "r", 
				() -> {personne.setNom(getString("Nouveau nom : "));
					   personne.setPrenom(getString("Nouveau prenom : "));
					   personne.setMail(getString("Nouveau mail : "));});
	}

	
	private Option supprimer(Competition competition)
	{
		return new Option("Supprimer", "d", () -> {competition.delete();});
	}
	
	private Option supprimer(Personne personne)
	{
		return new Option("Supprimer", "d", () -> {personne.delete();});
	}
	
	private Option supprimer(Equipe equipe)
	{
		return new Option("Supprimer", "d", () -> {equipe.delete();});
	}
	
	private List<Candidat> supprimerCandidat(final Competition competition)
	{
		return new List<Candidat>("Supprimer un candidat", "s", 
				() -> new ArrayList<>(competition.getCandidats()),
				(index, element) -> {competition.remove(element);}
				);
	}
	
	private Menu supprimerMembre(Equipe equipe)
	{
		return new List<Personne>("Supprimer un membre", "s", 
				() -> new ArrayList<>(equipe.getMembres()),
				(index, element) -> {equipe.remove(element);}
				);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}

	private Option quitterEtEnregistrer()
    {
        return new Option("Quitter et enregistrer", "q", 
                () -> 
                {
                    try
                    {
                        inscriptions.sauvegarder();
                        Action.QUIT.optionSelected();
                    } 
                    catch (IOException e)
                    {
                        System.out.println("Impossible d'effectuer la sauvegarde");
                    }
                }
            );
    }
	
	
	
}