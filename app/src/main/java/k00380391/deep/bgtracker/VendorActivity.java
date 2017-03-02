package k00380391.deep.bgtracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.http.StatusLine;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    double vendor_latitude,source_latitude;
    double vendor_longitude,source_longitude;
    String vendor_name;
    String place_id;

    public ArrayList<Reviews> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        Intent i = getIntent();
        vendor_name = i.getStringExtra("name");
        place_id = i.getStringExtra("place_id");
        source_latitude = i.getDoubleExtra("source_latitude", 0.0);
        source_longitude = i.getDoubleExtra("source_longitude",0.0);
        vendor_latitude = i.getDoubleExtra("vendor_latitude", 0.0);
        vendor_longitude = i.getDoubleExtra("vendor_longitude",0.0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(vendor_name);
        }

        fetchData();

        viewPager = (ViewPager) findViewById(R.id.viewpager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    public void fetchData() {

        MyApi googleApi = MyApi.retrofit.create(MyApi.class);

        Call<VendorApiResponse> call = googleApi.getPlaceDetail(place_id, Constants.API_KEY);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Fetching data", false, true);
        call.enqueue(new Callback<VendorApiResponse>() {

            @Override
            public void onResponse(Call<VendorApiResponse> call, Response<VendorApiResponse> response) {


                //apiResponse = response.body();

                setupViewPager(viewPager,response.body());
                progressDialog.dismiss();
                //Log.e("Response",response.body().getResults().toString());
            }

            @Override
            public void onFailure(Call<VendorApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager,VendorApiResponse response) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("vendor_name", vendor_name);
        bundle.putDouble("vendor_latitude", vendor_latitude);
        bundle.putDouble("vendor_longitude", vendor_longitude);
        bundle.putDouble("source_latitude", source_latitude);
        bundle.putDouble("source_longitude", source_longitude);
        bundle.putString("address", response.getResult().getFormattedAddress());
        bundle.putString("phone_number", response.getResult().getFormattedPhoneNumber());
        bundle.putDouble("rating", response.getResult().getRating());
        bundle.putString("icon",response.getResult().getIcon());

        //add details fragment to viewpager
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        adapter.addFragment(detailFragment, "Details");

        //add reviewFragment to viewpager
        Bundle reviewBundle = new Bundle();


        ReviewsFragment reviewsFragment = new ReviewsFragment();
        adapter.addFragment(reviewsFragment, "Reviews");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
