package k00380391.deep.bgtracker;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep Gandhi on 02-05-2016.
 */
public class GridAdapter extends BaseAdapter {

    private final List<String> categories = new ArrayList<>();
    private final List<Integer> icons = new ArrayList<>();

    private final LayoutInflater mInflater;
    Context context;

    public GridAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;

        categories.add("Restaurant");
        categories.add("Gym");
        categories.add("Bars");
        categories.add("Cafe");
        categories.add("Bank");
        categories.add("Hospital");

        icons.add(R.drawable.waiter_48);
        icons.add(R.drawable.gym_48);
        icons.add(R.drawable.bar_48);
        icons.add(R.drawable.cafe_48);
        icons.add(R.drawable.bank_48);
        icons.add(R.drawable.hospital_48);
    }


    @Override
    public int getCount() {
        return 6;
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
        View v = convertView;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, parent, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        int width= context.getResources().getDisplayMetrics().widthPixels;
        com.squareup.picasso.Picasso
                .with(context)
                .load(icons.get(position))
                .centerCrop().resize(width / 2,width/2)
                .into(picture);

        name.setText(categories.get(position));

        return v;
    }


}
