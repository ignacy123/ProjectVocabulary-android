package com.example.projectvocabulary.domain.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ignacy on 14.06.17.
 */
@Entity
public class User {

	@PrimaryKey
	Long id;
	String email;
	String firstName;
	String lastName;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		if (!id.equals(user.id)) {
			return false;
		}
		if (email != null ? !email.equals(user.email) : user.email != null) {
			return false;
		}
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
			return false;
		}
		return lastName != null ? lastName.equals(user.lastName) : user.lastName == null;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}
}
