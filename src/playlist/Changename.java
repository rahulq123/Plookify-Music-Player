/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

/**
 *
 * @author ec14211
 */
public class Changename {
    private ChangePlaylistNameController controller;
    
    private Changename() {
    }
    
    public void setController(ChangePlaylistNameController controller){
        this.controller = controller;
    }
    
    public ChangePlaylistNameController getcontroller(){
        return controller;
    }
    
    public static Changename getInstance() {
        return ChangenameHolder.INSTANCE;
    }
    
    private static class ChangenameHolder {

        private static final Changename INSTANCE = new Changename();
    }
}
