package com.example.projectvocabulary.domain.user;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.projectvocabulary.sql.UserColumns;

/**
 * Created by ignacy on 14.06.17.
 */

public class User implements UserColumns {

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

	public ContentValues getContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIRST_NAME, firstName);
		contentValues.put(LAST_NAME, lastName);
		contentValues.put(EMAIL, email);
		contentValues.put(ID, id);
		return contentValues;
	}

	public User(Cursor cursor) {
		id = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
		email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL));
		firstName = cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME));
		lastName = cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME));
	}

	public User() {
	}
}
