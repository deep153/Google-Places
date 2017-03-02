package k00380391.deep.bgtracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep Gandhi on 29-04-2016.
 */
public class GoogleApiResponse {

    public List<Object> htmlAttributions = new ArrayList<Object>();
    public ArrayList<Results> results;
    private String status;

    public GoogleApiResponse() {
        results = new ArrayList<Results>();
    }

    public int getSize() {
        return results.size();
    }

    public String getStatus() {
        return status;
    }

    public Results getResult(int index) {
        return results.get(index);
    }

    public void setStatus(String status){
        this.status = status;
    }
}
