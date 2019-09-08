package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/** File name:	BankSimulator.java
* Author:		Jo Suh
* Date:			April.16, 2019
* Purpose:		This file holds the BankSimulator class.
*/



/**
* This is a main app class that loads and displays the Bank Simulator's GUI
* 
* @author Jo Suh
* @version 1.0
* @since 1.0
*/
//loads the fxml file, generates a scene, and displays a scene from it
public class BankSimulator extends Application {
	// The starting point for a JavaFX app is an Application subclass,
	// so class BankSimulator extends Application
	// specify the controls' event handlers
	
	/**
	 * 'Runs' the BankSimulator.fxml
	 * <p>
	 * causes the JavaFX runtime to create an object of the BankSimulator class
	 * and calls its start method, passing the Stage object
	 * (which represents the window in which the app will be displayed)
	 * 
	 * @param stage a Stage that will be passed to call the start method
	 */
	@Override
	//public void start(Stage stage) throws Exception{
	public void start(Stage stage) {
		// The JavaFX runtime creates the window

		//creates the GUI, attaches it to a Scene and places it
		// on the Stage the method receives as an argument
		try {
			Parent root = FXMLLoader.load(getClass().getResource("BankSimulator.fxml"));
			Scene scene = new Scene(root); //attach scene graph to scene
			stage.setTitle("Bank Simulator"); //displayed in window's title bar
			stage.setScene(scene); //attach scene to stage
			stage.show(); //display the stage
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initialize the JavaFX runtime and begin executing the app
	 * <p>
	 * by calling class Application's static launch method
	 * 
	 * @param args an array of strings containing the command-line arguments
	 */
	public static void main(String[] args) {
		//create a TipCalculator object and call its start method
		launch(args);
	}
}
