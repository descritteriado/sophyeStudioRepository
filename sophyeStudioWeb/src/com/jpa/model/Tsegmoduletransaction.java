package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tsegmoduletransaction database table.
 * 
 */
@Entity
@NamedQuery(name="Tsegmoduletransaction.findAll", query="SELECT t FROM Tsegmoduletransaction t")
public class Tsegmoduletransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Tsegmodule
	@ManyToOne
	@JoinColumn(name="idmodule")
	private Tsegmodule tsegmodule;

	//bi-directional many-to-one association to Tsegtransaction
	@ManyToOne
	@JoinColumn(name="idtransaction")
	private Tsegtransaction tsegtransaction;

	public Tsegmoduletransaction() {
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

	public Tsegtransaction getTsegtransaction() {
		return this.tsegtransaction;
	}

	public void setTsegtransaction(Tsegtransaction tsegtransaction) {
		this.tsegtransaction = tsegtransaction;
	}

}