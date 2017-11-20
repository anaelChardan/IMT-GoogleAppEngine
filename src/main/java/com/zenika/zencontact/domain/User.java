package com.zenika.zencontact.domain;

import com.google.appengine.api.blobstore.BlobKey;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	public Long id;
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public Date lastConnectionDate;
	public Date birthdate;
	public String notes;

	public BlobKey photoKey; // points to the blobstore entry if any
	public String uploadURL;
	public String downloadURL;

	public static User create() {
		return new User();
	}

	public User id(Long id) {
		this.id = id;
		return this;
	}

	public User firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public User lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public User email(String email) {
		this.email = email;
		return this;
	}

	public User password(String password) {
		this.password = password;
		return this;
	}

	public User lastConnectionDate(Date lastConnectionDate) {
		this.lastConnectionDate = lastConnectionDate;
		return this;
	}

	public User birthdate(Date birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	public User notes(String notes) {
		this.notes = notes;
		return this;
	}

	public User photoKey(BlobKey photoKey) {
		this.photoKey = photoKey;
		return this;
	}

	public User uploadURL(String uploadURL) {
		this.uploadURL = uploadURL;
		return this;
	}

	public User downloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
		return this;
	}

}
