package k00380391.deep.bgtracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Deep Gandhi on 29-04-2016.
 */
public class Results {

    String name;

    @SerializedName("place_id")
    String placeId;

    Double rating;
    String vicinity;
    String icon;
    public OpeningHours openingHours;
    public Geometry geometry;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
        return this.placeId;
    }

    public String getVicinity() {
        return this.vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public OpeningHours getOpeningHours() {
        return this.openingHours;
    }

    public Geometry getGeometry() {
        return this.geometry;
    }
}
