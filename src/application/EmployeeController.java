package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class EmployeeController implements Initializable{
	
	@FXML Button backButton;
	@FXML TextField nameTF;
	@FXML TextField salaryTF;
	@FXML ComboBox<String> typeCB;
	@FXML ComboBox<String> cityCB;
	@FXML DatePicker dobTF;
	@FXML TextField IDTF;
    @FXML Label InfoLabel;
    @FXML Label addInfoLabel;
    @FXML TextField answerNameTF;
    @FXML TextField answerDOBTF;
    @FXML TextField answerSalaryTF;
    @FXML TextField answerWearhouseTF;

    
	public void initialize(URL url, ResourceBundle rb) {
		typeCB.getItems().addAll("Administrator", "worker");
	
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT WarehuoseID FROM warehuose";
			ResultSet r = db.read(sql);
			while(r.next()){
				cityCB.getItems().add(r.getString(1));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	public void clearPressed(ActionEvent e){
		nameTF.clear();
		salaryTF.clear();
	}
	
	public void addPressed(ActionEvent e){	
		if(nameTF.getText().equals("") || salaryTF.getText().equals("") || typeCB.getValue() == null 
		   || dobTF.getValue() == null || cityCB.getValue() == null){
			showInputErrorMessage();
			return;
		}
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "Insert into employee(name, DOB, salary, wearhouse) values('" + nameTF.getText() + "','" 
				+ Date.valueOf(dobTF.getValue()) + "'," + Double.parseDouble(salaryTF.getText()) + "," 
				+ Integer.parseInt(cityCB.getValue()) + ")";
			db.write(sql);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (NumberFormatException nfe) {
			showInputErrorMessage();
			return;
		}	
		
		//add type here
		if(typeCB.getValue().equals("Administrator")){
			try {
				int ID = getIncrementValue();
				ID--; //to get the right one
				DatabaseAPI db;
				db = new DatabaseAPI();
				String sql = "Insert into administrator(EID) values(" + ID + ")";
				db.write(sql);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}else{
			try {
				int ID = getIncrementValue();
				ID--; //to get the right one
				DatabaseAPI db;
				db = new DatabaseAPI();
				String sql = "Insert into worker(EID) values(" + ID + ")";
				db.write(sql);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}

		addInfoLabel.setText("it was successfully added!");
		
		
	}
	
	public void deletePressed(ActionEvent e){	
		InfoLabel.setText("");
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "delete from employee where EmployeeID = " + Integer.parseInt(IDTF.getText());
			db.write(sql);
			InfoLabel.setText("it has been successfully deleted");
		} catch (NumberFormatException nfe) {
			showInputErrorMessage();
			return;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			InfoLabel.setText("nothing to be deleted");
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			InfoLabel.setText("nothing to be deleted");
			e1.printStackTrace();
		}
		
		answerNameTF.setText("");
		answerDOBTF.setText("");
		answerSalaryTF.setText("");
		answerWearhouseTF.setText("");
	}
	
	public void getInfoPressed(ActionEvent e){
		InfoLabel.setText(" ");
		answerNameTF.setText("");
		answerDOBTF.setText("");
		answerSalaryTF.setText("");
		answerWearhouseTF.setText("");
		
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "select * from employee where EmployeeID = " + Integer.parseInt(IDTF.getText());
			ResultSet result = db.read(sql);
			if(result.next()){
				answerNameTF.setText(result.getString(2));
				answerDOBTF.setText(result.getString(3));
				answerSalaryTF.setText(result.getString(4) + "$");
				answerWearhouseTF.setText(result.getString(5));
				InfoLabel.setText("see the answer in the feild above!");
			}else
				
				InfoLabel.setText("no result!");
		} catch (NumberFormatException nfe) {
			showInputErrorMessage();
			return;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void updatePressed(ActionEvent e){
		InfoLabel.setText(" ");

		if(IDTF.getText().equals("") || answerNameTF.getText().equals("") 
				|| answerDOBTF.getText().equals("") || answerSalaryTF.getText().equals("")
				|| answerWearhouseTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "update employee "
					+ "set name = '" + answerNameTF.getText() + "' , DOB = '" + answerDOBTF.getText() 
			+ "' , salary = " + Double.parseDouble(answerSalaryTF.getText()) 
			+ " , wearhouse = " + Integer.parseInt(answerWearhouseTF.getText())
			+ " where EmployeeID = " + Integer.parseInt(IDTF.getText());
			db.write(sql);
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong Input");
			alert.setContentText("One or more of the changed values is wrong!\n");
			alert.showAndWait();
			return;
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong Input");
			alert.setContentText("One or more of the changed values is wrong!\n");
			alert.showAndWait();
			return;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InfoLabel.setText("updated!");
	}
	
	public void backPressed(ActionEvent e)throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void seeEmployeesPressed(ActionEvent e)throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("allEmployeeScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	private void showInputErrorMessage(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wrong Input");
		alert.setContentText("One or more of the desegnated feilds is empty!\n");
		alert.showAndWait();
	}
	
	
	private int getIncrementValue() throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'OnlineStoreSchema'AND TABLE_NAME = 'employee';";
		ResultSet r = db.read(sql);
		if(r.next())
			return Integer.parseInt(r.getString(1));
		else return -1;
	}
	
	/*
	private void fixIncrement() throws ClassNotFoundException, SQLException{
		int old = getIncrementValue();
		int n = old - 1;
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "ALTER TABLE `OnlineStoreSchema`.`employee`  AUTO_INCREMENT = " + n + ";";
		db.write(sql);
	}
	*/
	
}
