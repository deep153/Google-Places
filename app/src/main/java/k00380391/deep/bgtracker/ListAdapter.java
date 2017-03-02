package k00380391.deep.bgtracker;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Deep Gandhi on 29-04-2016.
 */
public class ListAdapter extends BaseAdapter {

    Context mContext;
    private GoogleApiResponse listItems = null;
    private LayoutInflater mInflater;
    Location location;
    double current_latitude,current_longitude;

    public ListAdapter(Context c,GoogleApiResponse listItems,Double latitude,Double longitude) {
        mContext = c;
        this.listItems = listItems;
        current_latitude = latitude;
        current_longitude = longitude;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItems.getSize();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        TextView rating, name, open_now, distance;
        ImageView image,dummay_image;


        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item, parent, false);
        }

        rating = (TextView) view.findViewById(R.id.rating);
        name = (TextView) view.findViewById(R.id.name);
        open_now = (TextView) view.findViewById(R.id.open_now);
        image = (ImageView) view.findViewById(R.id.image);
        dummay_image = (ImageView) view.findViewById(R.id.dummy_imageview);
        distance = (TextView) view.findViewById(R.id.distance);

        final String title = listItems.getResult(position).getName();

        name.setText(listItems.getResult(position).getName());
        if(listItems.getResult(position).getRating() != null) {
            rating.setText(listItems.getResult(position).getRating() + "");
        }else{
            rating.setText("0.0");
        }
        String mIconURI = listItems.getResult(position).getIcon();

        try {
            if (listItems.getResult(position).getOpeningHours().getOpenNow()) {
                open_now.setText("Open Now");
            } else {
                open_now.setText("Closed Now");
            }
        }catch (Exception e){
            open_now.setText("Not Available");
        }

        float[] dist = new float[1];
        double dest_lat = listItems.getResult(position).getGeometry().getLocation().getLat();
        double dest_long = listItems.getResult(position).getGeometry().getLocation().getLng();

        Location.distanceBetween(current_latitude, current_longitude, dest_lat, dest_long, dist);

        distance.setText((Math.round(dist[0] * 0.000621371192f * Math.pow(10, 2))/Math.pow(10, 2)) + " Miles Away");
        //Picasso.with(mContext).load(mIconURI).into(image);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(name.getText());
        TextDrawable dr = TextDrawable.builder()
                .buildRound(title.charAt(0) + "", color);
        dummay_image.setImageDrawable(dr);
        dummay_image.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);

        return view;
    }

    public GoogleApiResponse getListItems() {
        return listItems;
    }

    public void setListItems(GoogleApiResponse listItems) {
        this.listItems = null;
        this.listItems = listItems;
    }

    public String getPlaceId(int position) {
        return listItems.getResult(position).getPlaceId();
    }

    public String getName(int position) {
        return listItems.getResult(position).getName();
    }

    public double getVendorLatitude(int position) {
        return listItems.getResult(position).getGeometry().getLocation().getLat();
    }

    public double getVendorLongitude(int position) {
        return listItems.getResult(position).getGeometry().getLocation().getLng();
    }
}
