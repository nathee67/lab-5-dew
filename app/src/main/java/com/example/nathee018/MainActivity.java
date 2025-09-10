package com.example.nathee018;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnAddNote, btnBrowseNote;
    ProgressBar progressBar2;
    ImageView logo;

    private final ActivityResultLauncher<Intent> addNoteLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    User user = (User) result.getData().getSerializableExtra("user");
                    if (user != null) {
                        Intent show = new Intent(this, UserDetailActivity.class);
                        show.putExtra("user", user);
                        startActivity(show);


                    } else {
                        Toast.makeText(this, "No user returned", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logo = findViewById(R.id.imageView2);
        btnAddNote = findViewById(R.id.button3);
        btnBrowseNote = findViewById(R.id.button4);
        progressBar2 = findViewById(R.id.progressBar2);

        logo.setImageResource(R.drawable.dewdo);
        progressBar2.setVisibility(android.view.View.GONE);

        btnAddNote.setOnClickListener(v -> {
            Intent addNote = new Intent(MainActivity.this, AddNote.class);
            addNoteLauncher.launch(addNote);
        });

        btnBrowseNote.setOnClickListener(v -> {
            progressBar2.setVisibility(android.view.View.VISIBLE);
            new Thread(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                runOnUiThread(() -> {
                    progressBar2.setVisibility(android.view.View.GONE);
                    startActivity(new Intent(getApplicationContext(), BrowseNote.class));
                });
            }).start();
        });
    }
    /*public static void main(String[] args) {
//        Note note1 = new TextNote();
//        User user1 = new User();

//        note1.title = "Introduction to Java";
//        note1.content = "Java is a high-level programming language developed by Sun Microsystems...";
//        note1.createdDate = "2025-07-17";
//        note1.getSummary();

        TextNote textNote1 = new TextNote();
        textNote1.title = "Introduction to Java";
        //textNote1.content = "Java is a high-level programming language developed by Sun Microsystems...";
        textNote1.setTextContent = "Java is a high-level programming language developed by Sun Microsystems...";
        textNote1.createdDate = "2025-07-17";
        textNote1.getSummary();


//        user1.username = "zero";
//        user1.password = "StrongPass!2025";
//        user1.email = "zero@example.com";
//        user1.information();
//        user1.logout();

        AdminUser admin = new AdminUser();
        admin.setUsername("admin01");
        admin.setPassword("1234");
        admin.setEmail("admin@mail.com");
        admin.information();
        admin.logout();

        CustomerUser customer = new CustomerUser();
        customer.setUsername("zero");
        customer.setPassword("StrongPass!2025");
        customer.setEmail("zero@example.com");
        customer.information();
        customer.logout();
    }*/
    }
