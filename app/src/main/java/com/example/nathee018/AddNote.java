package com.example.nathee018;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddNote extends AppCompatActivity {

    private EditText etUserName, etPassword;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUserName = findViewById(R.id.editTextText);
        etPassword = findViewById(R.id.editTextText2);
        btnSave = findViewById(R.id.button);

       /* btnSave.setOnClickListener(v -> {
            String name = etUserName.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (name.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = null;
            if (name.equals("dewdo18") && pass.equals("2549")) {
                user = new User("dewdo18", "2549");
                user.setEmail("dewdo@mail.com");
            } else if (name.equals("nathee") && pass.equals("2006")) {
                user = new User("nathee", "2006");
                user.setEmail("nahee@example.com");
            }

            if (user == null) {
                Toast.makeText(this, "ไม่พบผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent result = new Intent();
            result.putExtra("user", user);
            setResult(RESULT_OK, result);
            finish();

            NoteEntity entity = NoteMapper.toEntity(Note);

            Context context = View.getContext();
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase.getInstance(context).noteDao().insert(entity);

            });*/
        //lAB7
        btnSave.setOnClickListener(v -> {
            String title = etUserName.getText().toString().trim();
            String content = etPassword.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                return;
            }

            //Note (TextNote)
            String createdDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
                    .format(new java.util.Date());
            Note note = new TextNote(title, createdDate, content);

            //OOP -> entity 11/9/68
            NoteEntity entity = NoteMapper.toEntity(note);

            //add bata to db
            Context context = getApplicationContext();
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase.getInstance(context).noteDao().insert(entity);

                runOnUiThread(() -> {
                    Toast.makeText(this, "บันทึกโน้ตสำเร็จ", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
        }
}