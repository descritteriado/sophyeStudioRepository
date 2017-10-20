package com.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tseguser database table.
 * 
 */
@Entity
@NamedQuery(name="Tseguser.findAll", query="SELECT t FROM Tseguser t")
public class Tseguser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private Boolean deleted;

	private String email;

	@Temporal(TemporalType.DATE)
	private Date expirationdate;

	private Boolean istemporal;

	private String lastnames;

	@Temporal(TemporalType.DATE)
	private Date lockdate;

	private String names;

	private byte[] password;

	private Integer phone1;

	private Integer phone2;

	private Boolean status;

	private String username;

	//bi-directional many-to-one association to Tseguserprofile
	@OneToMany(mappedBy="tseguser")
	private List<Tseguserprofile> tseguserprofiles;

	public Tseguser() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpirationdate() {
		return this.expirationdate;
	}

	public void setExpirationdate(Date expirationdate) {
		this.expirationdate = expirationdate;
	}

	public Boolean getIstemporal() {
		return this.istemporal;
	}

	public void setIstemporal(Boolean istemporal) {
		this.istemporal = istemporal;
	}

	public String getLastnames() {
		return this.lastnames;
	}

	public void setLastnames(String lastnames) {
		this.lastnames = lastnames;
	}

	public Date getLockdate() {
		return this.lockdate;
	}

	public void setLockdate(Date lockdate) {
		this.lockdate = lockdate;
	}

	public String getNames() {
		return this.names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public byte[] getPassword() {
		return this.password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Integer getPhone1() {
		return this.phone1;
	}

	public void setPhone1(Integer phone1) {
		this.phone1 = phone1;
	}

	public Integer getPhone2() {
		return this.phone2;
	}

	public void setPhone2(Integer phone2) {
		this.phone2 = phone2;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Tseguserprofile> getTseguserprofiles() {
		return this.tseguserprofiles;
	}

	public void setTseguserprofiles(List<Tseguserprofile> tseguserprofiles) {
		this.tseguserprofiles = tseguserprofiles;
	}

	public Tseguserprofile addTseguserprofile(Tseguserprofile tseguserprofile) {
		getTseguserprofiles().add(tseguserprofile);
		tseguserprofile.setTseguser(this);

		return tseguserprofile;
	}

	public Tseguserprofile removeTseguserprofile(Tseguserprofile tseguserprofile) {
		getTseguserprofiles().remove(tseguserprofile);
		tseguserprofile.setTseguser(null);

		return tseguserprofile;
	}

}