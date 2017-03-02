package k00380391.deep.bgtracker;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Deep Gandhi on 29-04-2016.
 */
public interface MyApi{

    /*LOGIN*/

    @GET("place/nearbysearch/json") //your login function in your api
    Call<GoogleApiResponse> getNearByPlaces(@Query("location") String location,@Query("radius") String radius,@Query("type") String type,@Query("key") String key);

    @GET("place/details/json") //your login function in your api
    Call<VendorApiResponse> getPlaceDetail(@Query("placeid") String placeid,@Query("key") String key);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
