package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface JSONApiGetUsers {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api/user")
    Call<List<User>> getUsers();
}


