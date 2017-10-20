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

	//bi-directional many-to-one association to Tsegtransaction
	@OneToMany(mappedBy="tsegmodule")
	private List<Tsegtransaction> tsegtransactions;

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

	public List<Tsegtransaction> getTsegtransactions() {
		return this.tsegtransactions;
	}

	public void setTsegtransactions(List<Tsegtransaction> tsegtransactions) {
		this.tsegtransactions = tsegtransactions;
	}

	public Tsegtransaction addTsegtransaction(Tsegtransaction tsegtransaction) {
		getTsegtransactions().add(tsegtransaction);
		tsegtransaction.setTsegmodule(this);

		return tsegtransaction;
	}

	public Tsegtransaction removeTsegtransaction(Tsegtransaction tsegtransaction) {
		getTsegtransactions().remove(tsegtransaction);
		tsegtransaction.setTsegmodule(null);

		return tsegtransaction;
	}

}