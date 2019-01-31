package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import by.home.white.tasks.entities.Note;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiInsertNote {

    @GET("api/task/add/{note}/{isChecked}/{priority}/{date}/{pendDate}/{userId}")
    Call<Integer> insertNote(@Path("note") String note, @Path("isChecked") boolean isChecked, @Path("priority") String priority, @Path("date") String date,
                          @Path("pendDate") String pendDate, @Path("userId") int userId);
}


