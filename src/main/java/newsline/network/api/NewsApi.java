package newsline.network.api;

import newsline.model.Article;
import newsline.model.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.ArrayList;

public interface NewsApi {

    @GET("/news")
    Call<ArrayList<Article>> getNewsList();

    @POST("/news/{id}")
    Call<Article> addComment(@Path("id") String id, @Body Comment comment);

}
