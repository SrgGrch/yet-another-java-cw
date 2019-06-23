package newsline.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static String BASE_URL = "http://localhost:3000/";

    public static String getBaseUrl(){
        return BASE_URL.substring(0, BASE_URL.length() - 1);
    }

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
