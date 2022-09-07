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
            requestPermissionsIfNecessary(new String[] {
                    // if you need to show the current location, uncomment the line below
                    //Berechtigungen für ungefähren und genauen Standort (Beide Notwendig)
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    // WRITE_EXTERNAL_STORAGE is required in order to show the map
                    Manifest.permission.WRITE_EXTERNAL_STORAGE


            });
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




            //Wenn auf den folgen button gecklickt wird, wird dem aktuellen standort erneut gefolgt
            folgen= this.instance.findViewById(R.id.folgen);
            folgen.setOnClickListener(event -> {
                //mehtode um dem aktuellen standort zu folgen
                mLocationOverlay.enableFollowLocation();
                map.getController().setZoom(map.getZoomLevelDouble());
            });


        });

        //Liste aller punkte
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        //Punkt Goethe Gymnasium
        //aTitel = Text oben; aSnippet = Text unten; Geo point = Position in Koordinaten (Zuerst Breite dann Länge)
        items.add(new OverlayItem("Goethe Gymnasium", "Goethestrasse 1", new GeoPoint(49.01809d,12.07463d))); // Lat/Lon decimal degrees

        Context context = this.instance.getApplicationContext();


        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        new QuizScreen();
                        return true;
                    }
                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();
                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);


        //punkt am Domplatz
        items.add(new OverlayItem("Dom Sankt Peter", "Domplatz 1", new GeoPoint(49.019437212010224d, 12.098297335534763d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);


        //punkt am Bahnhof/Arcaden
        items.add(new OverlayItem("Hauptbahnhof/Arcaden", "Ostengasse 3", new GeoPoint(49.01173392596627d, 12.099671342172106d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Stadpark
        items.add(new OverlayItem("Stadtpark", "", new GeoPoint(49.01919899373d, 12.081176517791059d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);


        //punkt an der Clermont-Ferrand
        items.add(new OverlayItem("Clermont Ferrand", "Clermont-Ferrand Alee", new GeoPoint(49.023317480956344d, 12.067827635582113d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);


        //punkt der Steinernen Brücke
        items.add(new OverlayItem("Steinerne Brücke", "", new GeoPoint(49.02265146306108d, 12.0972515d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt der Stadtbibliothek
        items.add(new OverlayItem("Stadtbibliothek", "Haidplatz 8", new GeoPoint(49.02002695633216d, 12.093111864417882d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Neupfahrplatz
        items.add(new OverlayItem("Neupfahrplatz", "", new GeoPoint(49.01840533614868d, 12.096341217848272d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Haus der Bayerischen Geschichte
        items.add(new OverlayItem("HdbG", "", new GeoPoint(49.02034752064426d, 12.10229083558212d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Historischen Museum
        items.add(new OverlayItem("Historisches Museum", "Dachauplatz", new GeoPoint(49.01779086536834d, 12.102089292721177d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Haus der Bayerischen Geschichte
        items.add(new OverlayItem("HdbG", "", new GeoPoint(49.02034752064426d, 12.10229083558212d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Schloss Thurn und Taxis
        items.add(new OverlayItem("Thurn und Taxis", "Emeransplatz", new GeoPoint(49.014514665635d, 12.097195163342073d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);


        //punkt an der Uni
        items.add(new OverlayItem("Universität", "Universitätstrasse 1", new GeoPoint(48.99674818635844d, 12.095792164417878d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Rennplatz
        items.add(new OverlayItem("Rennplatz", "Franz von Taxis Ring", new GeoPoint(49.013227601436874d, 12.051839413066663d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt am Weinweg
        items.add(new OverlayItem("Weinweg", "", new GeoPoint(49.02702246507209d, 12.068116722101989d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

                        return true;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        //punkt an der Dreifaltigkeitskirche
        items.add(new OverlayItem("Dreifalrigkeitskirche", "", new GeoPoint(49.030638899241794d, 12.096726984656957d))); // Lat/Lon decimal degrees

        context = this.instance.getApplicationContext();


        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this.instance.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    //Bei kurzem drücken
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    @Override
                    //Bei langem Drücken
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        new QuizScreen();

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
