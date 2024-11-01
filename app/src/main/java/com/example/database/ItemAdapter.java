package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<Person> {
    public ItemAdapter(@NonNull Context context, List<Person> people) {
        super(context, R.layout.activity_item, people);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            //add convertView=
            convertView= LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_item, null);

        final Person person = getItem(position);

        ((TextView) convertView.findViewById(R.id.tName))
                .setText(person.getName());

        ImageView imageView = convertView.findViewById(R.id.imageView);
        // imageView.setImageURI(Uri.parse(person.image));

        try {
            Glide.with(imageView.getContext())
                    .load(Uri.parse(person.getImage()))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("GlideError", "Load failed: " + e.getMessage());
                            return false; // Возвращаем false, чтобы позволить Glide выполнить стандартное поведение
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("GlideSuccess", "Resource loaded successfully");
                            return false; // Возвращаем false, чтобы позволить Glide выполнить стандартное поведение
                        }
                    })
                    .error(R.drawable.working)
                    .circleCrop()
                    .into(imageView);
        } catch (Exception e) {

        }
        return convertView;
    }
}