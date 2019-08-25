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


public class allProductsController implements Initializable{
	
	@FXML TableView<Products> table;
	@FXML TableColumn<Products, String> PIDCol;
	@FXML TableColumn<Products, String> NameCol;
	@FXML TableColumn<Products, String> categoryCol;
	@FXML TableColumn<Products, String> descriptionCol;
	@FXML TableColumn<Products, String> PriceCol;

	@FXML Button backButton;
	
	ObservableList<Products> data = FXCollections.observableArrayList();
	
	
	public void initialize(URL url, ResourceBundle rb) {

		PIDCol.setCellValueFactory(new PropertyValueFactory<Products, String>("id"));
		NameCol.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<Products, String>("category"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Products, String>("description"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<Products, String>("price"));
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT P.ProductID, P.PricePerItem, P.description, C.CategoryName, P.name  "
					+ "FROM products P, category C "
					+ "WHERE P.Category = C.CategoryID";
			ResultSet r = db.read(sql);
			while(r.next()){
				data.add( new Products(r.getString(1), r.getString(2) + "$", r.getString(3), r.getString(4), r.getString(5)) );
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
	
	
	public void toProduct(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}

	
}
