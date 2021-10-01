package karbanovich.fit.bstu.notes;

import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class Note {

    private String text;
    private String date;    //DD.MM.YYYY

    public Note(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {return text;}
    public String getDate() {return date;}

    public void setText(String text) {this.text = text;}
    public void setDate(String date) {this.date = date;}

    public static String dateToString(@NonNull DatePicker date) {
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDayOfMonth();
        String dateStr = "";

        if(String.valueOf(day).length() == 1) {
            dateStr += "0" + day + ".";
        } else dateStr += day + ".";

        if(String.valueOf(month).length() == 1) {
            dateStr += "0" + month + ".";
        } else dateStr += month + ".";

        dateStr += year;

        return dateStr;
    }
}
