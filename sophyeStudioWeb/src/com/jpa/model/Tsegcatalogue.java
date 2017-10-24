package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tsegcatalogue database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegcatalogue.findAll", query="SELECT t FROM Tsegcatalogue t")
public class Tsegcatalogue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	private String value;

	//bi-directional many-to-one association to Tsegcataloguedetail
	@OneToMany(mappedBy="tsegcatalogue")
	private List<Tsegcataloguedetail> tsegcataloguedetails;

	public Tsegcatalogue() {
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

	public List<Tsegcataloguedetail> getTsegcataloguedetails() {
		return this.tsegcataloguedetails;
	}

	public void setTsegcataloguedetails(List<Tsegcataloguedetail> tsegcataloguedetails) {
		this.tsegcataloguedetails = tsegcataloguedetails;
	}

	public Tsegcataloguedetail addTsegcataloguedetail(Tsegcataloguedetail tsegcataloguedetail) {
		getTsegcataloguedetails().add(tsegcataloguedetail);
		tsegcataloguedetail.setTsegcatalogue(this);

		return tsegcataloguedetail;
	}

	public Tsegcataloguedetail removeTsegcataloguedetail(Tsegcataloguedetail tsegcataloguedetail) {
		getTsegcataloguedetails().remove(tsegcataloguedetail);
		tsegcataloguedetail.setTsegcatalogue(null);

		return tsegcataloguedetail;
	}

}