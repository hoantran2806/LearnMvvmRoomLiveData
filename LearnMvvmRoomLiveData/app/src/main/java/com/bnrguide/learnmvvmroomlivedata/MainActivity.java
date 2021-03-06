package com.bnrguide.learnmvvmroomlivedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bnrguide.learnmvvmroomlivedata.adapter.NoteAdapter;
import com.bnrguide.learnmvvmroomlivedata.model.Note;
import com.bnrguide.learnmvvmroomlivedata.view.AddEditNoteActivity;
import com.bnrguide.learnmvvmroomlivedata.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private RecyclerView mRecyclerView;
    private Button mBtn_Add_Note;

    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();


    }

    private void setupView() {
        mRecyclerView = findViewById(R.id.note_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter noteAdapter = new NoteAdapter();
        mRecyclerView.setAdapter(noteAdapter);
        mBtn_Add_Note = findViewById(R.id.button_add);

        mBtn_Add_Note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
//                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.setNoteList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
                mNoteViewModel.deleteNote(noteAdapter.getNoteAt(position));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = dataIntent.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = dataIntent.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = dataIntent.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0);
            Note note = new Note(title, description, priority);
            mNoteViewModel.insertNote(note);
            Toast.makeText(this, "new Note Added", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = dataIntent.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "This Note can not be edited", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = dataIntent.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = dataIntent.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = dataIntent.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0);
            Note note = new Note(title, description, priority);
            note.setId(id);
            mNoteViewModel.updateNote(note);
        } else {
            Toast.makeText(this, "Note note Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                mNoteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
