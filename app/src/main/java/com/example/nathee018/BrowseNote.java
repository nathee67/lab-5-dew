package com.example.nathee018;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseNote extends AppCompatActivity {
    private EditText etName;
    private Button btnSearch;
    private ProgressBar progressBar;
    private TextView tvResult, showNote, showNoteFromAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browse_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.editTextText2);
        btnSearch = findViewById(R.id.button3);
        tvResult = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        btnSearch.setOnClickListener(v -> {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);

            progressBar.setVisibility(View.VISIBLE);
            btnSearch.setEnabled(false);
            tvResult.setText("กำลังค้นหาโน้ตของ " + etName.getText().toString().trim() + "...");

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnSearch.setEnabled(true);

                    tvResult.setText("ไม่พบข้อมูล");
                });
            }).start();
        });
        showNote = findViewById(R.id.textView3);
        showNoteFromAPI = findViewById(R.id.textView);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<NoteEntity> entities = AppDatabase.getInstance(this).noteDao().getAll();
            List<Note> notes = new ArrayList<>();
            for (NoteEntity e : entities) {
                notes.add(NoteMapper.fromEntity(e));

            }
            runOnUiThread(() -> {
                StringBuilder sd = new StringBuilder();
                for (Note n : notes) {
                    sd.append(n.getSummary()).append("\n");
                }
                showNote.setText(sd.toString());
            });
        });
        //load from API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<TextNote>> call = apiService.getTextNote();

        call.enqueue(new Callback<List<TextNote>>() {
            @Override
            public void onResponse(Call<List<TextNote>> call, Response<List<TextNote>> response) {
                if (!response.isSuccessful()) {
                    showNoteFromAPI.setText("Error Code: " + response.code());
                    return;
                }

                List<TextNote> notes = response.body();
                StringBuilder builder = new StringBuilder();
                for (TextNote n : notes) {
                    builder.append("Title: ").append(n.getTitle()).append("\n")
                            .append("Body: ").append(n.getTextContent()).append("\n\n");
                }
                showNoteFromAPI.setText(builder.toString());
            }

            @Override
            public void onFailure(Call<List<TextNote>> call, Throwable t) {
                showNoteFromAPI.setText("Failed: " + t.getMessage());
            }
        });

    }
}