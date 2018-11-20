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
public class storedController {
    private SearchViewController controller;
    
    private storedController() {
        
    }
    
    public void setController(SearchViewController controller){
        this.controller = controller;
    }
    
    public SearchViewController getController(){
        return controller;
    }

   
    public static storedController getInstance() {
        return storedControllerHolder.INSTANCE;
    }
    
    private static class storedControllerHolder {

        private static final storedController INSTANCE = new storedController();
    }
}
