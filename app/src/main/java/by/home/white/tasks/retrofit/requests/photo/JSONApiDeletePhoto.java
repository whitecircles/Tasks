package by.home.white.tasks.retrofit.requests.photo;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface JSONApiDeletePhoto {

    @GET("api/photo/delete/{id}/")
    Call<Void> deletePhoto(@Path("id") int id);
}


