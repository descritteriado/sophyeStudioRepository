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

	//bi-directional many-to-one association to Tsegprofiletransaction
	@OneToMany(mappedBy="tsegprofile")
	private List<Tsegprofiletransaction> tsegprofiletransactions;

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

	public List<Tsegprofiletransaction> getTsegprofiletransactions() {
		return this.tsegprofiletransactions;
	}

	public void setTsegprofiletransactions(List<Tsegprofiletransaction> tsegprofiletransactions) {
		this.tsegprofiletransactions = tsegprofiletransactions;
	}

	public Tsegprofiletransaction addTsegprofiletransaction(Tsegprofiletransaction tsegprofiletransaction) {
		getTsegprofiletransactions().add(tsegprofiletransaction);
		tsegprofiletransaction.setTsegprofile(this);

		return tsegprofiletransaction;
	}

	public Tsegprofiletransaction removeTsegprofiletransaction(Tsegprofiletransaction tsegprofiletransaction) {
		getTsegprofiletransactions().remove(tsegprofiletransaction);
		tsegprofiletransaction.setTsegprofile(null);

		return tsegprofiletransaction;
	}

}