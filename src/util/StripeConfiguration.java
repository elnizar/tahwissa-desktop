/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.stripe.net.RequestOptions.RequestOptionsBuilder;
import entity.Article;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextField;
import tahwissa.desktop.MyNotifications;

/**
 *
 * @author esprit
 */
public class StripeConfiguration {
    
    public static void setStripApiKey() {
        Stripe.apiKey = "sk_test_iidmtq0fuYpShFZH8xfUDGvO";
    }
    
    public static void payerStripe(TextField codeCardField, TextField expMonthField, TextField expYear, double prix) {
        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey("sk_test_iidmtq0fuYpShFZH8xfUDGvO").build();
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        System.out.println("Prix dans Stripe ?"+prix);
        chargeMap.put("amount", new Double(prix).intValue()*100);
        chargeMap.put("currency", "usd");
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", codeCardField.getText());
        cardMap.put("exp_month", Integer.parseInt(expMonthField.getText()));
      System.out.println("Integer Stripe payment "+ Integer.parseInt(expMonthField.getText()));
         System.out.println("Carte Number: "+codeCardField.getText());
//            System.out.println("Year: "+expYear.getText());
//            System.out.println("Month: "+expMonthField.getText());
        
        cardMap.put("exp_year", Integer.parseInt(expYear.getText()));
        System.out.println("Year Integer: "+Integer.parseInt(expYear.getText()));
        chargeMap.put("card", cardMap);
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
              MyNotifications.infoNotification("Paiement effectué", "Paiement effectué avec succès");
        } catch (StripeException e) {
            e.printStackTrace();
            MyNotifications.WarningNotification("Paiement échoué", e.getMessage());

        }
        
    }
    
    public static String payerStripe() {
        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey("sk_test_iidmtq0fuYpShFZH8xfUDGvO").build();
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "usd");
        
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", "4000000000000002");
        cardMap.put("exp_month", 12);
        cardMap.put("exp_year", 2020);
        
        chargeMap.put("card", cardMap);
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
            MyNotifications.infoNotification("Paiement effectué", "Paiement effectué avec succès");
            return "Paiement effectuer avec succès";
        } catch (StripeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            MyNotifications.WarningNotification("Paiement échoué", e.getMessage());

            return e.getMessage();
        }
        
    }
    
    public static void stripeExample() {
            RequestOptions requestOptions = (new RequestOptionsBuilder()).setApiKey("sk_test_iidmtq0fuYpShFZH8xfUDGvO").build();
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "usd");
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", "4242424242424242");
        cardMap.put("exp_month", 12);
        cardMap.put("exp_year", 2020);
        chargeMap.put("card", cardMap);
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
    
}
