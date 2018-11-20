/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.text.ParseException;

/**
 *
 * @author rahul
 */
public class exampledate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        GetDate date = new GetDate();
        String subscribedDate = "20/03/2016";        
        int minusDate = date.minusSubscribeDate(subscribedDate);
        System.out.println("main method calling Date : " + minusDate);
    }
}
