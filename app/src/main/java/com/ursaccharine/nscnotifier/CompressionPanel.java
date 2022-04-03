package com.ursaccharine.nscnotifier;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CompressionPanel extends AppCompatActivity {

   private MaterialButton  com_btn,mButtonViewShowUploads;
   private ImageView imageView2;
   private final int PICK_IMAGE_CODE = 172;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compression_panel);

        com_btn = findViewById(R.id.com_btn);
        imageView2 = findViewById(R.id.imageView2);
        mButtonViewShowUploads = findViewById(R.id.mButtonViewShowUploads);

        mButtonViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

        com_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkP();
            }
        });

    }

    private void checkP() {
        Dexter.withActivity(CompressionPanel.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,PICK_IMAGE_CODE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK){
            if (data!=null){
                Uri imageUri = data.getData();
                try {
                    Bitmap original = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    original.compress(Bitmap.CompressFormat.JPEG,50,stream);
                    imageView2.setImageBitmap(original);
                    byte[] imageByte = stream.toByteArray();
                    uploadImage(imageByte);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(byte[] imageByte) {
        ProgressDialog progressDialog = new ProgressDialog(CompressionPanel.this);
        progressDialog.setMessage("Image uploading...");
        progressDialog.show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Images").child(System.currentTimeMillis()+"jpeg");
        storageReference.putBytes(imageByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(CompressionPanel.this,"Image uploaded!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CompressionPanel.this,"Image upload failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this,ImageRetrieve.class);
        startActivity(intent);
    }
}