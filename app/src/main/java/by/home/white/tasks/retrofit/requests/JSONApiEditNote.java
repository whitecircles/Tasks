package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiEditNote {

    @GET("api/task/edit/{id}/{note}/{isChecked}/{priority}/{date}/{pendDate}/{userId}")
    Call<Void> editNote(@Path("id") int id, @Path("note") String note, @Path("isChecked") boolean isChecked, @Path("priority") String priority,
    @Path("date") String date, @Path("pendDate") String pendDate, @Path("userId") int userId);
}


