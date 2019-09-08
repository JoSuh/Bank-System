package application;



import application.BankAccount;

/** File name:	ChequingAccount.java
* Author:		Jo Suh
* Date:			April.16, 2019
 * Purpose:		This file holds the ChequingAccount class.
 */


/**
 * Contain the data members for a chequing account
 * <p>
 * inherited from BankAccount
 * 
 * @author Jo Suh
 * @version 3.0
 * @see java.io
 * @see java.lang
 * @see BankAccount
 * @since 1.0
 */
public class ChequingAccount extends BankAccount {
	//inherited from BankAccount
	//contains the data members for a chequing account

	/**
	 * monthly deducted fee
	 */
	private double fee;
	

	/**
	 * Parameterized constructor for the Chequing account
	 * 
	 * 
	 * @throws Exception when the user inputs a different value than the required
	 * 
	 * @param accNumber int value of the account number
	 * @param accHolder Person value of the account holder
	 * @param balance double value of the account balance
	 * @param fee double value of the monthly fee
	 */
	public ChequingAccount(int accNumber, Client accHolder, double balance, double fee) throws Exception{
		super(accNumber, accHolder, balance);
		
		if ( fee<=0 ) {
			//fee should be greater than zero
			throw new Exception("additional:Invalid fee amount");
		}
		this.fee = fee;
	}
	
	/**
	 * returns the data of the account formatted to display
	 * <p>
	 * Overrides toString() of class BankAccount
	 * @return String value of formatted information
	 */
	@Override
	public String toString() {
		//returns the data of the account formatted to display
		
		String returnString = super.toString() + " " + fee;
		return returnString;
	}

	/**
	 * proceeds the monthly update
	 * <p>
	 * subtracts the monthly fee from the balance
	 * Overrides toString() of class BankAccount
	 * 
	 * @throws Exception when the account has an insufficient balance
	 */
	@Override
	public void monthlyAccountUpdate() throws Exception {
		//processes the object with monthly update of withdrawing the fee 
		//(as long as bank balance is more than fee, else displays error message)
		if (super.balance > fee) {
			//withdrawing the fee
			super.updateBalance(fee, 'w');
		}else {
			//displays error message
			throw new Exception("output:Account #" + accNumber + " has insufficient balance");
		}
	}
	
}
