package com.example.projectvocabulary.network;

/**
 * Created by ignacy on 19.05.16.
 */
public class RegistrationWithUidDto {

	private String password;
	private String email;
	private String lastName;
	private String firstName;
	private String Uid;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}
}
