package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tgenprovince database table.
 * 
 */
@Entity
@NamedQuery(name="Tgenprovince.findAll", query="SELECT t FROM Tsegprovince t")
public class Tsegprovince implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Tcliclient
	@OneToMany(mappedBy="province")
	private List<Tcliclient> clients;

	//bi-directional many-to-one association to Tgencanton
	@OneToMany(mappedBy="province")
	private List<Tsegcanton> cantons;

	public Tsegprovince() {
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
		tcliclient.setProvince(this);

		return tcliclient;
	}

	public Tcliclient removeTcliclient(Tcliclient tcliclient) {
		getClients().remove(tcliclient);
		tcliclient.setProvince(null);

		return tcliclient;
	}

	
	public Tsegcanton addTgencanton(Tsegcanton tgencanton) {
		getCantons().add(tgencanton);
		tgencanton.setProvince(this);

		return tgencanton;
	}

	public Tsegcanton removeTgencanton(Tsegcanton tgencanton) {
		getCantons().remove(tgencanton);
		tgencanton.setProvince(null);

		return tgencanton;
	}

	public List<Tcliclient> getClients() {
		return clients;
	}

	public void setClients(List<Tcliclient> clients) {
		this.clients = clients;
	}

	public List<Tsegcanton> getCantons() {
		return cantons;
	}

	public void setCantons(List<Tsegcanton> cantons) {
		this.cantons = cantons;
	}

}