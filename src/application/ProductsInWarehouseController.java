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


public class ProductsInWarehouseController implements Initializable{
	
	@FXML TableView<ProductInWarehouse> table;
	@FXML TableColumn<ProductInWarehouse, String> PIDCol;
	@FXML TableColumn<ProductInWarehouse, String> sizeCol;
	@FXML TableColumn<ProductInWarehouse, String> WIDCol;
	@FXML Button backButton;
	
	ObservableList<ProductInWarehouse> data = FXCollections.observableArrayList();
	
	
	public void initialize(URL url, ResourceBundle rb) {

		sizeCol.setCellValueFactory(new PropertyValueFactory<ProductInWarehouse, String>("quantity"));
		PIDCol.setCellValueFactory(new PropertyValueFactory<ProductInWarehouse, String>("productID"));
		WIDCol.setCellValueFactory(new PropertyValueFactory<ProductInWarehouse, String>("warehouseID"));
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT * FROM wearhouse_has_product";
			ResultSet r = db.read(sql);
			while(r.next()){
				data.add(new ProductInWarehouse(r.getString(1), r.getString(3), r.getString(2) ));
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
	
	
	public void toWarehouse(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WarehouseScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}

	
}
