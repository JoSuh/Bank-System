package application;

import java.util.Scanner;


/** File name:	Client.java
* Author:		Jo Suh
* Date:			April.16, 2019
 * Purpose:		This file holds the Person class.
 */


/**
 * Contain common data members for a person
 * 
 * @author Jo Suh
 * @version 3.0
 * @see java.io
 * @see java.lang
 * @since 1.0
 */
public class Client {
	Scanner input = new Scanner(System.in);
	
	/**
	 * first name of the account holder
	 */
	private String firstName;
	/**
	 * last name of the account holder
	 */
	private String lastName;
	/**
	 * phone number of the account holder
	 */
	private String phoneNumber;
	/**
	 * email address of the account holder
	 */
	private String emailAddress;
	

	/**
	 * Parameterized constructor to create a Person Object
	 * @throws Exception when the user inputs a string in different format than the required
	 * 
	 * @param firstName String value of the person's first name
	 * @param lastName String value of the person's last name
	 * @param phoneNumber String value of the person's phone number
	 * @param emailAddress String value of the person's email address
	 * 
	 */
	public Client(String firstName, String lastName, String phoneNumber, String emailAddress) throws Exception{
		if ( firstName.equals("") || !firstName.matches("^*[a-zA-Z\\s]*$") ) {
			throw new Exception("firstName:Invalid characters");
		}
		this.firstName = firstName;
		
		if ( lastName.equals("") || !lastName.matches("^*[a-zA-Z\\s]*$") ) {
			throw new Exception("lastName:Invalid characters");
		}
		this.lastName = lastName;
		
		if ( phoneNumber.equals("") || !phoneNumber.matches("^[0-9]*$") ) {
			throw new Exception("phone:Invalid phone format");
		}
		this.phoneNumber = phoneNumber;
		
		if ( emailAddress.equals("") || !emailAddress.matches("^[a-zA-Z0-9_.]+@[a-zA-Z.]+?\\.[a-zA-Z]{2,3}$") ) {
			throw new Exception("email:Invalid email format");
		}
		this.emailAddress= emailAddress;
		
	}
	/**
	 * returns the value of the first name
	 * @return String value of first name
	 */
	public String getFirstName() {
		//getter for firstName
		return firstName;
	}
	/**
	 * returns the value of the last name
	 * @return String value of last name
	 */
	public String getLastName() {
		//getter for lastName
		return lastName;
	}
	/**
	 * returns the value of the phone number
	 * @return String value of phone number
	 */
	public String getPhoneNumber() {
		//getter for phoneNumber
		return phoneNumber;
	}
	/**
	 * returns the value of the email address
	 * @return String value of email address
	 */
	public String getEmailAddress() {
		//getter for emailAddress
		return emailAddress;
	}
	
}
