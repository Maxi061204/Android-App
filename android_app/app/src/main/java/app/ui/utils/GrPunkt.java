package app.ui.utils;

import androidx.appcompat.content.res.AppCompatResources;

import org.osmdroid.views.overlay.OverlayItem;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class GrPunkt extends Punkt {

    public GrPunkt(String title, double latitude, double longitude) {
        super(title, "", latitude, longitude);
    }

    @Override
    public OverlayItem getOverlayItem(){
        this.overlayItem.setMarker(AppCompatResources.getDrawable(MainActivity.getInstance(), R.drawable.ic_baseline_error_24));
        return this.overlayItem;
    }
}
