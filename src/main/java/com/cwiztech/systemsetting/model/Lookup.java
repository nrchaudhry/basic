package com.cwiztech.systemsetting.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cwiztech.login.model.LoginUser;

@Entity
@Table(name = "TBLSYSTEMSETTINGLOOKUP")
public class Lookup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;

	@Column(name = "ENTITYNAME")
	private String ENTITYNAME;

	@Column(name = "CODE")
	private String CODE;

	@Column(name = "DESCRIPTION")
	private String DESCRIPTION;

	@Column(name = "ENTITY_STATUS")
	private String ENTITY_STATUS;

	@Column(name = "ISACTIVE")
	private String ISACTIVE;

	@OneToOne (cascade = { CascadeType.ALL })
	@JoinColumn(name = "MODIFIED_BY")
	private LoginUser MODIFIED_BY;

	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getENTITYNAME() {
		return ENTITYNAME;
	}

	public void setENTITYNAME(String eNTITYNAME) {
		ENTITYNAME = eNTITYNAME;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getENTITY_STATUS() {
		return ENTITY_STATUS;
	}

	public void setENTITY_STATUS(String eNTITY_STATUS) {
		if (eNTITY_STATUS.compareTo("o") == 0)
			ENTITY_STATUS = eNTITY_STATUS.toUpperCase();
		else
			ENTITY_STATUS = eNTITY_STATUS;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public LoginUser getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(LoginUser mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

	public static long getDatabaseTableID() {
		return (long) 12;
	}

}
