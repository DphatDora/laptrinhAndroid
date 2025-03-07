package com.example.baitap_sqlite;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;
    Button btnAddNote;

    private void elementMapping(){
        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_note, arrayList);
        listView.setAdapter(adapter);
        btnAddNote = findViewById(R.id.btnAddNotes);
    }

    private void createDatabaseSQLite(){
        //insert data to table
        databaseHandler.QueryData("insert into Notes values(null,'ví dụ sqlite 3' )");
        databaseHandler.QueryData("insert into Notes values(null,'ví dụ sqlite 4' )");
    }

    private void InitDatabaseSQLite(){
        //khởi tạo db
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        //tạo table notes
        databaseHandler.QueryData("Create Table if not Exists Notes(Id integer Primary key autoincrement, NameNotes varchar(200))");
    }

    private void databaseSQLite(){

        arrayList.clear();
        //get data
        Cursor cursor = databaseHandler.getData("Select * From Notes");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            arrayList.add(new NotesModel(id, name));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //ánh xạ các đối tượng trên giao diện
        elementMapping();
        //khởi tạo database
        InitDatabaseSQLite();
        //createDatabaseSQLite();
        databaseSQLite();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddNotes();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // chức năng thêm note
    private void dialogAddNotes(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_note);

        EditText editText = dialog.findViewById(R.id.addTextName);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập note!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseHandler.QueryData("Insert into Notes Values(null, '" + name + "')");
                    Toast.makeText(MainActivity.this, "Đã thêm Note: "+ name, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // chức năng edit note
    public void dialogUpdateNotes(String name, int id){

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_notes);

        EditText editText = dialog.findViewById(R.id.editTextName);
        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        editText.setText(name);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                databaseHandler.QueryData("Update Notes Set NameNotes='"+name+"' Where Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Cập nhập Note thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //chức năng xóa note
    public void dialogDeleteNotes(String name, int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Xác nhận xóa note '" + name + "'?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHandler.QueryData("Delete From Notes Where Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Đã xóa Note '" + name + "'!", Toast.LENGTH_SHORT).show();
                databaseSQLite();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}