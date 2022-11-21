package app.ui.utils;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class Punkt {

    private String title, description;

    private double latitude, longitude;

    public Punkt(String title, String description, double latitude, double longitude){
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPoint getGeoPoint(){
        return new GeoPoint(latitude, longitude);
    }

    public OverlayItem getOverlayItem(){
        return new OverlayItem(title, description, this.getGeoPoint());
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
