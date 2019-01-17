package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiInsertNote {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api/task/add/{note}/{isChecked}/{priority}/{date}/{pendDate}/{userId}")
    Call<Void> insertNote(@Path("note") String note, @Path("isChecked") boolean isChecked, @Path("priority") String priority,
    @Path("date") String date, @Path("pendDate") String pendDate, @Path("userId") int userId);
}


