package com.bnrguide.learnmvvmroomlivedata.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bnrguide.learnmvvmroomlivedata.R;
import com.bnrguide.learnmvvmroomlivedata.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> noteList = new ArrayList<>();
    private OnItemClickListener itemClickListener;


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_note_view,viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        Note currentNote = noteList.get(position);
        noteHolder.mTv_Title.setText(currentNote.getTitle());
        noteHolder.mTv_Description.setText(currentNote.getDescription());
        noteHolder.mTV_Priority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public List<Note> getNoteList(){
        return noteList;
    }

    public void setNoteList(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return noteList.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView mTv_Title;
        TextView mTv_Description;
        TextView mTV_Priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            mTv_Title = itemView.findViewById(R.id.note_title);
            mTv_Description = itemView.findViewById(R.id.note_description);
            mTV_Priority = itemView.findViewById(R.id.note_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(itemClickListener != null && position != RecyclerView.NO_POSITION){
                        itemClickListener.onItemClick(noteList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }
}
