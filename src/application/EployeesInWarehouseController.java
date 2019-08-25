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


public class EployeesInWarehouseController implements Initializable{
	
	@FXML TableView<EmployeesInWarehouse> table;
	@FXML TableColumn<EmployeesInWarehouse, String> WIDCol;
	@FXML TableColumn<EmployeesInWarehouse, String> EIDCol;
	@FXML TableColumn<EmployeesInWarehouse, String> EnameCol;
	@FXML Button backButton;
	
	ObservableList<EmployeesInWarehouse> data = FXCollections.observableArrayList();
	
	
	public void initialize(URL url, ResourceBundle rb) {

		EIDCol.setCellValueFactory(new PropertyValueFactory<EmployeesInWarehouse, String>("EmployeeID"));
		WIDCol.setCellValueFactory(new PropertyValueFactory<EmployeesInWarehouse, String>("warehouseID"));
		EnameCol.setCellValueFactory(new PropertyValueFactory<EmployeesInWarehouse, String>("empName"));
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT E.wearhouse, E.EmployeeID, E.name "
					+ "FROM employee E, warehuose W "
					+ "WHERE W.WarehuoseID = E.wearhouse;"; 
			ResultSet r = db.read(sql);
			while(r.next()){
				data.add(new EmployeesInWarehouse(r.getString(1), r.getString(2), r.getString(3)));
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
