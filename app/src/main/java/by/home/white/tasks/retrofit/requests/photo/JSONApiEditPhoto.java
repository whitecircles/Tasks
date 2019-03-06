package by.home.white.tasks.retrofit.requests.photo;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface JSONApiEditPhoto {

    @GET("api/user/edit/{taskId}")
    Call<Void> editPhoto(@Path("taskId") int id, @Body Bitmap photo);
}


