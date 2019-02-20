package by.home.white.tasks;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Passwrd")
    @Expose
    private String Passwrd;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPasswrd() {
        return Passwrd;
    }

    public void setPasswrd(String password) {
        Passwrd = password;
    }
}

