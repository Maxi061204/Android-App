package app.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Arrays;

import app.MainActivity;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class MapScreen extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;

    private MainActivity instance;
    Button folgen;
    public MapScreen(){
        this.instance = MainActivity.getInstance();

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done

        this.instance.runOnUiThread(() -> {
            Context ctx = this.instance.getApplicationContext();
            Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
            //setting this before the layout is inflated is a good idea
            //it 'should' ensure that the map has a writable location for the map cache, even without permissions
            //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
            //see also StorageUtils
            //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
            //tile servers will get you banned based on this string

            //inflate and create the map
            this.instance.setContentView(R.layout.map_layout);

            map = (MapView) this.instance.findViewById(R.id.map);
            map.setTileSource(TileSourceFactory.MAPNIK);

            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);

            MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx), map);
            //zeigt eigenen Standpunkt and
            mLocationOverlay.enableMyLocation();

            //Folgt dem eigenen Standpunkt solange Karte noch nicht manuell verschoben wurde
            mLocationOverlay.enableFollowLocation();

            map.getOverlays().add(mLocationOverlay);

            //setzt Zoom auf bestimmten Wert
            map.getController().setZoom(18L);



            requestPermissionsIfNecessary(new String[] {
                    // if you need to show the current location, uncomment the line below
                    //Berechtigungen für ungefähren und genauen Standort (Beide Notwendig)
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    // WRITE_EXTERNAL_STORAGE is required in order to show the map
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            });
            //Wenn auf den folgen button gecklickt wird, wird dem aktuellen standort erneut gefolgt
            folgen= this.instance.findViewById(R.id.folgen);
            folgen.setOnClickListener(event -> {
                //mehtode um dem aktuellen standort zu folgen
                mLocationOverlay.enableFollowLocation();
                map.getController().setZoom(map.getZoomLevelDouble());
            });


        });

        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        //aTitel = Text oben; aSnippet = Text unten; Geo point = Position in Koordinaten (Zuerst Breite dann Länge)
        items.add(new OverlayItem("Titel", "Beschreibung", new GeoPoint(49.01809d,12.07463d))); // Lat/Lon decimal degrees

        Context context = this.instance.getApplicationContext();

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Toast.makeText(context, "Button tap", Toast.LENGTH_SHORT).show();

                        return true;
                    }
                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        //new QuizScreen();  Bessere Lösung einfügen. Noch nicht getestet.
                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ArrayList<String> permissionsToRequest = new ArrayList<>(Arrays.asList(permissions).subList(0, grantResults.length));

        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.instance, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this.instance,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);

        }

    }


}
