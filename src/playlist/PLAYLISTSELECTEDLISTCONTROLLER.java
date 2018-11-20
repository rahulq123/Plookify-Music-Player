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
public class PLAYLISTSELECTEDLISTCONTROLLER {
    private PLAYLISTController controller;
    
    private PLAYLISTSELECTEDLISTCONTROLLER() {
    }
    
    public void setController(PLAYLISTController controller){
        this.controller = controller;
    }
    
    public PLAYLISTController getController(){
        return controller;
    }
    
    public static PLAYLISTSELECTEDLISTCONTROLLER getInstance() {
        return PLAYLISTSELECTEDLISTCONTROLLERHolder.INSTANCE;
    }
    
    private static class PLAYLISTSELECTEDLISTCONTROLLERHolder {

        private static final PLAYLISTSELECTEDLISTCONTROLLER INSTANCE = new PLAYLISTSELECTEDLISTCONTROLLER();
    }
}
