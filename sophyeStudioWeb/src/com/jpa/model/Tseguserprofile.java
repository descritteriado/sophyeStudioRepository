package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tseguserprofile database table.
 * 
 */
@Entity
@NamedQuery(name="Tseguserprofile.findAll", query="SELECT t FROM Tseguserprofile t")
public class Tseguserprofile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coment;

	//bi-directional many-to-one association to Tsegprofile
	@ManyToOne
	@JoinColumn(name="idprofile")
	private Tsegprofile tsegprofile;

	//bi-directional many-to-one association to Tseguser
	@ManyToOne
	@JoinColumn(name="iduser")
	private Tseguser tseguser;

	public Tseguserprofile() {
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

	public Tsegprofile getTsegprofile() {
		return this.tsegprofile;
	}

	public void setTsegprofile(Tsegprofile tsegprofile) {
		this.tsegprofile = tsegprofile;
	}

	public Tseguser getTseguser() {
		return this.tseguser;
	}

	public void setTseguser(Tseguser tseguser) {
		this.tseguser = tseguser;
	}

}