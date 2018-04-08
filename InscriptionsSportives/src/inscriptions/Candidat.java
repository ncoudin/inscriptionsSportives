package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import hibernate.Passerelle;

/**
 * Candidat à un événement sportif, soit une personne physique, soit une équipe.
 *
 */

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Candidat implements Comparable<Candidat>, Serializable
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private static final long serialVersionUID = -6035399822298694746L;
	@Transient
	private Inscriptions inscriptions;
	private String nom;
	@OneToMany(targetEntity=Candidat.class, mappedBy = "competitions", fetch=FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL })
    @SortNatural
	private Set<Competition> competitions;
	
	Candidat(Inscriptions inscriptions, String nom)
	{
		this.inscriptions = inscriptions;	
		this.nom = nom;
		competitions = new TreeSet<>();
	}

	/**
	 * Retourne le nom du candidat.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Modifie le nom du candidat.
	 * @param nom
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
		Passerelle.save(this);
	}

	/**
	 * Retourne toutes les compétitions auxquelles ce candidat est inscrit.s
	 * @return
	 */

	public Set<Competition> getCompetitions()
	{
		return Collections.unmodifiableSet(competitions);
	}
	
	boolean add(Competition competition)
	{
		competitions.add(competition);
		Passerelle.save(competition);
		return competitions.add(competition);
	}

	boolean remove(Competition competition)
	{
		competitions.remove(competition);
		Passerelle.delete(competition);
		return competitions.remove(competition);
	}

	/**
	 * Supprime un candidat de l'application.
	 */
	
	public void delete()
	{
		for (Competition c : competitions)
			c.remove(this);
		//inscriptions.remove(this);
		Passerelle.delete(this);
	}
	
	@Override
	public int compareTo(Candidat o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return "\n" + getNom() + " -> inscrit � " + getCompetitions();
	}
}
