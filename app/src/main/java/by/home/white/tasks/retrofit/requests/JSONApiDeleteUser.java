package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiDeleteUser {
    @GET("api/user/delete/{id}/")
    Call<List<User>> deleteUser(@Path("id") int id);
}


