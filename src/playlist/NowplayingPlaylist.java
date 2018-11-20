/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

/**
 *
 * @author Priyanka
 */
public class NowplayingPlaylist {
    private NowPlayingPlaylistController control;
    
    private NowplayingPlaylist() {
    }
    
    public void setController(NowPlayingPlaylistController control){
        this.control = control;
    }
    
    public NowPlayingPlaylistController getController(){
        return control;
    }
    
    public static NowplayingPlaylist getInstance() {
        return NowplayingPlaylistHolder.INSTANCE;
    }
    
    private static class NowplayingPlaylistHolder {

        private static final NowplayingPlaylist INSTANCE = new NowplayingPlaylist();
    }    
}
