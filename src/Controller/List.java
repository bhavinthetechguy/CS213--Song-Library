/*
 * Bhavin Patel
 * Aksharkumar Patel
 */

package Controller;

public class List 
{
    String song;
    String artist;
    String album;
    String year;

    public List()
    {
    	
    }
    public List (String song, String artist, String album, String year)
    {
        this.song = song;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }
    
    public String getSong() 
    {
        return song;
    }
    
    public String getAlbum() 
    {
        return album;
    }

    public String getArtist() 
    {
        return artist;
    }

    public String getYear() 
    {
        return year;
    }

    public void setSong(String song)
    {
        this.song = song;
    }

    public void setArtist(String artist) 
    {
        this.artist = artist;
    }

    public void setAlbum(String album) 
    {
        this.album = album;
    }

    public void setYear(String year) 
    {
        this.year = year;
    }
    
    public static String CreateKey(String song, String artist) 
    {
        return (song + "\t" + artist).toUpperCase();
    }
    
    public String getKey() 
    {
        return CreateKey(song, artist);
    }

    public int Comparable(List list)
    {
        return this.getKey().compareTo(list.getKey());
    }
    
    @Override
    public String toString() 
    {
        return song +"  "+ artist;
    }
    
    /*public String toString()
    {
    	return song +" - by - "+ artist + " "+album + " "+year;
    }*/

    public String saveToFile() 
    {
        return song + "\t" + artist + "\t" + album + "\t" + year+ "\t" + "Checked\n";
    }
}
