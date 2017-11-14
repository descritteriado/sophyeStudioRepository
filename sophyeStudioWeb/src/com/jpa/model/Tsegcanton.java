package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tgencanton database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegcanton.findAll", query="SELECT t FROM Tsegcanton t")
public class Tsegcanton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Tcliclient
	@OneToMany(mappedBy="canton")
	private List<Tcliclient> clients;

	//bi-directional many-to-one association to Tgenprovince
	@ManyToOne
	@JoinColumn(name="idprovince")
	private Tsegprovince province;

	//bi-directional many-to-one association to Tgenparroquia
	@OneToMany(mappedBy="canton")
	private List<Tsegparroquia> parroquias;

	public Tsegcanton() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Tcliclient addTcliclient(Tcliclient tcliclient) {
		getClients().add(tcliclient);
		tcliclient.setCanton(this);

		return tcliclient;
	}

	public Tcliclient removeTcliclient(Tcliclient tcliclient) {
		getClients().remove(tcliclient);
		tcliclient.setCanton(null);

		return tcliclient;
	}

	public Tsegparroquia addTgenparroquia(Tsegparroquia tsegparroquia) {
		getParroquias().add(tsegparroquia);
		tsegparroquia.setCanton(this);

		return tsegparroquia;
	}

	public Tsegparroquia removeTgenparroquia(Tsegparroquia tsegparroquia) {
		getParroquias().remove(tsegparroquia);
		tsegparroquia.setCanton(null);

		return tsegparroquia;
	}

	public Tsegprovince getProvince() {
		return province;
	}

	public void setProvince(Tsegprovince province) {
		this.province = province;
	}

	public List<Tsegparroquia> getParroquias() {
		return parroquias;
	}

	public void setParroquias(List<Tsegparroquia> parroquias) {
		this.parroquias = parroquias;
	}

	public List<Tcliclient> getClients() {
		return clients;
	}

	public void setClients(List<Tcliclient> clients) {
		this.clients = clients;
	}

}