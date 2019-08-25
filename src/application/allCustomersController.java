package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class allCustomersController implements Initializable{
	
	@FXML TableView<Customer> table;
	@FXML TableColumn<Customer, String> CIDCol;
	@FXML TableColumn<Customer, String> NameCol;
	@FXML TableColumn<Customer, String> EmailCol;
	@FXML TableColumn<Customer, String> PasswordCol;
	@FXML TableColumn<Customer, String> DOBCol;
	@FXML TableColumn<Customer, String> CityCol;
	@FXML TableColumn<Customer, String> PhoneCol;
	@FXML TableColumn<Customer, String> CardCol;

	@FXML Button backButton;
	
	ObservableList<Customer> data = FXCollections.observableArrayList();
	
	
	public void initialize(URL url, ResourceBundle rb) {

		CIDCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
		NameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
		EmailCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		PasswordCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("password"));
		DOBCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("dob"));
		CityCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
		PhoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
		CardCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("card"));
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT C.CustomerID, C.Cname, C.email, C.password, C.DOB, M.CityName, P.phoneNumber, Q.CardNumber "
					+ "FROM customer C, City M, customer_phone P, customer_creditcard Q "
					+ "WHERE C.customercity = M.CityID "
					+ "AND C.CustomerID = P.customerID "
					+ "AND C.CustomerID = Q.CustomerID;";
			ResultSet r = db.read(sql);
			while(r.next()){
				data.add(new Customer(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8)));
			}
			table.setItems(data);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
	public void toCustomer(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}

	
}
