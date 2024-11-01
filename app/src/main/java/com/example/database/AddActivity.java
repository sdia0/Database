package com.example.database;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText etName,etEmail;
    ImageView imageView;
    ImageButton btnLoad;
    Button btnInsert;

    Uri imagePath = null;
    DbHelper db;

    private final int GALLERY_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        imageView=findViewById(R.id.imageView);
        btnLoad=findViewById(R.id.btnLoadImage);
        btnInsert=findViewById(R.id.btnInsert);

        db = new DbHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                if(name.isEmpty()||email.isEmpty())
                { Toast.makeText(getApplicationContext(),
                        "Fill name and email",
                        Toast.LENGTH_LONG).show();
                    return;
                }

                Person person= new Person(name, email, imagePath.toString());
                boolean result = db.insertData(person);

                if (result)
                {   Toast.makeText(getApplicationContext(),
                        "Data inserted",
                        Toast.LENGTH_SHORT);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "Data not inserted",
                            Toast.LENGTH_SHORT);
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_REQUEST_CODE){
                imagePath = data.getData();
                imageView.setImageURI(imagePath);
            }
        }
    }
}