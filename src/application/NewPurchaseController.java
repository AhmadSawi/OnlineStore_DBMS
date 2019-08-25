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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class NewPurchaseController implements Initializable{
	
	@FXML TextField IDTF;
	@FXML ComboBox<String> pidCB;
	@FXML TextField pidQuantity;
	@FXML ListView<String> cart;
	@FXML TextArea Recipt;
	@FXML Button backButton;
	@FXML Label totalPrice;
	
	double total = 0;
	ArrayList<Integer> pids = new ArrayList<Integer>();
	ArrayList<Integer> quantities = new ArrayList<Integer>();
	ArrayList<Double> prices = new ArrayList<Double>();
	int numberOfItems = 0;

		
	public void initialize(URL url, ResourceBundle rb) {
		
		totalPrice.setText("0$");
		
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
	
	public void addToCartPressed(ActionEvent e){
		if(IDTF.getText().equals("") || pidCB.getValue() == null || pidQuantity.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		try {
			numberOfItems++;
			pids.add(Integer.parseInt(pidCB.getValue()));
			quantities.add(Integer.parseInt(pidQuantity.getText()));
			prices.add(quantities.get(numberOfItems-1) * findProductPrice(Integer.parseInt(pidCB.getValue())));
			total +=( prices.get(numberOfItems-1));
			totalPrice.setText(total + "$");
			cart.getItems().add(findProductName(Integer.parseInt(pidCB.getValue())));
		} catch (NumberFormatException e1) {
			showInputErrorMessage();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void clearPressed(ActionEvent e){
		numberOfItems = 0;
		total = 0;
		totalPrice.setText(total + "$");
		cart.getItems().clear();
		pids.clear();
		quantities.clear();
		pidQuantity.clear();
	}
	
	
	public void showReciptPressed(ActionEvent e) throws NumberFormatException, ClassNotFoundException, SQLException{
		if(cart.getItems().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Cart");
			alert.setContentText("the shopping cart is empty! please add products first!\n");
			alert.showAndWait();
			return;
		}
		
		if(!seeIfCustomerExists(Integer.parseInt(IDTF.getText()))){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Customer Doesnt exist!");
			alert.setContentText("please use a valid customer ID!\n");
			alert.showAndWait();
			return;
		}
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "Insert into buys(Date, NumberOfItems, TotalPrice, Customer) "
					+ "values('" + LocalDate.now() + "'," 
					+ numberOfItems + ","
					+ total + ",'"
					+ Integer.parseInt(IDTF.getText()) + "');";
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
		
		int currentID = getCurrentPurchaseID();
		System.out.println(currentID);
		for(int i = 0; i < numberOfItems; i++){
			try {
				DatabaseAPI db;
				db = new DatabaseAPI();
				String sql = "Insert into cart(purchaseID, productID, quantity) "
						+ "values(" + currentID + "," 
						+ pids.get(i) + ","
						+ quantities.get(i) + ");";
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
		}
		
		String rText;
		rText =   "**********************************************************\n"
				+ "\t\t\t\t INVOICE\n"
				+ "**********************************************************\n"
				+ "Purchase Numer: \t\t" + currentID + "\n" 
				+ "-----------------------------------------------------------\n"
				+ "Purchase Date: \t\t" + LocalDate.now() + "\n" 
				+ "-----------------------------------------------------------\n"
				+ "Customer Name: \t\t" + getCustomerName(Integer.parseInt(IDTF.getText())) + "\n"
				+ "-----------------------------------------------------------\n" 
				+ "Number Of Items Purchased: \t\t" + numberOfItems + "\n"
				+ "-----------------------------------------------------------\n" 
				+ "Quantity\t\tName\t\t\tPrice \n" 
				+ "_______________________________________________\n";
		
		for(int i = 0; i < numberOfItems; i++){
			rText += quantities.get(i) + "\t\t";
			rText += cart.getItems().get(i) + "\t\t\t\t";
			rText += prices.get(i) + "$\n";
			
		}
			
				rText += ( "-----------------------------------------------------------\n"
				+ "Total price: \t\t" + total + "$\n"
				+ "-----------------------------------------------------------\n");
		
		Recipt.setText(rText);
		
		
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
	
	private double findProductPrice(int id) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT PricePerItem FROM products where ProductID = " + id + ";";
		ResultSet r = db.read(sql);
		if(r.next())
			return Double.parseDouble(r.getString(1));
		else return -1;
	}
	
	private String findProductName(int id) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT name FROM products where ProductID = " + id + ";";
		ResultSet r = db.read(sql);
		if(r.next())
			return r.getString(1);
		else return "";
	}
	
	private boolean seeIfCustomerExists(int id) throws NumberFormatException, SQLException, ClassNotFoundException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT CustomerID FROM customer;";
		ResultSet r = db.read(sql);
		int i = 1;
		while(r.next()){
			if(Integer.parseInt(r.getString(i)) == id)
				return true;
		}
		return false;
	}
	
	private int getCurrentPurchaseID() throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'OnlineStoreSchema'AND TABLE_NAME = 'buys';";
		ResultSet r = db.read(sql);
		if(r.next())
			return (Integer.parseInt(r.getString(1)) - 1);
		else return -1;
	}
	
	private String getCustomerName(int id) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT Cname FROM customer where CustomerID = " + id + ";";
		ResultSet r = db.read(sql);
		if(r.next())
			return r.getString(1);
		else return "";
	}
	
}
