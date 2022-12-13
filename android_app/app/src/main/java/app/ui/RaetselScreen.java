package app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import app.MainActivity;
import app.api.quiz.SpecificQuiz;
import app.api.quiz.SubSpecificQuiz;
import app.ui.utils.Utils;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class RaetselScreen extends AppCompatActivity implements MapEventsReceiver {
    private final MainActivity instance;

    private final String ortname;

    private MapView map;

    private Context ctx;

    private Button folgenButton, hinweisButton;

    private SubSpecificQuiz currentSubQuiz;
    private SpecificQuiz quiz;
    private int index;

    public RaetselScreen(String ortname){
        this.instance = MainActivity.getInstance();

        this.ctx = this.instance.getApplicationContext();

        this.ortname = ortname;

        this.index = 0;

        this.instance.runOnUiThread(() -> {
            Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(instance.getApplicationContext()));

            this.instance.setContentView(R.layout.raetsel_screen);

            folgenButton = this.instance.findViewById(R.id.folgen_raetsel);
            hinweisButton = this.instance.findViewById(R.id.ratsel_hinweis);

            map = (MapView) this.instance.findViewById(R.id.map);
            map.setTileSource(TileSourceFactory.MAPNIK);

            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);

            MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this.instance.getApplicationContext()), map);
            //zeigt eigenen Standpunkt and
            mLocationOverlay.enableMyLocation();

            //Folgt dem eigenen Standpunkt solange Karte noch nicht manuell verschoben wurde
            mLocationOverlay.enableFollowLocation();

            map.getOverlays().add(mLocationOverlay);

            map.getController().setZoom(18L);

            folgenButton.setOnClickListener((event) -> {
                mLocationOverlay.enableFollowLocation();
                map.getController().setZoom(map.getZoomLevelDouble());
            });

            Overlay touchOverlay = new Overlay(this) {
                @Override
                public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {

                    Projection proj = mapView.getProjection();
                    GeoPoint loc = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());

                    onTouch(loc.getLongitude(), loc.getLatitude());
                    return true;
                }
            };

            map.getOverlays().add(touchOverlay);
        });

        run();
    }

    private void run(){
        SpecificQuiz.vonPlatzName(this.ortname, quiz -> {
            this.quiz = quiz;
            updateSubQuiz(quiz.getSubQuizzes()[index]);

        });
    }

    private void updateSubQuiz(SubSpecificQuiz quiz){
        index += 1;
        this.currentSubQuiz = quiz;
        this.instance.runOnUiThread( () -> {
            Utils.showInfoMessage(instance, "Neuer Hinweis gefunden!", quiz.getDescription());

            this.hinweisButton.setOnClickListener((event) -> {
                Utils.showInfoMessage(instance, "Hinweis zum Ort \"" + this.ortname + "\"", quiz.getDescription());
            });
        });
    }

    private void onTouch(double longitude, double latitude){
        System.out.println("Touched: Long: " + longitude + " | Lat: " + latitude);
        System.out.println("Stored:  Long: " + this.currentSubQuiz.getLongitude() + " | Lat: " + this.currentSubQuiz.getLatitude());

        GeoPoint touched = new GeoPoint(latitude, longitude);
        GeoPoint stored = new GeoPoint(currentSubQuiz.getLatitude(), currentSubQuiz.getLongitude());

        double distance = touched.distanceToAsDouble(stored);

        System.out.println("Distance: " + distance);

        if (distance > 5) return;

        try{
            SubSpecificQuiz subQuizz = quiz.getSubQuizzes()[index];
            // instance.runOnUiThread(() -> Utils.showInfoMessage(instance, "Geschafft!", "Der Datenträger wurde gesichert!"));

            updateSubQuiz(subQuizz);
            return;
        } catch (ArrayIndexOutOfBoundsException e){
            this.instance.runOnUiThread(() -> {
                Utils.showInfoMessage(instance, "Geschafft!", "Alle Punkte zum Ort \"" + quiz.getLocationName() + "\" gefunden!");
                new MapScreen();
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        System.out.println("Pressed short at " + p.getLongitude() + " | " + p.getLatitude());
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        System.out.println("Pressed long at " + p.getLongitude() + " | " + p.getLatitude());
        return true;
    }
}
