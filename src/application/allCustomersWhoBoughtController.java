package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class allCustomersWhoBoughtController implements Initializable{

	
	@FXML Button backButton;
	@FXML ComboBox<String> productCB;
	
    @FXML TableView<Customer> table;
	@FXML TableColumn<Customer, String> CIDCol;
	@FXML TableColumn<Customer, String> NameCol;

	ObservableList<Customer> data = FXCollections.observableArrayList();
	
	
	public void initialize(URL url, ResourceBundle rb) {
			
			try {
				DatabaseAPI db;
				db = new DatabaseAPI();
				String sql = "SELECT name FROM products";
				ResultSet r = db.read(sql);
				while(r.next()){
					productCB.getItems().add(r.getString(1));
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} 

	public void toProduct(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	
	public void loadPressed(ActionEvent e) throws IOException{
		
		table.getItems().clear();
		
		if(productCB.getValue() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong Input");
			alert.setContentText("please choose a product first!\n");
			alert.showAndWait();
			return;
		}
		
		CIDCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
		NameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT C.CustomerID, C.Cname "
					+ "FROM customer C "
					+ "WHERE C.CustomerID in " + "( "
					+ "SELECT B.Customer "
					+ "FROM buys B, cart K " 
					+ "WHERE K.productID = " + findProductID(productCB.getValue()) 
					+ " AND K.purchaseID = B.PurchaseID);";
					
			ResultSet r = db.read(sql);
			while(r.next()){
				data.add(new Customer(r.getString(1), r.getString(2), "", "", "", "", "", ""));
			}
			table.setItems(data);
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	}
	
	private int findProductID(String str) throws ClassNotFoundException, SQLException{
		DatabaseAPI db;
		db = new DatabaseAPI();
		String sql = "SELECT ProductID FROM products where name = " + "'" + str + "';";
		ResultSet r = db.read(sql);
		if(r.next())
			return Integer.parseInt(r.getString(1));
		else return -1;
	}
}