package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tsegmodule database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegmodule.findAll", query="SELECT t FROM Tsegmodule t")
public class Tsegmodule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coment;

	private String description;

	//bi-directional many-to-one association to Tsegmoduletransaction
	@OneToMany(mappedBy="tsegmodule")
	private List<Tsegmoduletransaction> tsegmoduletransactions;

	//bi-directional many-to-one association to Tsegprofilemodule
	@OneToMany(mappedBy="tsegmodule")
	private List<Tsegprofilemodule> tsegprofilemodules;

	public Tsegmodule() {
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

	public List<Tsegmoduletransaction> getTsegmoduletransactions() {
		return this.tsegmoduletransactions;
	}

	public void setTsegmoduletransactions(List<Tsegmoduletransaction> tsegmoduletransactions) {
		this.tsegmoduletransactions = tsegmoduletransactions;
	}

	public Tsegmoduletransaction addTsegmoduletransaction(Tsegmoduletransaction tsegmoduletransaction) {
		getTsegmoduletransactions().add(tsegmoduletransaction);
		tsegmoduletransaction.setTsegmodule(this);

		return tsegmoduletransaction;
	}

	public Tsegmoduletransaction removeTsegmoduletransaction(Tsegmoduletransaction tsegmoduletransaction) {
		getTsegmoduletransactions().remove(tsegmoduletransaction);
		tsegmoduletransaction.setTsegmodule(null);

		return tsegmoduletransaction;
	}

	public List<Tsegprofilemodule> getTsegprofilemodules() {
		return this.tsegprofilemodules;
	}

	public void setTsegprofilemodules(List<Tsegprofilemodule> tsegprofilemodules) {
		this.tsegprofilemodules = tsegprofilemodules;
	}

	public Tsegprofilemodule addTsegprofilemodule(Tsegprofilemodule tsegprofilemodule) {
		getTsegprofilemodules().add(tsegprofilemodule);
		tsegprofilemodule.setTsegmodule(this);

		return tsegprofilemodule;
	}

	public Tsegprofilemodule removeTsegprofilemodule(Tsegprofilemodule tsegprofilemodule) {
		getTsegprofilemodules().remove(tsegprofilemodule);
		tsegprofilemodule.setTsegmodule(null);

		return tsegprofilemodule;
	}

}