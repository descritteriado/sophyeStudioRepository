package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tsegprofiletransaction database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegprofiletransaction.findAll", query="SELECT t FROM Tsegprofiletransaction t")
public class Tsegprofiletransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String coment;

	//bi-directional many-to-one association to Tsegprofile
	@ManyToOne
	@JoinColumn(name="idprofile")
	private Tsegprofile tsegprofile;

	//bi-directional many-to-one association to Tsegtransaction
	@ManyToOne
	@JoinColumn(name="idtransaction")
	private Tsegtransaction tsegtransaction;

	public Tsegprofiletransaction() {
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

	public Tsegtransaction getTsegtransaction() {
		return this.tsegtransaction;
	}

	public void setTsegtransaction(Tsegtransaction tsegtransaction) {
		this.tsegtransaction = tsegtransaction;
	}

}