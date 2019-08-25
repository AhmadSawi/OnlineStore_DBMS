package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class CityController{
	
	@FXML Button backButton;
	@FXML TextField nameTF;
	@FXML TextField IDTF;
    @FXML TextField answerNameTF;
    @FXML Label InfoLabel;
    @FXML Label addInfoLabel;

	public void getInfoPressed(ActionEvent e) {
		InfoLabel.setText(" ");
		answerNameTF.setText("");
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "select * from city where CityID = " + Integer.parseInt(IDTF.getText());
			ResultSet result = db.read(sql);
			if(result.next()){
				answerNameTF.setText(result.getString(2));
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
	
	public void deletePressed(ActionEvent e){	
		InfoLabel.setText("");
		if(IDTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		
		DatabaseAPI db;
		try{
			db = new DatabaseAPI();
			String sql = "delete from city where CityID = " + Integer.parseInt(IDTF.getText());
			db.write(sql);
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
		InfoLabel.setText("it has been successfully deleted");
			
	}
	
	public void allCustomersInCityPressed(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("allCustomersInCity.fxml"));
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    Parent root = loader.load();
	    Scene scene = new Scene(root,800,600);
	    stage.setScene(scene);
	}
	
	public void addPressed(ActionEvent e){	
		addInfoLabel.setText("");
		if(nameTF.getText().equals("")){
			showInputErrorMessage();
			return;
		}
		try {
			DatabaseAPI db;
			db = new DatabaseAPI();
			String sql = "Insert into city(CityName) values('" + nameTF.getText() + "')";
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
	
	public void clearPressed(ActionEvent e){
		nameTF.clear();
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
		alert.setContentText("One or more of the desegnated feilds is empty or has a wrong value!\n");
		alert.showAndWait();
	}
	
}
