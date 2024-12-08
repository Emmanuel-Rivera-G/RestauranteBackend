package utp.edu.pe.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utp.edu.pe.utils.gson.adapters.LocalDateAdapter;

import java.time.LocalDate;

public class GsonFactory {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
}
