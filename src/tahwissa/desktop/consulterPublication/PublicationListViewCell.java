/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.consulterPublication;

import entity.ImageDB;
import entity.Publication;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author BRAHMI
 */
public class PublicationListViewCell extends ListCell<Publication> {

    @FXML
    private Label cellTitle;

    @FXML
    private Label cellDate;

    @FXML
    private Text cellContenu;

    @FXML
    private ImageView cellImage;

    @FXML
    private GridPane cellGrid;

    @FXML
    private FXMLLoader mLLoader;

    public PublicationListViewCell() {

    }

    @Override
    protected void updateItem(Publication publication, boolean empty) {
        if ((publication!=null) && (publication.getImages().size() != 0) ){
            super.updateItem(publication, empty);

            if (empty || publication == null) {

                setText(null);
                setGraphic(null);

            } else {
                if (mLLoader == null) {
                    mLLoader = new FXMLLoader(getClass().getResource("/tahwissa/desktop/consulterPublication/ListCell.fxml"));
                    mLLoader.setController(this);

                    try {
                        mLLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                cellTitle.setText(publication.getTitre());
                cellDate.setText(publication.getDateHeurePublication().toString());
                cellContenu.setText(publication.getContenu());
                //System.out.println("Is Publication null ?"+publication.get==null);

                File file = new File(DetailsPublicationController.pathImage + publication.getImages().get(publication.getImages().size() - 1).getChemin());
                System.out.println(DetailsPublicationController.pathImage + publication.getImages().get(publication.getImages().size() - 1).getChemin());
                try {
                    String local = file.toURI()
                            .toURL()
                            .toString();
                    Image image = new Image(local);
                    cellImage.setImage(image);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PublicationListViewCell.class.getName()).log(Level.SEVERE, null, ex);
                }

                setText(null);
                setGraphic(cellGrid);
            }
        }

    }

}
