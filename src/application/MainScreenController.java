package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainScreenController {
	
	@FXML Button CustomerButton;
	@FXML Button EmployeeButton;
	@FXML Button ProductButton;
	@FXML Button WarehouseButton;
	@FXML Button CityButton;
	@FXML Button CategoryButton;
	@FXML Button NewPurchaseButton;
	 
	public void aboutUs(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Thank You for using our products. \n \n we are: \n Ahmad Sawi\t\t\t 1150007\n Obeida rimawi\t\t 1150275\n Mohammad dahla\t\t 1152537\n"
				+ "\nYou can contact us at: +972598608932\n");
		alert.showAndWait();
	}
	
public void toCustomer(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerScreen.fxml"));
    Stage stage = (Stage) CustomerButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}

public void toEmployee(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeScreen.fxml"));
    Stage stage = (Stage) EmployeeButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}

public void toProduct(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductScreen.fxml"));
    Stage stage = (Stage) ProductButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}

public void toWarehouse(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("WarehouseScreen.fxml"));
    Stage stage = (Stage) WarehouseButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}

public void toCity(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("CityScreen.fxml"));
    Stage stage = (Stage) CityButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}

public void toCategory(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("CategotyScreen.fxml"));
    Stage stage = (Stage) CategoryButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}

public void toNewPurchase(ActionEvent e) throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPurchaseScreen.fxml"));
    Stage stage = (Stage) NewPurchaseButton.getScene().getWindow();
    Parent root = loader.load();
    Scene scene = new Scene(root,800,600);
    stage.setScene(scene);
}
	
}
