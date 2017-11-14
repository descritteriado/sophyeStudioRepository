package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tgenparroquia database table.
 * 
 */
@Entity
@NamedQuery(name="Tgenparroquia.findAll", query="SELECT t FROM Tsegparroquia t")
public class Tsegparroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Tcliclient
	@OneToMany(mappedBy="parroquia")
	private List<Tcliclient> clients;

	//bi-directional many-to-one association to Tgencanton
	@ManyToOne
	@JoinColumn(name="idcanton")
	private Tsegcanton canton;

	public Tsegparroquia() {
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
		tcliclient.setParroquia(this);

		return tcliclient;
	}

	public Tcliclient removeTcliclient(Tcliclient tcliclient) {
		getClients().remove(tcliclient);
		tcliclient.setParroquia(null);

		return tcliclient;
	}

	public List<Tcliclient> getClients() {
		return clients;
	}

	public void setClients(List<Tcliclient> clients) {
		this.clients = clients;
	}

	public Tsegcanton getCanton() {
		return canton;
	}

	public void setCanton(Tsegcanton canton) {
		this.canton = canton;
	}

	

}