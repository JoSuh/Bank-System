package application;


import java.math.RoundingMode;
import java.text.NumberFormat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;


/** File name:	BankSimulatorController.java
* Author:		Jo Suh
* Date:			April.16, 2019
* Purpose:		This file holds the BankSimulatorController class.
*/



/**
* This is the controller that handles actions for the BankSimulator's events
* 
* @author Jo Suh
* @version 1.0
* @since 1.0
*/
public class BankSimulatorController {
	/**
	 * an enum for the program's process types
	 */
	private enum states{
		BASIC,
		UPDATE_ACCOUNT,
		PRINTING_ACCOUNT,
		END
	}
	/**
	 * a states value for the current process type
	 */
	private static states currentState = states.BASIC;

	/**
	 * An object of the Bank class
	 */
	public Bank bank = new Bank(1000);//create a bank of size = 1000
	
	//formatters for currency and percentage
	/**
	 * Formatter to format numbers into currency format:
	 * $ and a number to 2 decimal places
	 */
	private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
	/**
	 * Formatter to format numbers into percentage format:
	 * % and a number
	 */
	private static final NumberFormat percent = NumberFormat.getPercentInstance();
	{
		// initialize the formats
		// 0-4 rounds down, 5-9 rounds up
		percent.setRoundingMode(RoundingMode.HALF_UP);
		currency.setRoundingMode(RoundingMode.HALF_UP);
	}
	
	
    @FXML
    private Button addAccountButton;

    @FXML
    private Button cancelAccountCreationButton;

    @FXML
    private Button cancelTransactionButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button displayAccountButton;

    @FXML
    private Button monthlyUpdateButton;

    @FXML
    private Button printAllAccountsButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button readrecordsButton;

    @FXML
    private Button runButton;

    @FXML
    private Button updateAccountButton;

    @FXML
    private GridPane accountNumberGridpane;

    @FXML
    private GridPane accountTypeGridPane;

    @FXML
    private GridPane addAccountGridPane;

    @FXML
    private GridPane runGridpane;

    @FXML
    private GridPane transactionGridpane;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label additionalLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label bankSimulatorLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label interestRateLabel;

    @FXML
    private Label interestRateValueLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label optionLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label transactionAmountLabel;

    @FXML
    private Label transactionTypeLabel;

    @FXML
    private MenuButton transactionTypeMenuButton;

    @FXML
    private MenuItem depositMenuItem;

    @FXML
    private MenuItem withdrawlMenuItem;

    @FXML
    private Slider interestRateSlider;

    @FXML
    private TextArea outputTextField;

    @FXML
    private TextField accountNumberTextField;

    @FXML
    private TextField additionalTextField;

    @FXML
    private TextField balanceTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField optionTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField transactionAmountTextField;

    @FXML
    private ToggleButton checkingToggleButton;

    @FXML
    private ToggleButton savingsToggleButton;


    /**
	 * Prints Text to the indicated TextField
	 * 
	 * @param where (the TextField) the string should be printed to 
	 * @param textToPrint the String to be printed
	 */
    private void printText(TextField where, String textToPrint) {
		//output text
    	where.setText(textToPrint);
    	where.requestFocus();
    } 

    /**
	 * Prints Text to the main TextArea output
	 * 
	 * @param textToPrint the String to be printed
	 */
    private void printText(String textToPrint) {
		//output to the main text area
		outputTextField.setText(textToPrint);
		outputTextField.requestFocus();
    } 

    /**
	 * Checks to where the error text should be output to and prints the error
	 * 
	 * @param errorMessage the String to be printed
	 */
    private void checkInputError(String errorMessage) {
    	String[] sep = errorMessage.split(":"); //{Text field, Error message}
    	
    	switch(sep[0]) {
    	case ("accountNumber"):
    		printText(accountNumberTextField, sep[1]);
    		break;
    	case ("additional"):
    		printText(additionalTextField, sep[1]);
    		break;
    	case ("balance"):
    		printText(balanceTextField, sep[1]);
    		break;
    	case ("email"):
    		printText(emailTextField, sep[1]);
    		break;
    	case ("firstName"):
    		printText(firstNameTextField, sep[1]);
    		break;
    	case ("lastName"):
    		printText(lastNameTextField, sep[1]);
    		break;
    	case ("option"):
    		printText(optionTextField, sep[1]);
    		break;
    	case ("phone"):
    		printText(phoneNumberTextField, sep[1]);
    		break;
    	case ("transactionAmount"):
    		printText(transactionAmountTextField, sep[1]);
    		break;
    	case (""):
    		//output to the main text area
    		printText(sep[1]);
    		break;
    	default:
    		printText(errorMessage);
    	}
    }
    

    /**
	 * Hides or shows the entire Transaction details and sets the fields to default
	 * 
	 * @param show a boolean value indicating whether the details are being hidden or not
	 */
    private void showTransaction(boolean show) {
    	showAccountNumber(show);
    	transactionGridpane.setVisible(show);
    	
    	transactionAmountTextField.setText("");
    	transactionTypeMenuButton.setText("Deposit or Withdrawl");
    	runButton.setText("Make Transaction");
    }

    /**
	 * Hides or shows the entire account number details and sets the fields to default
	 * 
	 * @param show a boolean value indicating whether the details are being hidden or not
	 */
    private void showAccountNumber(boolean show) {
    	showAccountAdding(false);//by default hide the account adding part
    	transactionGridpane.setVisible(false);//by default hide the transaction part
    	runButton.setText("Run");
    	
    	runGridpane.setVisible(show);
    	accountNumberGridpane.setVisible(show);
    	optionTextField.setText("");
    }

    /**
	 * Hides or shows the entire account adding details and sets the fields to default
	 * 
	 * @param show a boolean value indicating whether the details are being hidden or not
	 */
    private void showAccountAdding(boolean show) {
    	
    	addAccountGridPane.setVisible(show);
     	accountTypeGridPane.setVisible(show);

     	accountNumberTextField.setText("");
    	balanceTextField.setText("");
    	firstNameTextField.setText("");
    	lastNameTextField.setText("");
    	phoneNumberTextField.setText("");
    	emailTextField.setText("");
    }
    

    /**
	 * Sets everything to the basic view, where most details are hidden
	 * 
	 */
    private void toBasicView() {
		currentState = states.BASIC;
		
    	showTransaction(false);
    	showAccountAdding(false);
    	
    	//outputTextField.setText("");
    }

    /**
	 * when the button to print all accounts is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void printAllAccountsButtonPressed(ActionEvent event) {
		toBasicView();//reset to basic
    	try {
    		printText(bank.printAccountDetails());
    	}catch (Exception x) {
    		checkInputError(x.getMessage());
    	}
    }

    /**
	 * when the button to update all accounts is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void monthlyUpdateButtonPressed(ActionEvent event) {
		toBasicView();//reset to basic
    	try {
    		bank.monthlyUpdate();
    		printText("Successfully ran monthly update");
    	}catch (Exception x) {
    		checkInputError(x.getMessage());
    	}
    }

    /**
	 * when the button to read the input records is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void readrecordsButtonPressed(ActionEvent event) {
		toBasicView();//reset to basic
    	try {
    		bank.readRecords();
    		printText("Successfully read records");
    	}catch (Exception x) {
    		checkInputError(x.getMessage());
    	}
    }

    /**
	 * when the button to add an account is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void addAccountButtonPressed(ActionEvent event) {
		toBasicView();//reset to basic
		setChecking(true); //by default show chequing account
    	showAccountAdding(true);
    }
    

    /**
	 * Checks values to see if they are empty or negative, to filter them out before having to go through the exceptions
	 * 
	 * @throws Exception when the value is negative or empty
	 * @param currentWorkingOn a String value indicating the TextField the error (if exists) should be sent to 
	 * @param number a String value holding the value inserted by the user
	 * @return a double value 'number'
	 */
    private double checkBasicNumber(String currentWorkingOn, String number) throws Exception{
    	double numberValue =0d;
    	if (number.equals("") || number.equals(null) ) {
    		throw new Exception(currentWorkingOn + ":Enter a value");
    	}
    	try {
    		numberValue = Double.valueOf(number);
    		//check if it is a negative
    		if (numberValue < 0) {
    			throw new Exception();
    		}
    	}catch (Exception x) {
    		//need to check for String-->int (or simmilar) conversion error from textfield
    		checkInputError( currentWorkingOn + ":Invalid input" );
    	}
    	
    	return numberValue;
    }
    

    /**
	 * when the button to create an account is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void createUserButtonPressed(ActionEvent event) { 	
    	try {
    		
    		//int accountNumber = Integer.valueOf(accountNumberTextField.getText());
    		int accountNumber = (int) checkBasicNumber("accountNumber", accountNumberTextField.getText());
    		
    		String firstName = firstNameTextField.getText();
    		String lastName = lastNameTextField.getText();
    		String phone = phoneNumberTextField.getText();
    		String email = emailTextField.getText();


    		//double balance = Double.valueOf(balanceTextField.getText());
    		double balance = checkBasicNumber("balance", balanceTextField.getText());
    		
    		
    		//double addtional = Double.valueOf(additionalTextField.getText());
    		double addtional = checkBasicNumber("additional", additionalTextField.getText());

    		
			Client inputUser = new Client(firstName, lastName, phone, email);
			BankAccount inputAccount;
			//work with a demo rather than the actual account array
    		//since you don't want to construct it in to the actual bank
			
    		if ( savingsToggleButton.isSelected()) {
    			//set as Savings accounts
        		//double interestRate = Double.valueOf(interestRateValueLabel.getText().replace("%", ""));
        		//double interestRate = Double.valueOf(interestRateValueLabel.getText());
        		double interestRate = Double.valueOf(interestRateSlider.getValue()/100);
        		inputAccount = new SavingsAccount(accountNumber, inputUser, balance, addtional, interestRate);
    		}else {
    			//set as Chequing account
    			inputAccount = new ChequingAccount(accountNumber, inputUser, balance, addtional);
    		}
    		
    		bank.addAccount(inputAccount);
    		toBasicView();//reset to basic
    		printText("Account successfully created");
    	}catch (Exception x) {
    		checkInputError(x.getMessage());
    	}
    }

    /**
	 * when the button to update an account is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clickeds
	 */
    @FXML
    void updateAccountButtonPressed(ActionEvent event) {
		toBasicView();//reset to basic
    	currentState = states.UPDATE_ACCOUNT;
    	showTransaction(true);
    }

    /**
	 * When the run buttons for various processes are selected, check the current process type and run accordingly
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void runButtonPressed(ActionEvent event) {
    	try {
    		//===============================================================================================================
    		int accountNumber = (int) checkBasicNumber( "option", optionTextField.getText());

    		if (currentState == states.UPDATE_ACCOUNT) {
    			//transaction
	    	    String depositOrWithdrawl;
	    		if (transactionTypeMenuButton.getText().equals(depositMenuItem.getText())) {
	    			depositOrWithdrawl = "deposited to";
	    		}else if (transactionTypeMenuButton.getText().equals(withdrawlMenuItem.getText())) {
	    			depositOrWithdrawl = "withdrew from";
	    		}else {
	    			throw new Exception(":Please select a transaction type");
	    		}
	    		
	    		double amount = checkBasicNumber( "transactionAmount", transactionAmountTextField.getText());
	    		
	    		bank.updateAccount(accountNumber, depositOrWithdrawl, amount);
	    		String[] forPrint = depositOrWithdrawl.split(" ");
	    		printText("Successfully " + forPrint[0] + " $" + amount + " " + forPrint[1] + " account #" + accountNumber);
	    		
    		}else if (currentState == states.PRINTING_ACCOUNT) {
    			//printing the details of the account
    			printText( bank.displayAcount(accountNumber) );
    		}
    		
    	}catch (Exception x) {
    		checkInputError(x.getMessage());
    	}
    }

    /**
	 * when the button to display an account is pressed, attempt to do so
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void displayAccountButtonPressed(ActionEvent event) {
		toBasicView();//reset to basic
    	currentState = states.PRINTING_ACCOUNT;
    	showAccountNumber(true);
    }
    

    /**
	 * when the button to quit the program is pressed, exit
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void quitButtonPressed(ActionEvent event) {
    	currentState = states.END;
    	Platform.exit();
    }

    /**
	 * sets the account type as checking or savings and modify the fields accordingly
	 * 
	 * @param isChecking a boolean value indicating whether the account creation should be of type checking or not
	 */
    private void setChecking(boolean isChecking) {
    	checkingToggleButton.setSelected(isChecking);
    	savingsToggleButton.setSelected(!isChecking);
    	
    	interestRateLabel.setVisible(!isChecking);
    	interestRateSlider.setVisible(!isChecking);
    	interestRateValueLabel.setVisible(!isChecking);
    	if (isChecking) {
    		additionalLabel.setText("Monthly Fee");
    	}else {
    		additionalLabel.setText("Minimum Balance");
    		interestRateValueLabel.setText("50%"); //by default, set it to the average
    	}
    }

    /**
	 * 
	 * when the toggle button for a checking account type is pressed, set it to selected
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void checkingToggleButtonChecked(ActionEvent event) {
    	setChecking(true);
    }

    /**
	 * when the toggle button for a savings account type is pressed, set it to selected
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void savingsToggleButtonChecked(ActionEvent event) {
    	setChecking(false);
    }

    /**
	 * when the button to cancel process is pressed, cancel and go back to basic view
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void cancelProcess(ActionEvent event) {
		toBasicView();//reset to basic
    }

    /**
	 * when the menu item for a deposit transaction is pressed, set it to be the text of the menu
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void depositMenuItemSelected(ActionEvent event) {
    	transactionTypeMenuButton.setText(depositMenuItem.getText());
    }
    
    /**
	 * when the menu item for a withdrawl transaction is pressed, set it to be the text of the menu
	 * 
	 * @param event an ActionEvent for when the button is clicked
	 */
    @FXML
    void withdrawlMenuItemSelected(ActionEvent event) {
    	transactionTypeMenuButton.setText(withdrawlMenuItem.getText());
    }
  
    /**
   	 * initialize the controller
   	 */
   	public void initialize() {
   		//called by the FXMLLoader to initialize the controller
   		
   		//listener for changes to the sliders' values
		interestRateSlider.valueProperty().addListener(new ChangeListener<Number>() {
	
			/**
			 * Checks and updates the value based on the slider
			 * 
			 * @param ov an ObservableValue
			 * @param oldValue a Number that holds the old value
			 * @param newValue a Number that holds the new changed value
			 */		
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				double value = Double.valueOf(newValue.intValue());
				//percent format formats 1 --> 100% so need to divide by 100
				interestRateValueLabel.setText( percent.format(value/100) );
				
			}
		}
		);
   		
   	}
}
