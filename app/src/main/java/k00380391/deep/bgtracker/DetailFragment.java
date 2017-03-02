package k00380391.deep.bgtracker;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by Deep Gandhi on 01-05-2016.
 */
public class DetailFragment extends Fragment {

    String vendor_name;
    LatLng vendorLocation;
    LatLng currentLocation;
    double vendor_latitude,source_latitude;
    double vendor_longitude,source_longitude;
    private GoogleMap map;
    public static View view;

    public DetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadGoogleMap() {

        GMapV2Direction md = new GMapV2Direction();

        vendorLocation =  new LatLng(vendor_latitude, vendor_longitude);
        currentLocation = new LatLng(source_latitude,source_longitude);

        map = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMap();

//        Document doc = md.getDocument(currentLocation, vendorLocation,
//                GMapV2Direction.MODE_DRIVING);
//
//        ArrayList<LatLng> directionPoint = md.getDirection(doc);
//        PolylineOptions rectLine = new PolylineOptions().width(3).color(
//                Color.RED);
//
//        for (int i = 0; i < directionPoint.size(); i++) {
//            rectLine.add(directionPoint.get(i));
//        }
//        Polyline polylin = map.addPolyline(rectLine);

        //add marker
        map.addMarker(new MarkerOptions().position(vendorLocation).title(vendor_name));

        //move camera : 15 = street view
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(vendorLocation)             // Sets the center of the map to Mountain View
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.detail_fragment, container, false);

        vendor_latitude = this.getArguments().getDouble("vendor_latitude");
        vendor_longitude = this.getArguments().getDouble("vendor_longitude");
        source_latitude = this.getArguments().getDouble("source_latitude");
        source_longitude = this.getArguments().getDouble("source_longitude");

        TextView vendor_name = (TextView) view.findViewById(R.id.vendor_name);
        vendor_name.setText(this.getArguments().getString("vendor_name"));

        TextView address = (TextView) view.findViewById(R.id.address);
        address.setText(this.getArguments().getString("address"));

        TextView phone_number = (TextView) view.findViewById(R.id.phone_number);
        phone_number.setText(this.getArguments().getString("phone_number"));

        TextView rating = (TextView) view.findViewById(R.id.rating);
        rating.setText(this.getArguments().getDouble("rating") + "");


        loadGoogleMap();
        return view;
    }

}
