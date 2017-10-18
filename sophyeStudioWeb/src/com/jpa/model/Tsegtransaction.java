package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tsegtransaction database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegtransaction.findAll", query="SELECT t FROM Tsegtransaction t")
public class Tsegtransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coment;

	private String description;

	private String url;

	//bi-directional many-to-one association to Tsegmoduletransaction
	@OneToMany(mappedBy="tsegtransaction")
	private List<Tsegmoduletransaction> tsegmoduletransactions;

	public Tsegtransaction() {
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Tsegmoduletransaction> getTsegmoduletransactions() {
		return this.tsegmoduletransactions;
	}

	public void setTsegmoduletransactions(List<Tsegmoduletransaction> tsegmoduletransactions) {
		this.tsegmoduletransactions = tsegmoduletransactions;
	}

	public Tsegmoduletransaction addTsegmoduletransaction(Tsegmoduletransaction tsegmoduletransaction) {
		getTsegmoduletransactions().add(tsegmoduletransaction);
		tsegmoduletransaction.setTsegtransaction(this);

		return tsegmoduletransaction;
	}

	public Tsegmoduletransaction removeTsegmoduletransaction(Tsegmoduletransaction tsegmoduletransaction) {
		getTsegmoduletransactions().remove(tsegmoduletransaction);
		tsegmoduletransaction.setTsegtransaction(null);

		return tsegmoduletransaction;
	}

}