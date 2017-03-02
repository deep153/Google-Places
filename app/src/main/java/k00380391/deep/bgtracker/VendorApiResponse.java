package k00380391.deep.bgtracker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deep Gandhi on 29-04-2016.
 */
public class VendorApiResponse {

    public Result result;
    public Boolean status;

    public Result getResult() {
        return result;
    }

    public Boolean getStatus() {
        return status;
    }
}
