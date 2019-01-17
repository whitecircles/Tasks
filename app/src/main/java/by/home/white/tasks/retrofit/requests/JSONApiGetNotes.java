package by.home.white.tasks.retrofit.requests;

import java.util.List;

import by.home.white.tasks.User;
import by.home.white.tasks.entities.Note;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JSONApiGetNotes {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api/task/{id}")
    Call<List<Note>> getNotes(@Path("Id") int id);
}


