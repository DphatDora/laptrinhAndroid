package com.example.baitap_sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<NotesModel> noteList;

    public NotesAdapter(MainActivity context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }

    //khai báo view holder
    private class ViewHolder{
        TextView textViewNote;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder.textViewNote = convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewEdit = convertView.findViewById(R.id.imageViewEdit);
            viewHolder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());

        // click update button
        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogUpdateNotes(notes.getNameNote(), notes.getIdNote());
            }
        });

        //click delete button
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogDeleteNotes(notes.getNameNote(), notes.getIdNote());
            }
        });

        return convertView;
    }
}
