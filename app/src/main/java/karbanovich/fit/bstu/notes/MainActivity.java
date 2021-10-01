package karbanovich.fit.bstu.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_NOTES = 10;

    //View
    private DatePicker date;
    private EditText text;
    private Button btnAddNote;
    private Button btnEditNote;
    private Button btnDeleteNote;

    //Data
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding();
    }

    private void binding() {
        date = (DatePicker) findViewById(R.id.date);
        text = (EditText) findViewById(R.id.text);
        btnAddNote = (Button) findViewById(R.id.btnAddNote);
        btnEditNote = (Button) findViewById(R.id.btnEditNote);
        btnDeleteNote = (Button) findViewById(R.id.btnDeleteNote);

        setNoteByDate(date);

        date.setOnDateChangedListener((datePicker, i, i1, i2) -> {
            setNoteByDate(date);
        });
    }


    public void add(View view) {
        String text = this.text.getText().toString();
        Note note = new Note(text, Note.dateToString(date));

        ArrayList<Note> notes = JSONHelper.getNotesJSON(this);
        notes.add(note);

        JSONHelper.saveNotesJSON(this, notes);
        setNoteByDate(date);
    }

    public void edit(View view) {
        notes = JSONHelper.getNotesJSON(this);

        Note note = notes
                .stream()
                .filter(n -> n.getDate().equals(Note.dateToString(date)))
                .findFirst()
                .get();

       notes.get(notes.indexOf(note)).setText(text.getText().toString());
       JSONHelper.saveNotesJSON(this, notes);
    }

    public void delete(View view) {
        notes = JSONHelper.getNotesJSON(this);
        notes.removeIf(n -> n.getDate().equals(Note.dateToString(date)));
        JSONHelper.saveNotesJSON(this, notes);
        setNoteByDate(date);
    }

    private void setNoteByDate(DatePicker date) {
        notes = JSONHelper.getNotesJSON(this);
        Note note = null;

        if (!notes.isEmpty()) {
            note = notes.stream()
                    .filter(n -> n.getDate().equals(Note.dateToString(date)))
                    .findFirst()
                    .orElse(null);
        }

        if(note != null) {
            input(true);
            text.setText(note.getText());
            btnAddNote.setVisibility(View.GONE);
            btnEditNote.setVisibility(View.VISIBLE);
            btnDeleteNote.setVisibility(View.VISIBLE);
        } else {
            input(true);
            text.setText("");
            btnAddNote.setVisibility(View.VISIBLE);
            btnEditNote.setVisibility(View.INVISIBLE);
            btnDeleteNote.setVisibility(View.INVISIBLE);
        }

        if(note == null && notes.size() >= MAX_NOTES)
            input(false);
    }

    private void input(boolean bool) {
        text.setEnabled(bool);
        btnAddNote.setEnabled(bool);
        btnEditNote.setEnabled(bool);
        btnDeleteNote.setEnabled(bool);
    }
}