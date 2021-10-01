package karbanovich.fit.bstu.notes;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONHelper {
    private static final String FILE_NAME = "notes.json";

    public static ArrayList<Note> getNotesJSON(Context context) {
        if(!isExist(context))
            return new ArrayList<>();

        Gson gson = new Gson();
        File file = new File(context.getFilesDir(), FILE_NAME);

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);

            DataItems contacts = gson.fromJson(isr, DataItems.class);

            fis.close();
            isr.close();
            return contacts.getNotes();
        } catch (Exception e) { }

        return new ArrayList<>();
    }

    public static void saveNotesJSON(Context context, ArrayList<Note> notes) {
        File file = new File(context.getFilesDir(), FILE_NAME);

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();

        dataItems.setNotes(notes);
        String jsonStr = gson.toJson(dataItems);

        try {
            FileOutputStream fos = new FileOutputStream(file, false);

            fos.write(jsonStr.getBytes());
            fos.close();
        } catch (Exception e) { }
    }

    private static boolean isExist(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        return file.exists();
    }

    private static class DataItems {
        private ArrayList<Note> notes;

        public ArrayList<Note> getNotes() {return notes;}
        public void setNotes(ArrayList<Note> notes) {this.notes = notes;}
    }

}
