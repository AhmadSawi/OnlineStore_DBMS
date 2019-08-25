package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;



public class CustomerController implements Initializable{
	
	@FXML Button backButton;
	@FXML TextField nameTF;
	@FXML TextField emailTF;
	@FXML PasswordField passwordTF;
	@FXML ComboBox<String> cityCB;
	@FXML DatePicker dobTF;
	@FXML TextField phoneTF;
	@FXML TextField creditTF;
	@FXML TextField IDTF;
    @FXML Label InfoLabel;
    @FXML Label addInfoLabel;
    @FXML TextField answerNameTF;
    @FXML TextField answerDOBTF;
    @FXML TextField answerEmailTF;
    @FXML TextField answerPhoneTF;

    
	public void initialize(URL url, ResourceBundle rb) {
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT CityName FROM city";
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
		emailTF.clear();
		passwordTF.clear();
		phoneTF.clear();
		creditTF.clear();
		cityCB.setValue(null);
		dobTF.setValue(null);
	}
	
	public void addPressed(ActionEvent e) throws ClassNotFoundException, SQLException{
		if(nameTF.getText().equals("") || emailTF.getText().equals("") || passwordTF.getText().equals("") 
				|| cityCB.getValue() == null || phoneTF.getText().equals("") || creditTF.getText().equals("")
				|| dobTF.getValue() == null){
			showInputErrorMessage();	
			return;
		}
		
		try{
			Integer.parseInt(phoneTF.getText());
			Integer.parseInt(creditTF.getText());
		} catch (NumberFormatException nfe) {
			showInputErrorMessage();
			return;
		}	
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			int cityid = findCityID(cityCB.getValue());
			String sql = "Insert into customer(Cname, email, password, DOB, customercity) "
					+ "values('" + nameTF.getText() + "','" 
					+ emailTF.getText() + "',"
					+ passwordTF.getText() + ",'"
					+ Date.valueOf(dobTF.getValue()) + "'," 
					+ cityid + ")";
			db.write(sql);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int currentid = getIncrementValue() - 1;
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "Insert into customer_phone(phoneNumber, customerID) "
					+ "values('" + phoneTF.getText() + "'," 
					+ currentid + ")";
			db.write(sql);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "Insert into customer_creditcard(CardNumber, CustomerID) "
					+ "values('" + creditTF.getText() + "'," 
					+ currentid + ")";
			db.write(sql);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		addInfoLabel.setText("it was successfully added!");
		
		nameTF.clear();
		emailTF.clear();
		passwordTF.clear();
		phoneTF.clear();
		creditTF.clear();
		cityCB.setValue(null);
		dobTF.setValue(null);
}
	
	
	public void deletePressed(ActionEvent e){
		if(IDTF.getText().equals("")){
			showInputErrorMessage();	
			return;
		}
		

		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "delete from customer where CustomerID = " + Integer.parseInt(IDTF.getText());
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
		answerEmailTF.setText("");
		answerPhoneTF.setText("");
			
	}
	
	public void getInfoPressed(ActionEvent e){
		InfoLabel.setText(" ");
		answerNameTF.setText("");
		answerDOBTF.setText("");
		answerEmailTF.setText("");
		answerPhoneTF.setText("");
		
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "select * from customer where CustomerID = " + Integer.parseInt(IDTF.getText());
			ResultSet result = db.read(sql);
			if(result.next()){
				answerNameTF.setText(result.getString(2));
				answerEmailTF.setText(result.getString(3));
				answerDOBTF.setText(result.getString(5));
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
		try{
			db = new DatabaseAPI();
			String sql = "select phoneNumber from customer_phone where CustomerID = " + Integer.parseInt(IDTF.getText());
			ResultSet result = db.read(sql);
			if(result.next()){
				answerPhoneTF.setText(result.getString(1));
				InfoLabel.setText("see answer in the feilds above!");
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
	
	public void allCustomersPressed(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("allCustomersScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void backPressed(ActionEvent e)throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
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
	
	private int findCityID(String str) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT CityID FROM city where CityName = " + "'" + str + "';";
		ResultSet r = db.read(sql);
		if(r.next())
			return Integer.parseInt(r.getString(1));
		else return -1;
	}
	
	private int getIncrementValue() throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'OnlineStoreSchema'AND TABLE_NAME = 'customer';";
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
		String sql = "ALTER TABLE `OnlineStoreSchema`.`customer`  AUTO_INCREMENT = " + n + ";";
		db.write(sql);
	}
	*/
	
}
