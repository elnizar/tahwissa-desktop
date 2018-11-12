package tahwissa.desktop.mapDisplay;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderRequest;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import entity.Evenement;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import netscape.javascript.JSObject;

public class MapController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }
    public static Evenement e;
    @Override
    public void mapInitialized() {
       

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(33.8869, 9.5375))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(6);

        map = mapView.createMap(mapOptions);
        
        List<String> places = new ArrayList<String>();
        places.add(e.getDestination());
       
        GeocodingService service = new GeocodingService();
        
        places.forEach((t) -> {
            GeocodingService s = new GeocodingService();
            s.geocode(t, (grs, gs) -> {
                if (gs.equals(gs.OK)) {
                    LatLong location = (grs[0].getGeometry().getLocation());
                    MarkerOptions options = new MarkerOptions();
                    options.position(location);
                    options.animation(Animation.BOUNCE);
                    Marker m = new Marker(options);
                    InfoWindow window = new InfoWindow();
                    InfoWindowOptions infoOpt = new InfoWindowOptions();
                     infoOpt.content("<h2>" + t + "</h2> <img style='width:100px;height:100px' src='http://localhost/tahwissa/web/images/evenements/" + e.getImages().get(0).getChemin() + "'/>");
                    window.setOptions(infoOpt);
                    map.addUIEventHandler(m, UIEventType.click, (jso) -> {
                        window.open(map, m);
                    });
                    window.open(map,m);
                    map.addMarker(m);
                }
            });
        });

    }

}
