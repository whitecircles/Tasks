package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiEditUser {

    @GET("api/user/edit/{id}/{name}/{password}")
    Call<List<User>> editUser(@Path("Id") int id, @Path("name") String name, @Path("password") String password);
}


