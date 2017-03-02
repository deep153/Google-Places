package k00380391.deep.bgtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Deep Gandhi on 01-05-2016.
 */
public class ReviewsFragment extends Fragment {

    public View view;
    ListView reviewListView;
    public ReviewsFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.review_fragment, container, false);
        //reviewListView = (ListView) view.findViewById(R.id.review_listview);

        return view;
    }

}
