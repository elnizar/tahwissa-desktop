/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Meedoch
 */
public class Notification {
    
   
    
    private static final Image img= new Image("/success.png");
    public static void createNotification(String title, String message) {
        ImageView i = new ImageView(img);
        i.setFitWidth(50);
        i.setFitHeight(50);
        i.setImage(img);
        Notifications n = Notifications.create()
                .title(title)
                .text(message)
                .graphic(i)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        n.show();
    }
}
