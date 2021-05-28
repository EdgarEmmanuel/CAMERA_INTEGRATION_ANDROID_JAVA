package com.example.camera_integration_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button uploadImageButton;
    ImageView imageUploaded;
    private static File absoluteLocationPathToPhoto = null;
    private static final String PHOTO_EXTENSION = ".jpg";
    private static final String PHOTO_NAME  = "photo"+PHOTO_EXTENSION;
    private static final String PHOTO_FILE_PROVIDER = "com.example.camera_integration_android.fileprovider";
    private static final int CAMERA_CODE_IDENTIFIER = 1; // this number is whatever you want

    // ===================== the code that produces Hight quality image ========
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadImageButton = findViewById(R.id.uploadImageButton);
        imageUploaded = findViewById(R.id.imageUploaded);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    absoluteLocationPathToPhoto = getPhotoFile(PHOTO_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(goToCamera.resolveActivity(getPackageManager()) != null){

                    Uri fileProvider = FileProvider
                            .getUriForFile(MainActivity.this,
                                    PHOTO_FILE_PROVIDER,
                                    absoluteLocationPathToPhoto);

                    goToCamera.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider);

                    startActivityForResult(goToCamera, CAMERA_CODE_IDENTIFIER);
                } else {
                    Toast.makeText(MainActivity.this
                            ,"OUPS THERE IS NO APP TO FULFILL THIS ACTION",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private File getPhotoFile(String photoName) throws IOException {
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(photoName,PHOTO_EXTENSION,storageDirectory);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if(requestCode == CAMERA_CODE_IDENTIFIER && resultCode == Activity.RESULT_OK){
            Bitmap cameraResultData = BitmapFactory
                    .decodeFile(absoluteLocationPathToPhoto.getAbsolutePath());
            imageUploaded.setImageBitmap(cameraResultData);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

// ===================== the code that produces bad quality image ========

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        uploadImageButton = findViewById(R.id.uploadImageButton);
//        imageUploaded = findViewById(R.id.imageUploaded);
//
//        uploadImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goToCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                if(goToCamera.resolveActivity(getPackageManager()) != null){
//
//                    startActivityForResult(goToCamera, CAMERA_CODE_IDENTIFIER);
//                } else {
//                    Toast.makeText(MainActivity.this
//                            ,"OUPS THERE IS NO APP TO FULFILL THIS ACTION",
//                            Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        });
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        if(requestCode == CAMERA_CODE_IDENTIFIER && resultCode == Activity.RESULT_OK){
//            Bitmap cameraImageResult = (Bitmap)data.getExtras().get("data");
//            imageUploaded.setImageBitmap(cameraImageResult);
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//
//    }





}