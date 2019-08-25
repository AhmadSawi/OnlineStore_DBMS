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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class WarehouseController implements Initializable{
	
	@FXML Button backButton;
	@FXML TextField sizeTF;
	@FXML ComboBox<String> cityCB;
	@FXML TextField IDTF;
	@FXML ComboBox<String> pidCB;
	@FXML TextField pidQuantity;
    @FXML Label InfoLabel;
    @FXML Label InfoLabel2;
    @FXML Label addInfoLabel;
    @FXML ComboBox<String> WIDchooserCB;
	
	
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
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT WarehuoseID FROM warehuose";
			ResultSet r = db.read(sql);
			while(r.next()){
				WIDchooserCB.getItems().add(r.getString(1));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT ProductID FROM products";
			ResultSet r = db.read(sql);
			while(r.next()){
				pidCB.getItems().add(r.getString(1));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	public void getproductInfoPressed(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("productInWarehouseScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void getEmployeeInfoPressed(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployyesInWarehouseScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void deletePressed(ActionEvent e){
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "delete from warehuose where WarehuoseID = " + Integer.parseInt(IDTF.getText());
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
	}
	
	public void addProductPressed(ActionEvent e){	
		if(pidCB.getValue() == null || WIDchooserCB.getValue() == null){
			showInputErrorMessage();
			return;
		}
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "Insert into wearhouse_has_product(Warehouse, quantity, product) "
					+ "values(" + WIDchooserCB.getValue() + "," + Integer.parseInt(pidQuantity.getText()) + "," + pidCB.getValue() + ");";
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
		InfoLabel2.setText("added successfully");
	}
	
	public void clearPressed(ActionEvent e){
		sizeTF.clear();
	}
	
	public void addPressed(ActionEvent e){	
		if(sizeTF.getText().equals("") || cityCB.getValue() == null){
			showInputErrorMessage();		
			return;
		}
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			int cityID = findCityID(cityCB.getValue());
			String sql = "Insert into warehuose(size, city) "
					+ "values(" + Integer.parseInt(sizeTF.getText()) + "," + cityID + ");";
			db.write(sql);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		addInfoLabel.setText("it was successfully added!");
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
	
	/*
	private int getIncrementValue() throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'OnlineStoreSchema'AND TABLE_NAME = 'warehuose';";
		ResultSet r = db.read(sql);
		if(r.next())
			return Integer.parseInt(r.getString(1));
		else return -1;
	}
	
	private void fixIncrement() throws ClassNotFoundException, SQLException{
		int old = getIncrementValue();
		int n = old - 1;
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "ALTER TABLE `OnlineStoreSchema`.`warehuose`  AUTO_INCREMENT = " + n + ";";
		db.write(sql);
	}
	*/
	
}
