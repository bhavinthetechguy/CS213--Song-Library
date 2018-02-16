/*
 * Bhavin Patel
 * Aksharkumar Patel
 */

package Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
	
	@FXML private TextField song;
	@FXML private TextField album;
	@FXML private TextField year;
	@FXML private TextField artist;
	
	@FXML private ListView<List> view;
	
	@FXML private Button add;
	@FXML private Button update;
	@FXML private Button delete;
	@FXML private Button clear;
	
	String song1, year1, artist1, album1;
	private final static String file = "SongList.txt";
		
	Map<String, List> map = new HashMap<>();
	
	List mapToList;
    ObservableList<List> obsList= FXCollections.observableArrayList();;
   
         
	 public void start() throws IOException
	 { 
		 try
			{
				FileReader read = new FileReader(file);
				BufferedReader bRead = new BufferedReader(read);
				String s;
				
				while((s = bRead.readLine()) != null)
				{
					String[] breaking = s.split("\t");
					addHandler(breaking[0],breaking[1],breaking[2],breaking[3]);
				}
				bRead.close();
				read.close();
			}
				
			 catch (FileNotFoundException e)
			 {
				e.printStackTrace();
			 }
		 
		if(obsList != null)
		{
		view.getItems().setAll(obsList);
				
			 view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<List>()
			 {
			     @Override
			     public void changed(ObservableValue<? extends List> observable, List oldValue, List newValue) 
			     {
			    	 if(newValue != null)
			    	 {
			    		 song.setText(newValue.getSong());
			    		 artist.setText(newValue.getArtist());
			    		 album.setText(newValue.getAlbum());
			    		 year.setText(newValue.getYear());
			    	 }
			    	 else
			    	 {
			    		 song.setText("");
			    		 artist.setText("");
			    	     album.setText("");
			    		 year.setText("");
			    	 }
			    	
			     }
					});
			 if(obsList.size() > 0)
			 {
				 view.getSelectionModel().select(0);
			 }
		}
	 }
	 
	
		
	  @FXML
	    private void intializeAdd(ActionEvent event) throws IOException 
	    {
		  
		song1 =song.getText();
		year1 = year.getText();
		artist1 = artist.getText();
		album1 = album.getText();
		
		//year1 = year1.replace(" ", "");
					
		if(song1 == null || song1.trim().equals("") || artist1 == null || artist1.trim().equals(""))
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Details Warning");
			alert.setHeaderText("Warning");
			alert.setContentText("Song and/or Arrtist fiels is Empty!");
			alert.showAndWait();
			return;		
		}

		if(year1 != null)
		{
			if(!year1.trim().equals(""))
			{
				Pattern p = Pattern.compile("\\d\\d\\d\\d");
				Matcher mat = p.matcher(year1);
				if(!mat.matches())
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Year Format");
					alert.setHeaderText("Warning");
					alert.setContentText("Incorrect year format.Valid Year Format is YYYY.Example - 2018 !");
					alert.showAndWait();
					return;	
				}
			}
		}
		
		
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Adding Confirmation");
		confirm.setHeaderText("Confirmation");
		confirm.setContentText("Are you sure you want to Add this song to Library?");
		
		Optional<ButtonType> result = confirm.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			String generateKey = List.CreateKey(song1, artist1);
			mapToList = map.get(generateKey);
			int i;
			if(mapToList == null)
			{
				mapToList = new List(song1,artist1,album1,year1);
				map.put(generateKey, mapToList);
			    i = sortList(mapToList);
			}
			else
			{
				Alert dup = new Alert(AlertType.WARNING);
				dup.setTitle("Duplicate Warning");
				dup.setHeaderText("Warning");
				dup.setContentText("Can't add Duplicate!");
				dup.showAndWait();
				
				int pp = view.getSelectionModel().getSelectedIndex();
				List pp1 = view.getSelectionModel().getSelectedItem();
				view.getSelectionModel().select(pp);
				
				song.setText(pp1.getSong());
				artist.setText(pp1.getArtist());
				album.setText(pp1.getAlbum());
				year.setText(pp1.getYear());
				
				return;
			}
			
			Writer add = new FileWriter(file);
			for(List adding : map.values() )
			{
				add.write(adding.saveToFile());
			}
			
			add.close();
			start();
			view.getItems().setAll(obsList);
			view.getSelectionModel().select(i);
		}
		else
		{
			return;
		}
		
		} 
	  
	    @FXML
	    private void intializeUpdate(ActionEvent event) throws IOException 
	    {
	    	song1 =song.getText();
			year1 = year.getText();
			artist1 = artist.getText();
			album1 = album.getText();
			
	    	if(song1 == null || song1.trim().equals("") || artist1 == null || artist1.trim().equals(""))
			{	
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Details Warning");
				alert.setHeaderText("Warning");
				alert.setContentText("Cannot change to an Empty Song and/or Artist name!");
				alert.showAndWait();
				
				int pp = view.getSelectionModel().getSelectedIndex();
				List pp1 = view.getSelectionModel().getSelectedItem();
				view.getSelectionModel().select(pp);
				
				song.setText(pp1.getSong());
				artist.setText(pp1.getArtist());
				album.setText(pp1.getAlbum());
				year.setText(pp1.getYear());
				
				return;
			}
	    	
			if(year1 != null)
	    	{
				if(!year1.trim().equals(""))
				{
					Pattern p = Pattern.compile("\\d\\d\\d\\d");
					Matcher mat = p.matcher(year1);
					if(!mat.matches())
					{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Invalid Year Format");
							alert.setHeaderText("Warning");
							alert.setContentText("Incorrect year format.Valid Year Format is YYYY.Example - 2018 !");
							alert.showAndWait();
							return;	
					}
				}
			}
			
			List lb = view.getSelectionModel().getSelectedItem();
			String so = lb.getSong();
			String ar = lb.getArtist();
			String al = lb.getAlbum();
			String yr = lb.getYear();
			
			if(so.equals(song1) && ar.equals(artist1) && al.equals(album1) && yr.equals(year1))
			{
				Alert wr = new Alert(AlertType.WARNING);
				wr.setTitle("Nothing Changed");
				wr.setHeaderText("Warning");
				wr.setContentText("Nothing has changed");
				wr.showAndWait();
				return;
			}
			
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Update Confirmation");
			confirm.setHeaderText("Confirmation");
			confirm.setContentText("Are you sure you want to UPDATE the song?");
			
			Optional<ButtonType> result = confirm.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				
	   			int i= view.getSelectionModel().getSelectedIndex();
	   			String old = obsList.get(i).getKey();
	   			String ne = List.CreateKey(song1, artist1);
			
	   			List oldKey = map.get(old);
	   			if(oldKey == null)
	   			{
	   				return;
	   			}
	   			else
	   			{
	   				if(old.equals(ne))
	   				{	
	   					oldKey.setAlbum(album1);
   						oldKey.setYear(year1);
   						
   						Writer add = new FileWriter(file);
   						for(List adding : map.values() )
   						{
   							add.write(adding.saveToFile());
   						}
					
   						add.close();
   						return;
	   					
	   				}
	   				else
	   				{
	   					List newKey = map.get(ne);
	   					if(newKey == null)
	   					{
	   						oldKey.setSong(song1);
	   						oldKey.setArtist(artist1);
	   						oldKey.setAlbum(album1);
	   						oldKey.setYear(year1);
						
	   						map.remove(old);
	   						map.put(ne, oldKey);
						
	   						obsList.remove(i);
	   						i = sortList(oldKey);
						
	   						Writer add = new FileWriter(file);
	   						for(List adding : map.values() )
	   						{
	   							add.write(adding.saveToFile());
	   						}
						
	   						add.close();
	   					}
	   					else
	   					{
	   						Alert alert = new Alert(AlertType.WARNING);
	   						alert.setTitle("Empty Details Warning");
	   						alert.setHeaderText("Warning");
	   						alert.setContentText("No change can be done because duplicate exists in library!");
	   						alert.showAndWait();
	   						
	   						List lp = view.getSelectionModel().getSelectedItem();
	   						song.setText(lp.getSong());
	   						artist.setText(lp.getArtist());
	   						album.setText(lp.getAlbum());
	   						year.setText(lp.getYear());
	   						
	   						return;
	   					}
	   				}
	   			}
	   			//obsList = FXCollections.observableArrayList();
	   			start();
	   			view.getItems().setAll(obsList);
	   			view.getSelectionModel().select(i);
				}
			else
			{
				return;
			}
			
	    }

	    @FXML
	    private void intializeDelete(ActionEvent event) throws IOException 
	    {
	    	song1 =song.getText();
			year1 = year.getText();
			artist1 = artist.getText();
			album1 = album.getText();
			
	    	if(song1 == null || song1.trim().equals("") || artist1 == null || artist1.trim().equals(""))
			{	
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty Details Warning");
				alert.setHeaderText("Warning");
				alert.setContentText("Cannot Delete an Empty List!");
				alert.showAndWait();
				return;	
			}
			
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Delete Confirmation");
			confirm.setHeaderText("Confirmation");
			confirm.setContentText("Are you sure you want to DELETE the song?");
			
			Optional<ButtonType> result = confirm.showAndWait();
			if(result.get() == ButtonType.OK)
			{
			
				int i = view.getSelectionModel().getSelectedIndex();
				String key = obsList.get(i).getKey();
			
				List list = map.get(key);
			
				if(list != null)
				{
					map.remove(key);
					obsList.remove(i);
				
					Writer add = new FileWriter(file);
					for(List adding : map.values() )
					{
						add.write(adding.saveToFile());
					}
				
					add.close();
					start();

					view.getItems().setAll(obsList);			
					view.getSelectionModel().select(i);
				
					if(i == obsList.size())
					{
						view.getSelectionModel().select(i-1);
					}

					return;
				}
				else
				{
					return;
				}
			}
			else
			{
				return;
			}
	    }
	    
	    @FXML
	    void OnActionClear(ActionEvent event) 
	    {
	    	song.setText("");
	    	artist.setText("");
	    	album.setText("");
	    	year.setText("");
	    	
	    	
	    	view.getSelectionModel().select(obsList.size());
	    }
	    
	    public int addHandler(String song, String artist, String album, String year) 
		 {
			 	String generateKey = List.CreateKey(song, artist);
			 	//System.out.println(generateKey);
				mapToList = map.get(generateKey);
				//System.out.println(mapToList);
			 	if(mapToList == null)
			 	{
				mapToList = new List(song,artist,album,year);
				//System.out.println(mapToList);
				map.put(generateKey, mapToList);
				return sortList(mapToList);
			 	}
			 	else
			 	{
			 		return -1;
			 	}
		
		    }
		 
			public int sortList(List mapList) 
			{
				if(obsList == null)
				{
					obsList.add(mapList);
					return 0;
				}
				else
				{
					for(int i=0; i<obsList.size();i++)
					{
						if(mapList.Comparable(obsList.get(i)) < 0)
						{
							//System.out.println(mapList.Comparable(obsList.get(i)));
							//System.out.println("MapList:"+mapList);
							//System.out.println("Obser:"+obsList.get(i));
							obsList.add(i,mapList);
							return i;
						}
					}
					
					obsList.add(mapList);		
					return obsList.size() - 1;
				}
				
			}
}
