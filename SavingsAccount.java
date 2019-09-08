package application;



import application.BankAccount;

/** File name:	SavingsAccount.java
* Author:		Jo Suh
* Date:			April.16, 2019
 * Purpose:		This file holds the SavingsAccount class.
 */


/**
 * Contain the data members for a savings account
 * <p>
 * inherited from BankAccount
 * 
 * @author Jo Suh
 * @version 2.0
 * @see java.io
 * @see java.lang
 * @see BankAccount
 * @since 1.0
 */
public class SavingsAccount extends BankAccount{
	//inherited from BankAccount
	//contains the data members for a savings account
	/**
	 * minimum balance for the account
	 */
	private double minBalance;
	/**
	 * monthly interest rate
	 * <p>
	 *  customers are given interests at the end of the month as long as the balance is above the minimum balance
	 */
	private double interestRate;

	/**
	 * Parameterized constructor for the Savings account
	 * @throws Exception when the user inputs a different value than the required
	 * 
	 * @param accNumber int value of the account number
	 * @param accHolder Person value of the account holder
	 * @param balance double value of the account balance
	 * @param minBalance double value of the minimum balance of the account
	 * @param interestRate double value of the interest rate of the account
	 */
	public SavingsAccount(int accNumber, Client accHolder, double balance, double minBalance, double interestRate) throws Exception{
		super(accNumber, accHolder, balance);
		if (minBalance<=0 || minBalance>balance) {
			throw new Exception("additional:Invalid balance");
		}
		this.minBalance = minBalance;
		
		if ( interestRate<0 || interestRate>1 ) {
			throw new Exception("interestRate:Invalid interest rate");
		}
		this.interestRate = interestRate;
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
		
		String returnString = super.toString() + " " + minBalance + " " + interestRate;
		return returnString;
	}

	/**
	 * proceeds the monthly update
	 * <p>
	 * adds the monthly interest rate to the account balance
	 * Overrides toString() of class BankAccount
	 * 
	 * 
	 * @throws Exception when the account has an insufficient minimum balance
	 */
	@Override
	public void monthlyAccountUpdate() throws Exception {
		//processes the object with monthly update of adding interest
		//(as long as bank balance is more than minBalance, else displays error message)
		if (super.balance > minBalance) {
			//'deposit' the interest
			super.updateBalance(super.balance*interestRate, 'd');
		}else {
			//displays error message
			throw new Exception("output:Account #" + accNumber + " has insufficient minimum balance");
		}
	}
}
