package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tsegcataloguedetail database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegcataloguedetail.findAll", query="SELECT t FROM Tsegcataloguedetail t")
public class Tsegcataloguedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	private String value;

	//bi-directional many-to-one association to Tcliclient
	@OneToMany(mappedBy="documentType")
	private List<Tcliclient> tcliclients;

	//bi-directional many-to-one association to Tsegcatalogue
	@ManyToOne
	@JoinColumn(name="idcatalogue")
	private Tsegcatalogue tsegcatalogue;

	public Tsegcataloguedetail() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Tcliclient> getTcliclients() {
		return this.tcliclients;
	}

	public void setTcliclients(List<Tcliclient> tcliclients) {
		this.tcliclients = tcliclients;
	}

	public Tcliclient addTcliclient(Tcliclient tcliclient) {
		getTcliclients().add(tcliclient);
		tcliclient.setDocumentType(this);

		return tcliclient;
	}

	public Tcliclient removeTcliclient(Tcliclient tcliclient) {
		getTcliclients().remove(tcliclient);
		tcliclient.setDocumentType(null);

		return tcliclient;
	}

	public Tsegcatalogue getTsegcatalogue() {
		return this.tsegcatalogue;
	}

	public void setTsegcatalogue(Tsegcatalogue tsegcatalogue) {
		this.tsegcatalogue = tsegcatalogue;
	}

}