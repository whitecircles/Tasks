package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiInsertUser {

    @GET("api/user/add/{name}/{password}")
    Call<Void> insertUser(@Path("name") String name, @Path("password") String password);
}


