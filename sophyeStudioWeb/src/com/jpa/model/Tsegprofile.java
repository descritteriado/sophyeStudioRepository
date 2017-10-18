package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tsegprofile database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegprofile.findAll", query="SELECT t FROM Tsegprofile t")
public class Tsegprofile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coment;

	private String description;

	//bi-directional many-to-one association to Tsegprofilemodule
	@OneToMany(mappedBy="tsegprofile")
	private List<Tsegprofilemodule> tsegprofilemodules;

	//bi-directional many-to-one association to Tseguserprofile
	@OneToMany(mappedBy="tsegprofile")
	private List<Tseguserprofile> tseguserprofiles;

	public Tsegprofile() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComent() {
		return this.coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tsegprofilemodule> getTsegprofilemodules() {
		return this.tsegprofilemodules;
	}

	public void setTsegprofilemodules(List<Tsegprofilemodule> tsegprofilemodules) {
		this.tsegprofilemodules = tsegprofilemodules;
	}

	public Tsegprofilemodule addTsegprofilemodule(Tsegprofilemodule tsegprofilemodule) {
		getTsegprofilemodules().add(tsegprofilemodule);
		tsegprofilemodule.setTsegprofile(this);

		return tsegprofilemodule;
	}

	public Tsegprofilemodule removeTsegprofilemodule(Tsegprofilemodule tsegprofilemodule) {
		getTsegprofilemodules().remove(tsegprofilemodule);
		tsegprofilemodule.setTsegprofile(null);

		return tsegprofilemodule;
	}

	public List<Tseguserprofile> getTseguserprofiles() {
		return this.tseguserprofiles;
	}

	public void setTseguserprofiles(List<Tseguserprofile> tseguserprofiles) {
		this.tseguserprofiles = tseguserprofiles;
	}

	public Tseguserprofile addTseguserprofile(Tseguserprofile tseguserprofile) {
		getTseguserprofiles().add(tseguserprofile);
		tseguserprofile.setTsegprofile(this);

		return tseguserprofile;
	}

	public Tseguserprofile removeTseguserprofile(Tseguserprofile tseguserprofile) {
		getTseguserprofiles().remove(tseguserprofile);
		tseguserprofile.setTsegprofile(null);

		return tseguserprofile;
	}

}