package application;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** File name:	Bank.java
* Author:		Jo Suh
* Date:			April.16, 2019
 * Purpose:		This file holds the Bank class.
 */


/**
 * Contain the array of BankAccount objects
 * 
 * @author Jo Suh
 * @version 3.0
 * @see java.io
 * @see java.lang
 * @see java.nio.file.Paths
 * @since 1.0
 */
public class Bank {
	//contain the array of BankAccount objects

	/**
	 * Array of accounts
	 */
	//static BankAccount [] accounts;
	public static ArrayList<BankAccount> accounts;
	/**
	 * Actual number of accounts existing in the array
	 * <p>
	 * By default 0
	 */
	public static int numAccounts = 0;
	/**
	 * The Scanner used to read input from the input text file
	 */
	private static Scanner inputFile;
	/**
	 * The Formatter used to write output to the output text file
	 */
	private static Formatter outputFile;
	/**
	 * The maximum size of the Bank
	 */
	private int sizeBank;
	
	
	/**
	 * Default constructor for Bank
	 * <p>
	 * Instantiates an ArrayList of BankAccounts
	 */
	public Bank() {
		//constructor
		sizeBank = 1000; //allocates default size
		//accounts = new BankAccount[sizeBank];
		accounts= new ArrayList<BankAccount>(sizeBank); //set the array's initial capacity
	}
	
	/**
	 * Parameterized constructor for Bank
	 * <p>
	 * Instantiates an ArrayList of BankAccounts
	 * 
	 * @param maxSize The maximum size of the Bank
	 */
	public Bank(int maxSize) {
		//constructor
		sizeBank = maxSize; //allocates default size
		//accounts = new BankAccount[sizeBank];
		accounts= new ArrayList<BankAccount>(sizeBank); //set the array's initial capacity
	}
	/**
	 * Add an account to the array
	 * <p>
	 * prompts user to enter data for an account, which is added to array
	 * either chequing or savings account is added if there is room
	 * 
	 * @throws Exception when the account creation was unsuccessful
	 * @param accountToAdd the account to be added to the bank
	 */
	public void addAccount(BankAccount accountToAdd) throws Exception{

		if (numAccounts >= sizeBank ) {
			//the bank is full
			throw new Exception(":insufficient storage");
		}
		accounts.add(accountToAdd);//add to actual array
		numAccounts++; //add 1 to the number of accounts
	}
	/**
	 * Find and display a specific account
	 * <p>
	 * prompts user to enter an account number to display, then returns data formatted to display or an error message
	 * uses toString() from BankAccount class
	 * 
	 * @throws Exception when the account could not be displayed
	 * @param accountNumber the integer value of the account number entered by the user
	 * @return String value of the formatted account information
	 */	
	public String displayAcount(int accountNumber) throws Exception{
		// find the index
		int index = findAccount(accountNumber);
		//If there is a corresponding account
		return accounts.get(index).toString();//return its account information
	}
	/**
	 * prints details of all accounts
	 * <p>
	 * output the bank records to the output text file as well as print to the console
	 * 
	 * @throws Exception when an error with the output file occurs
	 * @return a String value of the accounts' details to be printed
	 */
	public String printAccountDetails() throws Exception{
		//output the bank records to the bankoutput.txt file and print them to the console
		
		String output = "";
		
		try {
			//Header
			output += "\nBanking System\n*********************************";
			output += "\nNumber of account holders: " + numAccounts + "\n";

			openOutputFile(); //re-open the output file file writer
			
			//for each account in the Bank
			for (BankAccount each: accounts) {
				String accInfo = "";
				//get the account type
				if(each instanceof SavingsAccount) {
					//If the account is a Savings account
					accInfo = "S ";
				}else if(each instanceof ChequingAccount){
					//If the account is a Chequing account
					accInfo = "C ";
				}
				//append the additional info
				accInfo += each.toString() + "\n"; //add an escape sequence

				outputFile.format(accInfo); // write to the output file
				output += accInfo; // write to command line
			}
			closeOutputFile(); //close the output file
			output += "\n"; //additional line at the command line for aesthetic purposes (easy reading)
		
		}catch (FormatterClosedException formatterClosedException){
			throw new Exception(":Error writing output file.");
		}catch (NoSuchElementException elementException){
			throw new Exception(":Invalid output file");
		}
		
		return output;
	}
	/**
	 * update the balance of an account
	 * <p>
	 * prompts user to enter which account number to update, and by how much
	 * and then updates the balance appropriately.
	 * returns success message or error message
	 * 
	 * @throws Exception when the transaction was unsuccessful
	 * @param accountNumber the integer value of the specified account number
	 * @param depositOrWithdrawl The String holding the method of transaction
	 * @param amount the amount of transaction to be made
	 */
	public void updateAccount(int accountNumber, String depositOrWithdrawl, double amount) throws Exception{
		// find the index
		int index = findAccount(accountNumber);
		accounts.get(index).updateBalance(amount, depositOrWithdrawl.toLowerCase().charAt(0));
	}
	/**
	 * find an account given the account number
	 * <p>
	 * prompts user to enter which account number they wish to find and returns corresponding index
	 * if none found, returns -1
	 * 
	 * @throws Exception when the specified account could not be found
	 * @param accountNumber the integer value of the specified account number
	 * @return int value of the index at which the corresponding account lies
	 */
	public int findAccount(int accountNumber) throws Exception{

		//loop for a matching account
		for (int each=0; each< numAccounts; each++) {
			BankAccount eachAcc = accounts.get(each);
			String anAccNum = eachAcc.toString().split(" ")[0];
			//acc num, first name, last name, email address, balance
			// 0			1			2			3			4
			if ( accountNumber == Integer.valueOf(anAccNum) ) {
				return each;
			}
		}
		throw new Exception("option:No such account");
	}
	/**
	 * Run a monthly update on all bank accounts
	 * 
	 * @throws Exception when there are errors for the accounts
	 */
	public void monthlyUpdate() throws Exception{
		//process through each current account in the array
		//and updates the balance appropriately
		String defaultMessage = "Error...\n";
		String errorMessage = defaultMessage;

		for (BankAccount each: accounts) {
			try {
				//check if there are errors for the accounts and combine them all into one single error output String
				each.monthlyAccountUpdate();
			}catch (Exception x) {
				errorMessage += x.getMessage().split(":")[1]; //get just the message, not where it should appear
				errorMessage += "\n"; //newline character as "\n" does not work
			}
		}
		//if there were errors, throw exception
		if (!errorMessage.equals(defaultMessage)) {
			throw new Exception(":"+errorMessage);
		}
	}

	/**
	 * open the input file
	 * 
	 * @throws Exception when there is an error opening the file
	 */
	private void openInputFile() throws Exception{
		//open the input file
		try {
			//inputFile = new Scanner(inputTextFile);
			inputFile = new Scanner(Paths.get("BankInput.txt"));
			
		} catch (IOException ioException) {
			//may occur if the file is missing or does not have access to
			throw new Exception(":Error opening input file");
		}
	}
	
	/**
	 * read the records on the input file
	 * 
	 * @throws Exception when there is an error reading the input file
	 */
	public void readRecords() throws Exception{
		////Create a new account in the array of accounts for each record in the file
		//The “C”/“S”at the beginning of each line denotes
		//whether an account is a checking account or savings account

		openInputFile();
		
		if (inputFile == null) {
			//check if the input file has been correctly opened
		      return;
		 }
		
		int inputLine = 0;
		
		try {
			while(inputFile.hasNext()) {
				//While there is more to read

				
				//take them to create corresponding banks
				// Account type / account number / first name / last name / phone number / email / balance / (interestRate) / minimum balance or fee
				inputLine+=1;
				
				String acctype = inputFile.next();
				int accNum= inputFile.nextInt();
				String firstName = inputFile.next();
				String lastName = inputFile.next();
				String phoneNumber = inputFile.next();
				String email = inputFile.next();
				double balance = inputFile.nextDouble();
				
				Client inputUser = new Client(firstName, lastName, phoneNumber, email);
				BankAccount inputAccount;
				
				switch(acctype.toUpperCase().charAt(0)) {
				case('S'):
					//saving account
					//expected to have a minimum balance at the end
					double interestRate = inputFile.nextDouble();
					double minBal = inputFile.nextDouble();
					inputAccount = new SavingsAccount(accNum, inputUser, balance, minBal, interestRate);
					accounts.add(inputAccount);
					numAccounts+=1;
					break;
				case ('C'):
					//chequing account
					double fee = inputFile.nextDouble();
					inputAccount = new ChequingAccount(accNum, inputUser, balance, fee);
					accounts.add(inputAccount);
					numAccounts+=1;
					break;
				default:
					//error
					throw new Exception(":Error in input file - invalid account type");
				}
			}

		} catch(NoSuchElementException elementexception) {
			throw new Exception(":Input file improperly formed");
			
		}catch(IllegalStateException stateException) {
			throw new Exception(":Error reading from input file");

		}catch(Exception x) {
			//the exceptions caused by invalid parameters
			String[] errorMessage = x.getMessage().split(":"); //get just the message, not where it should appear
			throw new Exception(":Error reading from input #" + inputLine + "... " + errorMessage[1]);
		}
		
		closeInputFile();//close the file
	}
	/**
	 * close the input file
	 */
	private void closeInputFile() {
		//close the input file
		if ( inputFile != null ) {
			//need to check for not null so that no exceptions are thrown
			//from a result of an attempt to perform a method on a null value
			inputFile.close();
		}
	}
	/**
	 * open the output file
	 * 
	 * @throws Exception when there is an error in opening the output file
	 */
	private void openOutputFile() throws Exception{
		//open the output file
		try {
			outputFile = new Formatter("BankOutput.txt");
		} catch (IOException ioException) {
			//may occur if the file is missing or does not have access to
			throw new Exception(":Error opening output file");
		}
	}
	/**
	 * close the output file
	 */
	private void closeOutputFile() {
		//close the output file
		if ( outputFile != null ) {
			//need to check for not null so that no exceptions are thrown
			//from a result of an attempt to perform a method on a null value
			outputFile.close();
		}
	}
}
