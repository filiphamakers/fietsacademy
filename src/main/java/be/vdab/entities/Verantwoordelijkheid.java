package be.vdab.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "verantwoordelijkheden")
public class Verantwoordelijkheid implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	@ManyToMany
	@JoinTable(
			name = "docentenverantwoordelijkheden", 
			joinColumns = @JoinColumn(name = "verantwoordelijkheidid"), 
			inverseJoinColumns = @JoinColumn(name = "docentid"))
	private Set<Docent> docenten = new LinkedHashSet<>();

	public void add(Docent docent) {
		docenten.add(docent);
		if (!docent.getVerantwoordelijkheden().contains(this)) {
			docent.add(this);
		}
	}

	public void remove(Docent docent) {
		docenten.remove(docent);
		if (docent.getVerantwoordelijkheden().contains(this)) {
			docent.remove(this);
		}
	}

	// GETTERS
	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Set<Docent> getDocenten() {
		return Collections.unmodifiableSet(docenten);
	}

	// EQUALS & HASHCODE
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Verantwoordelijkheid)) {
			return false;
		}
		return ((Verantwoordelijkheid) obj).naam.equalsIgnoreCase(this.naam);
	}

	@Override
	public int hashCode() {
		return naam.toUpperCase().hashCode();
	}

}