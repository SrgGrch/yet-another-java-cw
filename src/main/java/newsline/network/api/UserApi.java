package newsline.network.api;

import newsline.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/users")
    Call<User> authUser(
            @Query("login") String login,
            @Query("pass") String pass
    );

    @POST("/reg")
    Call<User> regUser(@Body Object object);


}
