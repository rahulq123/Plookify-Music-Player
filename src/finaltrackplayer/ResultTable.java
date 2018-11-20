package finaltrackplayer;

public class ResultTable { 

 
    private String artist; 
    private String genre; 
    private String name;
    private int length;
 
 
     public ResultTable(){ 
         this.artist = ""; 
         this.genre = ""; 
         this.name = "";
         this.length = 0;
     } 
 
 
     public ResultTable(String artist, String genre, String name, int length){ 
         this.artist = artist; 
         this.genre = genre; 
         this.name = name;
         this.length = length;
     } 
 
 
     public String getArtist() { 
         return artist; 
     } 
 
 
     public void setArtist(String artist) { 
         this.artist = artist; 
     } 
 
 
     public String getGenre() { 
         return genre; 
     } 
 
 
     public void setGenre(String genre) { 
         this.genre = genre; 
     } 
 
 
     public String getName() { 
         return name; 
     } 
 
 
     public void setName(String name) { 
         this.name = name; 
     } 
     
     public int getLength() {
         return length;
     }
     
     public void setLength(int length) {
         this.length = length;
     }
 } 
