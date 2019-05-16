
package com.cwiztech.login.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.cwiztech.systemsetting.model.Lookup;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLLOGINUSER")
public class LoginUser implements UserDetails {

	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long USER_ID;

	@Column(name = "USER_NAME")
	private String USER_NAME;

	@JsonIgnore
	@Column(name = "PASSWORD")
	private String PASSWORD;

	@Column(name = "LAST_LOGIN")
	private String LAST_LOGIN;

	@OneToOne
	@JoinColumn(name = "USER_STATUS")
	private Lookup USER_STATUS;

	@Column(name = "ISCHANGEPASSWORD_REQUESTED")
	private String ISCHANGEPASSWORD_REQUESTED;

	@Column(name = "ISCHANGEPASSWORD_REQUESTEDWHEN")
	private String ISCHANGEPASSWORD_REQUESTEDWHEN;

	@Column(name = "ISACTIVE")
	private String ISACTIVE;

	@JsonIgnore
	@OneToOne (cascade = { CascadeType.ALL })
	@JoinColumn(name = "MODIFIED_BY")
	private LoginUser MODIFIED_BY;

	@JsonIgnore
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(long uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	@JsonIgnore
	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}

	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}

	public Lookup getUSER_STATUS() {
		return USER_STATUS;
	}

	public void setUSER_STATUS(Lookup uSER_STATUS) {
		USER_STATUS = uSER_STATUS;
	}

	public String getISCHANGEPASSWORD_REQUESTED() {
		return ISCHANGEPASSWORD_REQUESTED;
	}

	public void setISCHANGEPASSWORD_REQUESTED(String iSCHANGEPASSWORD_REQUESTED) {
		ISCHANGEPASSWORD_REQUESTED = iSCHANGEPASSWORD_REQUESTED;
	}

	public String getISCHANGEPASSWORD_REQUESTEDWHEN() {
		return ISCHANGEPASSWORD_REQUESTEDWHEN;
	}

	public void setISCHANGEPASSWORD_REQUESTEDWHEN(String iSCHANGEPASSWORD_REQUESTEDWHEN) {
		ISCHANGEPASSWORD_REQUESTEDWHEN = iSCHANGEPASSWORD_REQUESTEDWHEN;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	@JsonIgnore
	public LoginUser getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(LoginUser mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	@JsonIgnore
	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	@JsonIgnore
	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

	public static long getDatabaseTableID() {
		return (long) 2;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		return authority;
	}

	@Override
	public String getPassword() {
		String salt = BCrypt.gensalt(12);
		String hasehed_password = BCrypt.hashpw(PASSWORD, salt);

		return hasehed_password;
	}

	@Override
	public String getUsername() {
		return USER_NAME;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// we never lock accounts
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Credentials Never Expired
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (ISACTIVE.compareTo("Y") == 0)
			return true;
		else
			return false;
	}

}