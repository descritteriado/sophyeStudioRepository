package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tsegprofilemodule database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegprofilemodule.findAll", query="SELECT t FROM Tsegprofilemodule t")
public class Tsegprofilemodule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Tsegmodule
	@ManyToOne
	@JoinColumn(name="idmodule")
	private Tsegmodule tsegmodule;

	//bi-directional many-to-one association to Tsegprofile
	@ManyToOne
	@JoinColumn(name="idprofile")
	private Tsegprofile tsegprofile;

	public Tsegprofilemodule() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Tsegmodule getTsegmodule() {
		return this.tsegmodule;
	}

	public void setTsegmodule(Tsegmodule tsegmodule) {
		this.tsegmodule = tsegmodule;
	}

	public Tsegprofile getTsegprofile() {
		return this.tsegprofile;
	}

	public void setTsegprofile(Tsegprofile tsegprofile) {
		this.tsegprofile = tsegprofile;
	}

}