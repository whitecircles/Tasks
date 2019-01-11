package by.home.white.tasks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/api/user")
    Call<List<User>> getUsers();
}
