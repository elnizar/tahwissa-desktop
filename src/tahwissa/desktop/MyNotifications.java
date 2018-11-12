/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author esprit
 */
public class MyNotifications {
    
    public static void infoNotification(String title, String message) {
        Image img = new Image("/resources/img/customer-service.png") ;
        ImageView image = new ImageView(img);
        image.setFitHeight(64);
        image.setFitWidth(64);
        Notifications notificationBuilder = Notifications.create()
               // .darkStyle()
                .hideAfter(Duration.seconds(6))
                .position(Pos.BOTTOM_RIGHT)
                .title(title)
                .text(message)
                .graphic(image)
                ;
        notificationBuilder.show();
    }
    
    public static void ErrorNotification(String title, String message) {
        Image img = new Image("/resources/img/cancel.png") ;
        ImageView image = new ImageView(img);
        image.setFitHeight(64);
        image.setFitWidth(64);
        Notifications notificationBuilder = Notifications.create()
               // .darkStyle()
                .hideAfter(Duration.seconds(30))
                .position(Pos.BOTTOM_RIGHT)
                .title(title)
                .text(message)
                .graphic(image)
                ;
        notificationBuilder.showError();
    }
    
    public static void WarningNotification(String title, String message) {
        Image img = new Image("/resources/img/warning.png") ;
        ImageView image = new ImageView(img);
        image.setFitHeight(64);
        image.setFitWidth(64);
        Notifications notificationBuilder = Notifications.create()
               // .darkStyle()
                .hideAfter(Duration.seconds(30))
                .position(Pos.BOTTOM_RIGHT)
                .title(title)
                .text(message)
                .graphic(image)
                ;
        notificationBuilder.showWarning();
    }    
    
}
