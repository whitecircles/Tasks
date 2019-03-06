package by.home.white.tasks.retrofit.requests.photo;

import android.graphics.Bitmap;
import android.net.Uri;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface JSONApiInsertPhoto {

    @POST("api/photo/add/{taskId}")
    Call<Integer> insertPhoto(@Path("taskId") int taskId, @Query("photo") String uri);
}


