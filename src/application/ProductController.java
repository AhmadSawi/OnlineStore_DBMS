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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ProductController implements Initializable{
	
	@FXML Button backButton;
	@FXML TextField nameTF;
	@FXML TextField priceTF;
	@FXML ComboBox<String> typeCB;
	@FXML TextArea descriptionTA;
	@FXML TextField IDTF;
    @FXML Label InfoLabel;
    @FXML Label addInfoLabel;
    @FXML TextField answerNameTF;
    @FXML TextField answerPriceTF;
    @FXML TextArea answerDescriptionTF;
    @FXML TextField answerCategoryTF;
	
	public void initialize(URL url, ResourceBundle rb) {

		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT CategoryName FROM category";
			ResultSet r = db.read(sql);
			while(r.next()){
				typeCB.getItems().add(r.getString(1));
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
		priceTF.clear();
		descriptionTA.clear();
	}
	
	public void addPressed(ActionEvent e){	
		if(nameTF.getText().equals("") || priceTF.getText().equals("") || descriptionTA.getText().equals("")
		  || typeCB.getValue() == null){
			showInputErrorMessage();	
			return;
		}
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			int catID = findCategory(typeCB.getValue());
			String sql = "Insert into products(PricePerItem, description, Category, name) "
					+ "values(" + Double.parseDouble(priceTF.getText()) + ",'" + descriptionTA.getText() + "',"
					+ catID + ",'" + nameTF.getText() + "');";
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
		addInfoLabel.setText("it was successfully added!");
	}
	
	public void deletePressed(ActionEvent e){
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "delete from products where ProductID = " + Integer.parseInt(IDTF.getText());
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
	
	public void getInfoPressed(ActionEvent e){
		InfoLabel.setText(" ");
		answerNameTF.setText("");
		answerCategoryTF.setText("");
		answerDescriptionTF.setText("");
		answerPriceTF.setText("");
		
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "select * from products where ProductID = " + Integer.parseInt(IDTF.getText());
			ResultSet result = db.read(sql);
			if(result.next()){
				answerPriceTF.setText(result.getString(2) + "$");
				answerDescriptionTF.setText(result.getString(3));
				answerCategoryTF.setText(getCategory(Integer.parseInt(result.getString(4))));
				answerNameTF.setText(result.getString(5));
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
	
	public void backPressed(ActionEvent e)throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void CustomersWhoBoughtPressed(ActionEvent e)throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("allCustomersWhoBoughtScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void seeAllPressed(ActionEvent e)throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("allProducts.fxml"));
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
	
	/*
	private int getIncrementValue() throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'OnlineStoreSchema'AND TABLE_NAME = 'products';";
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
		String sql = "ALTER TABLE `OnlineStoreSchema`.`products`  AUTO_INCREMENT = " + n + ";";
		db.write(sql);
	}
	*/
	
	private int findCategory(String str) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT CategoryID FROM category where CategoryName = " + "'" + str + "';";
		ResultSet r = db.read(sql);
		if(r.next())
			return Integer.parseInt(r.getString(1));
		else return -1;
	}
	
	private String getCategory(int c) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT CategoryName FROM category where CategoryID = " + c + ";";
		ResultSet r = db.read(sql);
		if(r.next())
			return r.getString(1);
		else
			return "";
	}
	
}
