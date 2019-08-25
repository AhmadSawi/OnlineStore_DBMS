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


public class allEmployeesController implements Initializable{
	
	@FXML TableView<Employee> table;
	@FXML TableColumn<Employee, String> EIDCol;
	@FXML TableColumn<Employee, String> NameCol;
	@FXML TableColumn<Employee, String> DOBCol;
	@FXML TableColumn<Employee, String> WarehouseCol;
	@FXML TableColumn<Employee, String> SalaryCol;

	@FXML Button backButton;
	
	ObservableList<Employee> data = FXCollections.observableArrayList();
	
	
	public void initialize(URL url, ResourceBundle rb) {

		EIDCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
		NameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		DOBCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("dob"));
		WarehouseCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("warehouse"));
		SalaryCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
		
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "SELECT * "
					+ "FROM employee E;";
			ResultSet r = db.read(sql);
			while(r.next()){
				data.add( new Employee(r.getString(1), r.getString(2), r.getString(3), r.getString(4) + "$", r.getString(5)) );
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
	
	
	public void toEmployee(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeScreen.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}

	
}
