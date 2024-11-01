package com.example.database;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContentActivity extends AppCompatActivity {

    TextView tId;
    EditText etName, etEmail;
    ImageView imageView;
    ImageButton btnLoad, btnSave, btnDelete;
    Uri imagePath = null;
    DbHelper db = new DbHelper(this);
    private final int GALLERY_REQ_CODE = 1;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tId = findViewById(R.id.tIndex);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        imageView = findViewById(R.id.imageView);
        btnLoad = findViewById(R.id.btnLoadImage);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);


        person = (Person) getIntent().getSerializableExtra("key");
        tId.setText("ID: " + person.getId());
        etName.setText(person.getName());
        etEmail.setText(person.getEmail());
        try {
            imagePath = Uri.parse(person.getImage());
            imageView.setImageURI(imagePath);
        } catch (Exception e) {
            requestPermission();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Person new_person = new Person(etName.getText().toString(),
                        etEmail.getText().toString(),
                        imagePath.toString());
                new_person.setId(person.getId());
                try {
                    Boolean result = db.updateData(new_person);
                    if (result) {
                        Toast.makeText(getApplicationContext(),
                                "Data updated",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else Toast.makeText(getApplicationContext(),
                            "Cannot update data",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("COLUMN", e.getMessage() + "");
                    Log.i("keykey",new_person.getId()+"");
                    Log.i("keykey",e.getMessage());
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Boolean checkInsert = db.deleteData(person);
                    if (checkInsert) {
                        Toast.makeText(getApplicationContext(),
                                "Data deleted",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(),
                                "Data not deleted",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(),
                            Toast.LENGTH_SHORT).show();

                    Log.i("keykey",person.getId()+"");
                    Log.i("keykey",e.getMessage());
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image load
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                //for gallery
                imagePath = data.getData();
                imageView.setImageURI(data.getData());
            }
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 100;

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_MEDIA_IMAGES
            }, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }
}