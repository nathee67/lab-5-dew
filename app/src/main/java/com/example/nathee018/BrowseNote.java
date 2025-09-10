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

public class BrowseNote extends AppCompatActivity {
    private EditText etName;
    private Button btnSearch;
    private ProgressBar progressBar;
    private TextView tvResult;

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
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnSearch.setEnabled(true);

                    tvResult.setText("ไม่พบข้อมูล");
                });
            }).start();
        });
    }
}