package by.home.white.tasks.retrofit.requests.photo;

import android.graphics.Bitmap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface JSONApiGetPhoto {

    @GET("api/photo/{taskId}")
    Call<Bitmap> getPhoto(@Path("taskId") int taskId);
}


