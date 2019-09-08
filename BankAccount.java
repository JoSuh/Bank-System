package application;


import java.util.Scanner;


/** File name:	BankAccount.java
* Author:		Jo Suh, s040943462
* Course:		CST8132 – OOP
* Assignment: 	Lab 9
* Date:			April.16, 2019
* Professor:	Dr. Mohammad Kadhum
 * Purpose:		This file holds the BankAccount class.
 */


/**
 * base class containing the common data members for all Bank Accounts
 * 
 * @author Jo Suh
 * @version 3.0
 * @see java.io
 * @see java.lang
 * @since 1.0
 */
public abstract class BankAccount {
	Scanner input = new Scanner(System.in);
	//the base class
	//contain the common data members for all Bank Accounts
	/**
	 * account number, up to 8 digits long
	 */
	protected int accNumber;
	/**
	 * the account holder
	 */
	protected Client accHolder;
	/**
	 * The balance for the account
	 */
	protected double balance;

	/**
	 * Default constructor for a Banking account
	 */
	public BankAccount() {}
	/**
	 * Parameterized constructor for  a Banking account
	 * 
	 * @throws Exception when the Bank account creation is unsuccessful
	 * @param accNumber int value of the account number
	 * @param accHolder Person value of the account holder
	 * @param balance double value of the account balance
	 */
	public BankAccount(int accNumber, Client accHolder, double balance) throws Exception{
		if (accNumber<100000000 && accNumber>=0) { 
			for (int each=0; each < Bank.numAccounts; each++) {
				//check uniqueness of of new account numbers
				BankAccount eachAcc = Bank.accounts.get(each);
				String[] eachPart = eachAcc.toString().split(" ");
				//acc num, first name, last name, phone number, email address, balance
				// 0			1			2			3			4			5
				
				if (accNumber == Integer.valueOf(eachPart[0])) {
					//if there is already a matching account number
					throw new Exception("accountNumber:Repeating account number");
				}
			}
		}else {
			throw new Exception("accountNumber:Invalid account number");
		}
		this.accNumber = accNumber;
		
		this.accHolder = accHolder;
		
		if (balance<0) { 
			throw new Exception("balance:Invalid opening balance");
		}
		this.balance = balance;
	}
	/**
	 * returns the data of the account formatted to display
	 * <p>
	 * Overrides toString() of class Object
	 * @return String value of formatted information
	 */
	@Override
	public String toString() {

		String print = accNumber + " "
				+ accHolder.getFirstName() + " " + accHolder.getLastName() + " "
				+ accHolder.getPhoneNumber() + " "
				+ accHolder.getEmailAddress() + " "
				+ balance;
		
		return print;
	}
	/**
	 * Updates the balance by the parameter amount
	 * <p>
	 * Does not allow user to insert a negative value,
	 * or for a withdrawal, a value greater than the balance.
	 * 
	 * @throws Exception when the transaction is unsuccessful
	 * @param amount a double value of amount to add to the bank accuont's balance
	 * @param depositOrWithdrawal a character value indicating the transaction method
	 */
	public void updateBalance(double amount, char depositOrWithdrawal) throws Exception{
		
		//negative amount
		if (amount<0) {
			//not allowed for either method
			// throw an IllegalArgumentException
			throw new Exception("transactionAmount:Invalid amount");
			//throw new TransactionIllegalArgumentException(accNumber, amount, "Invalid transaction: cannot have negative amount");
		}
		
		//if the amount is not negative
		switch(depositOrWithdrawal) {
			case ('d'):
				//deposit
				//already checked every condition so just add amount to balance
				this.balance += amount;
				break;
			case ('w'):
				//withdrawal
				//check if the amount is greater than the balance or not
				if (amount>balance) {
					//throw an IllegalArgumentException
					throw new Exception("transactionAmount:Insufficient balance");
					//throw new TransactionIllegalArgumentException(accNumber, amount, "Invalid withrawl: the amount is greater than the available balance");
				} else {
					//subtract the amount from the balance
					this.balance -= amount;
				}
				break;
		}
	}
	/**
	 * proceeds the monthly update
	 * 
	 * @throws Exception when there is an error in the monthly update
	 */
	public abstract void monthlyAccountUpdate() throws Exception;
	
	
}
