package com.app.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "user", catalog = "bug_hunter_db")

public class User implements java.io.Serializable {

	private Integer id;
	private String login;
	private String email;
	private Integer active;
	
	private String password;
	private String role;
	private String userName;

	private String userSurname;
	private Date birthDate;
	private String about;
	private String imageLink = "/images/default.png";
	private Set<Userhistory> userhistories = new HashSet<Userhistory>(0);

	private String DEFAULT_ROLE= "student";
	private Integer DEFAULT_ACTIVE= 1;
	
		
	public User() {
		this.role = DEFAULT_ROLE;
		this.active = DEFAULT_ACTIVE;

	}

	public User(String login, String email, String password, String role, String userName, String userSurname,
			Date birthDate) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.userSurname = userSurname;
		this.birthDate = birthDate;
	}

	public User(String login, String email, String password, String role, String userName, String userSurname,
			Date birthDate, String about, String imageLink, Set<Userhistory> userhistories) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.userSurname = userSurname;
		this.birthDate = birthDate;
		this.about = about;
		this.imageLink = imageLink;
		this.userhistories = userhistories;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "login", nullable = false, length = 50)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "email", nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Column(name = "active", nullable = false)
	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Column(name = "password", nullable = false, length = 512)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role", nullable = false, length = 50)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "user_name", nullable = false, length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_surname", nullable = false, length = 50)
	public String getUserSurname() {
		return this.userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", length = 10)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "about", length = 65535)
	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Column(name = "image_link", length = 100)
	public String getImageLink() {
		return this.imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	public Set<Userhistory> getUserhistories() {
		return this.userhistories;
	}

	public void setUserhistories(Set<Userhistory> userhistories) {
		this.userhistories = userhistories;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", userName=" + userName + ", userSurname=" + userSurname + ", birthDate=" + birthDate + ", about="
				+ about + ", imageLink=" + imageLink + ", userhistories=" + userhistories + "]";
	}

}
