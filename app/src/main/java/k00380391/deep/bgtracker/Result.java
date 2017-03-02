package k00380391.deep.bgtracker;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Deep Gandhi on 29-04-2016.
 */

public class Result {

    @SerializedName("formatted_address")
    public String formattedAddress;

    @SerializedName("formatted_phone_number")
    public String formattedPhoneNumber;

    @SerializedName("place_id")
    public String placeId;

    public Double rating;
    String vicinity;
    public String icon;
    public ArrayList<Reviews> reviews;


    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return this.formattedPhoneNumber;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
        return this.placeId;
    }

    public String getVicinity() {
        return this.vicinity;
    }

    public String getIcon() {
        return this.icon;
    }

    public Double getRating() {
        Log.e("aa", rating + "");
        return this.rating;
    }

    public Reviews getReviews(int position) {
        return this.reviews.get(position);
    }
}
