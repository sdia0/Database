package com.example.database;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageButton btnAdd;
    DbHelper db = new DbHelper(this);
    @Override
    protected void onStart() {
        super.onStart();
        // Получаем данные из базы данных
        List<Person> people = db.getData();

        // Создаем новый адаптер с обновленными данными
        ItemAdapter adapter = new ItemAdapter(this, people);

        // Устанавливаем новый адаптер на ListView
        listView.setAdapter(adapter);
    }

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

        listView=findViewById(R.id.listView);
        btnAdd=findViewById(R.id.btnAdd);

        //fillData();
        ItemAdapter adapter= new ItemAdapter(this, db.getData());
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(getApplicationContext(),
                        ContentActivity.class);
                Person person = (Person) parent.getAdapter().getItem(position);
                Log.d("PATH", person.getImage());
                intent.putExtra("key",person);
                startActivity(intent);
            }
        });

    }

    void fillData() {
        String packageName = getPackageName(); // Получаем имя пакета приложения

        // Данные для вставки
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Tom", "tom@gmail.com", "android.resource://" + packageName + "/drawable/pic1"),
                new Person("Sam", "samm@gmail.com", "android.resource://" + packageName + "/drawable/pic2"),
                new Person("Alice", "alice@gmail.com", "android.resource://" + packageName + "/drawable/pic3"),
                new Person("Kate", "kate@gmail.com", "android.resource://" + packageName + "/drawable/pic4"),
                new Person("Peter", "peter@gmail.com", "android.resource://" + packageName + "/drawable/pic5"),
                new Person("Henry", "henry@gmail.com", "android.resource://" + packageName + "/drawable/pic6"),
                new Person("Lee", "lee@gmail.com", "android.resource://" + packageName + "/drawable/pic7"),
                new Person("Anne", "anne@gmail.com", "android.resource://" + packageName + "/drawable/pic8"),
                new Person("Oleg", "oleg@gmail.com", "android.resource://" + packageName + "/drawable/pic9"),
                new Person("Ken", "ken@gmail.com", "android.resource://" + packageName + "/drawable/pic10")
        ));

        // Получаем объект базы данных
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        // Очищаем таблицу
        sqLiteDatabase.execSQL("DELETE FROM people");

        // Вставляем новые данные
        for (Person person : people) {
            db.insertData(person);
        }
    }
}