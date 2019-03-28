package com.bnrguide.learnmvvmroomlivedata.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.bnrguide.learnmvvmroomlivedata.R;
import com.bnrguide.learnmvvmroomlivedata.model.Note;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";

    public static final String EXTRA_DESCRIPTION = "extra_description";

    public static final String EXTRA_PRIORITY = "extra_priority";

    public static final String EXTRA_ID = "extra_id";

    private EditText mEdt_Title;
    private EditText mEdt_Description;
    private NumberPicker mNp_Priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        setUpView();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(R.string.edit_note);
            mEdt_Title.setText(intent.getStringExtra(EXTRA_TITLE));
            mEdt_Description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            mNp_Priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 0));
        } else {
            setTitle("Add Note");
        }
    }

    private void setUpView() {
        mEdt_Title = findViewById(R.id.title_edittext);
        mEdt_Description = findViewById(R.id.description_edittext);
        mNp_Priority = findViewById(R.id.priority_picker);
        mNp_Priority.setMinValue(1);
        mNp_Priority.setMaxValue(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = mEdt_Title.getText().toString();
        String description = mEdt_Description.getText().toString();
        int priority = mNp_Priority.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Insert Title or Description", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_DESCRIPTION, description);
            intent.putExtra(EXTRA_PRIORITY, priority);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                intent.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
