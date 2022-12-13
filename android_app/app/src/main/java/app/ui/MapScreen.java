package app.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Arrays;

import app.MainActivity;
import app.ui.utils.Punkt;
import de.uwuwhatsthis.quizApp.ui.loginScreen.R;

public class MapScreen extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;

    private MainActivity instance;
    private Button folgen, settings;
    public static int Zoom = 18;

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

            settings = this.instance.findViewById(R.id.button7);
            folgen = this.instance.findViewById(R.id.folgen);

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
            map.getController().setZoom(Zoom);



            settings.setOnClickListener(event -> {
                new SettingsScreen();
            });

            folgen.setOnClickListener(event -> {
                //mehtode um dem aktuellen standort zu folgen
                mLocationOverlay.enableFollowLocation();
                map.getController().setZoom(map.getZoomLevelDouble());
            });


        });

        //Liste aller punkte
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        Context context = this.instance.getApplicationContext();

        Punkt[] punkte = new Punkt[]{
                new Punkt("Goethe Gymnasium", "Goethestrasse 1", 49.01809d,12.07463d),
                new Punkt("Dom Sankt Peter", "Domplatz 1", 49.019437212010224d, 12.098297335534763d),
                new Punkt("Hauptbahnhof/Arcaden", "Ostengasse 3", 49.01173392596627d, 12.099671342172106d),
                new Punkt("Stadtpark", "", 49.01919899373d, 12.081176517791059d),
                new Punkt("Clermont Ferrand", "Clermont-Ferrand Allee", 49.023317480956344d, 12.067827635582113d),
                new Punkt("Steinerne Brücke", "", 49.02265146306108d, 12.0972515d),
                new Punkt("Stadtbibliothek", "Haidplatz 8", 49.02002695633216d, 12.093111864417882d),
                new Punkt("Neupfahrplatz", "", 49.01840533614868d, 12.096341217848272d),
                new Punkt("HdbG", "", 49.02034752064426d, 12.10229083558212d), // Haus der Bayerischen Geschichte
                new Punkt("Historisches Museum", "Dachauplatz", 49.01779086536834d, 12.102089292721177d),
                new Punkt("HdbG", "", 49.02034752064426d, 12.10229083558212d), // 2. mal Haus der Bayerischen Geschichte ??
                new Punkt("Thurn und Taxis", "Emeransplatz", 49.014514665635d, 12.097195163342073d),
                new Punkt("Universität", "Universitätstrasse 1", 48.99674818635844d, 12.095792164417878d),
                new Punkt("Rennplatz", "Franz von Taxis Ring", 49.013227601436874d, 12.051839413066663d),
                new Punkt("Weinweg", "", 49.02702246507209d, 12.068116722101989d),
                new Punkt("Dreifaltigkeitskirche", "", 49.030638899241794d, 12.096726984656957d)
        };

        for (Punkt punkt: punkte){
            items.add(punkt.getOverlayItem());
        }


        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                        Punkt punkt = null;

                        for (Punkt p1: punkte){
                            if (p1.getOverlayItem() == item){
                                punkt = p1;
                            }
                        }

                        Location emptyLocation = new Location("");
                        emptyLocation.setLatitude(punkt.getLatitude());
                        emptyLocation.setLongitude(punkt.getLongitude());

                        float radius = 50.0f;
                        float distance = MainActivity.getLocation().distanceTo(emptyLocation);

                        if (distance > radius){
                            Toast.makeText(instance.getApplicationContext(), "Du bist zu weit vom punkt weg!", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        new QuizScreen();
                        return true;
                    }
                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return this.onItemSingleTapUp(index, item);
                    }
                });
        mOverlay.setFocusItemsOnTap(true);


        Marker startmarker = new Marker(map);
        startmarker.setPosition(new GeoPoint(49.030638899241794d, 12.096726984656957d));
        startmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        startmarker.setTitle("rofl");
        // startmarker.setIcon(this.instance.getDrawable(R.drawable.ic_baseline_error_24));
        startmarker.setImage(this.instance.getDrawable(R.drawable.ic_baseline_error_24));
        map.getOverlays().add(startmarker);

        map.getOverlays().add(mOverlay);
    }

    public static void ZoomErhöhen() {
        Zoom = Zoom +1 ;
    }
    public static void ZoomVeringern() {
        Zoom = Zoom -1 ;
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

    public static void requestPermissionsIfNecessary(String[] permissions, MainActivity instance) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(instance, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    instance,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);

        }

    }

    public void ZoomPlus (){
        if(Zoom<=50){

            Zoom = Zoom++;}
    }

    public void ZoomMinus (){
        if (Zoom >= 1) {
            Zoom--;
        }
        }}


