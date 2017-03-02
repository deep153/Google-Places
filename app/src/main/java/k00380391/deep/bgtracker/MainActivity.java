package k00380391.deep.bgtracker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    private LatLng currentLocation;
    Location location;
    private double longitude,latitude;
    LocationService ls;

    private static final String[] LOCATION_PERMS={
        Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final int INITIAL_REQUEST = 1337;

    List<Results> resultValues = new ArrayList<Results>();

    ListView listView;
    ListAdapter adapter;
    Context context;
    GoogleApiResponse apiResponse;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //remove title
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);

        List<String> categories = new ArrayList<String>();
        categories.add("Restaurant");
        categories.add("Gyms");
        categories.add("Bars");
        categories.add("Cafe");
        categories.add("Bank");
        categories.add("Hospital");

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetchData(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        context = this;

        listView = (ListView) findViewById(R.id.listview);

        ls = new LocationService(this);

        if (!canAccessLocation() && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(LOCATION_PERMS, INITIAL_REQUEST);
        }else{
            loadGoogleMap();
            fetchData(0);
        }
    }


    public void loadGoogleMap() {

        longitude = ls.getLongitude();
        latitude = ls.getLatitude();

//        Log.e("Long", longitude+"");
//        Log.e("Lat", latitude+"");
//
//        currentLocation =  new LatLng(latitude, longitude);
//
//        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//        //add marker
//        map.addMarker(new MarkerOptions().position(currentLocation).title("Marker in Sydney"));
//
//        //move camera : 15 = street view
//        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));
//        //map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(currentLocation)             // Sets the center of the map to Mountain View
//                .zoom(18)                   // Sets the zoom
//                .bearing(90)                // Sets the orientation of the camera to east
//                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void fetchData(int type) {

        MyApi googleApi = MyApi.retrofit.create(MyApi.class);

        String location = latitude + "," + longitude;

        Call<GoogleApiResponse> call;

        String place = "restaurant";
        switch (type) {
            //Restaurant
            case 0 : {
                place = "restaurant";
                break;
            }

            //Gyms
            case 1 : {
                place = "gym";
                break;
            }

            //Bars
            case 2 : {
                place = "bar";
                break;
            }

            //Cafe
            case 3 : {
                place = "cafe";
                break;
            }

            case 4 : {
                place = "bank";
                break;
            }

            //Hospital
            case 5 : {
                place = "hospital";
                break;
            }
        }

        call = googleApi.getNearByPlaces(location,"1000",place,Constants.API_KEY);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Fetching data", false, true);
        call.enqueue(new Callback<GoogleApiResponse>() {

            @Override
            public void onResponse(Call<GoogleApiResponse> call, Response<GoogleApiResponse> response) {

                //apiResponse = response.body();

                if(counter == 0) {
                    loadListView(response.body());
                }else{
                    refreshLisview(response.body());
                }

                counter++;
                progressDialog.dismiss();
                //Log.e("Response",response.body().getResults().toString());
            }

            @Override
            public void onFailure(Call<GoogleApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        if ( Build.VERSION.SDK_INT >= 23) {
            return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case INITIAL_REQUEST: {
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    loadGoogleMap();
                    fetchData(0);
                    // permission was granted, yay! Do the task you need to do.

                }else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void loadListView(GoogleApiResponse listItems) {

        adapter = new ListAdapter(context,listItems,latitude,longitude);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String place_id = adapter.getPlaceId(position);

                String name = adapter.getName(position);

                Intent i = new Intent(context,VendorActivity.class);
                i.putExtra("place_id", place_id);
                i.putExtra("name",name);
                i.putExtra("source_latitude",latitude);
                i.putExtra("source_longitude",longitude);
                i.putExtra("vendor_latitude",adapter.getVendorLatitude(position));
                i.putExtra("vendor_longitude", adapter.getVendorLongitude(position));
                startActivity(i);
            }
        });
    }

    private void refreshLisview(GoogleApiResponse listItems){

        adapter.setListItems(listItems);
        adapter.notifyDataSetChanged();
    }
}
