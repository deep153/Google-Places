package k00380391.deep.bgtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Deep Gandhi on 01-05-2016.
 */
public class SpinnerAdapter extends ArrayAdapter{

    private Context context;
    LayoutInflater mInflater;
    List<String> item_list;

    public SpinnerAdapter(Context context,int textViewResourceId,List<String> item_list) {

        super(context,textViewResourceId,item_list);
        this.context=context;
        this.item_list = item_list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        View view = mInflater.inflate(R.layout.spinner_item, parent,
                false);
        TextView spinner_item = (TextView) view.findViewById(R.id.spinner_text);
        spinner_item.setText(item_list.get(position));

        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {


        View row = mInflater.inflate(R.layout.spinner_item, parent,
                false);
        TextView v = (TextView) row.findViewById(R.id.spinner_text);

        v.setText(item_list.get(position));
        return row;
    }
}
