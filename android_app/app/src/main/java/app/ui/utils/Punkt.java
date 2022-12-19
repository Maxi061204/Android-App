package app.ui.utils;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class Punkt {

    protected String title, description;

    protected double latitude, longitude;

    protected OverlayItem overlayItem;

    public Punkt(String title, String description, double latitude, double longitude){
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;

        this.overlayItem = new OverlayItem(title, description, this.getGeoPoint());
    }

    public GeoPoint getGeoPoint(){
        return new GeoPoint(latitude, longitude);
    }

    public OverlayItem getOverlayItem(){
        return this.overlayItem;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
