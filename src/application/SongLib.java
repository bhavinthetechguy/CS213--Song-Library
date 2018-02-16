/*
 * Bhavin Patel 
 * Aksharkumar Patel
 */


package application;
	
import Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class SongLib extends Application {
	@Override
	public void start(Stage stage) throws Exception 
	{	
		/*FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ApplicationView.fxml"));
		
		Parent root = loader.load();*/
		
		FXMLLoader loader = new FXMLLoader();   
	    loader.setLocation(getClass().getResource("/Layout/ApplicationView.fxml"));
	    GridPane root = (GridPane)loader.load();
	   	
		Controller controller = loader.getController();
	    controller.start();
		
		Scene scene = new Scene(root);
		stage.setTitle("Song Library"); 
        stage.setScene(scene); 
        stage.sizeToScene(); 
        stage.setResizable(false); 
        stage.show(); 	
	}
	 
	public static void main(String[] args) {
		launch(args);
	}
	
}
