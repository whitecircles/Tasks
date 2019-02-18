package by.home.white.tasks.retrofit;


import by.home.white.tasks.retrofit.requests.JSONApiDeleteNote;
import by.home.white.tasks.retrofit.requests.JSONApiEditNote;
import by.home.white.tasks.retrofit.requests.JSONApiGetNotes;
import by.home.white.tasks.retrofit.requests.JSONApiGetUsers;


import by.home.white.tasks.retrofit.requests.JSONApiInsertNote;
import by.home.white.tasks.retrofit.requests.JSONApiInsertUser;
import by.home.white.tasks.retrofit.requests.photo.JSONApiInsertPhoto;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://mveronicatest2.azurewebsites.net/";
    private Retrofit mRetrofit;

    private NetworkService() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONApiGetUsers getJSONApiGetUsers() {
        return mRetrofit.create(JSONApiGetUsers.class);
    }

    public JSONApiGetNotes getJSONApiGetNotes() {
        return mRetrofit.create(JSONApiGetNotes.class);
    }

    public JSONApiInsertNote getJSONApiInsertNote() { return mRetrofit.create(JSONApiInsertNote.class); }

    public JSONApiEditNote getJSONApiEditNote() {
        return mRetrofit.create(JSONApiEditNote.class);
    }

    public JSONApiDeleteNote getJSONApiDeleteNote() { return mRetrofit.create(JSONApiDeleteNote.class); }

    public JSONApiInsertUser getJSONApiInsertUser() { return mRetrofit.create(JSONApiInsertUser.class); }


}

