package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import hibernate.Passerelle;

/**
 * Repr�sente une comp�tition, c'est-�-dire un ensemble de candidats 
 * inscrits � un �v�nement, les inscriptions sont closes � la date dateCloture.
 *
 */

@Entity
public class Competition implements Comparable<Competition>, Serializable
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private static final long serialVersionUID = -2882150118573759729L;
	@Transient
	private Inscriptions inscriptions;
	private String nom;
	@ManyToMany
	@Cascade(value = { CascadeType.ALL })
    @SortNatural
	private Set<Candidat> candidats;
	
	private Date dateCloture;
	private boolean enEquipe = false;

	Competition(Inscriptions inscriptions, String nom, Date dateCloture, boolean enEquipe)
	{
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
	}
	
	/**
	 * Retourne le nom de la comp�tition.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Modifie le nom de la comp�tition.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom ;
		Passerelle.save(this);
	}
	
	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, 
	 * faux si les inscriptions sont closes.
	 * @return
	 * @throws Exception 
	 */
	
	public boolean inscriptionsOuvertes() 
	{
		// TODO retourner vrai si et seulement si la date syst�me est ant�rieure � la date de cl�ture.
		Date date = new Date();
		try {
		if(date.after(getDateCloture()))
			return false;
		}
		catch(Exception e)
		{
			System.out.println("La date n'est pas donn�e !");
		}
		return true;
	}
	
	/**
	 * Retourne la date de cloture des inscriptions.
	 * @return
	 */
	
	public Date getDateCloture()
	{
		return dateCloture;
	}
	
	/**
	 * Est vrai si et seulement si les inscriptions sont r�serv�es aux �quipes.
	 * @return
	 */
	
	public boolean estEnEquipe()
	{
		return enEquipe;
	}
	
	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la reculer 
	 * mais pas de l'avancer.
	 * @param dateCloture
	 */
	
	public void setDateCloture(Date dateCloture)
	{
		// TODO v�rifier que l'on avance pas la date.
		if (!dateCloture.after(this.dateCloture))
		{
			Passerelle.save(this);
			this.dateCloture = dateCloture;
		}
		else
			System.out.println("Impossible d'avancer la date de cloture !");
	}
	
	/**
	 * Retourne l'ensemble des candidats inscrits.
	 * @return
	 */
	
	public Set<Candidat> getCandidats()
	{
		return Collections.unmodifiableSet(candidats);
	}
	
	/**
	 * Inscrit un candidat de type Personne � la comp�tition. Provoque une
	 * exception si la comp�tition est r�serv�e aux �quipes ou que les 
	 * inscriptions sont closes.
	 * @param personne
	 * @return
	 */
	
	public boolean add(Personne personne)
	{
		// TODO v�rifier que la date de cl�ture n'est pas pass�e
		if (enEquipe)
			throw new RuntimeException();
		if(!inscriptionsOuvertes())
			throw new RuntimeException();
		personne.add(this);
		candidats.add(personne);
		Passerelle.save(personne);
		return candidats.add(personne);
	}

	/**
	 * Inscrit un candidat de type Equipe � la comp�tition. Provoque une
	 * exception si la comp�tition est r�serv�e aux personnes ou que 
	 * les inscriptions sont closes.
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe)
	{
		// TODO v�rifier que la date de cl�ture n'est pas pass�e
		if (!enEquipe)
			throw new RuntimeException();
		if(!inscriptionsOuvertes())
			throw new RuntimeException();
		equipe.add(this);
		candidats.add(equipe);
		Passerelle.save(equipe);
		return candidats.add(equipe);
	}

	/**
	 * D�sinscrit un candidat.
	 * @param candidat
	 * @return
	 */
	
	public boolean remove(Candidat candidat)
	{
		candidat.remove(this);
		candidats.remove(candidat);
		Passerelle.delete(candidat);
		return candidats.remove(candidat);
	}
	
	/**
	 * Supprime la comp�tition de l'application.
	 */
	
	public void delete()
	{
		for (Candidat candidat : candidats)
			remove(candidat);
		//inscriptions.remove(this);
		Passerelle.delete(this);
	}
	
	@Override
	public int compareTo(Competition o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return getNom();
	}
}