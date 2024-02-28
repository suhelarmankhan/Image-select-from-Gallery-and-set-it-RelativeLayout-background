package com.tecoditlimited.imagebackgroundsettest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private RelativeLayout relativeLayout;
    private File imageFile;
    Button selectImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativeLayout);
        selectImageButton = findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(v->{
            selectImageFromGallery();

        });


    }

    public void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                // Resize and compress the image
                CompressImageSize.BitmapWithDimensions bitmapWithDimensions = CompressImageSize.decodeSampledBitmapFromUri(getContentResolver(), imageUri, 800, 800);
                Bitmap bitmap = bitmapWithDimensions.bitmap;
                int originalWidth = bitmapWithDimensions.width;
                int originalHeight = bitmapWithDimensions.height;

                // Set the compressed image as the background
                relativeLayout.setBackground(null);
                relativeLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                showToast("Image set as background.");

                // Use originalWidth and originalHeight as needed in your application logic
                Log.d("MainActivity", "Original width: " + originalWidth + ", Original height: " + originalHeight);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showToast("Failed to process image.");
            }
        } else {
            showToast("Image selection cancelled.");
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

