package com.lenguajes.recetas_bombur.recipes.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.RecetasBomburApplication;
import com.lenguajes.recetas_bombur.activitymanagement.DialogManager;
import com.lenguajes.recetas_bombur.activitymanagement.IntentUtils;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;
import com.lenguajes.recetas_bombur.permissions.PermissionsManager;
import com.lenguajes.recetas_bombur.utils.PathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class CreateRecipeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog exitDialog;
    private RecyclerView mImagesRecyclerView;
    private ArrayList<String> mImagesPaths;
    private final StorageReference storageReference = RecetasBomburApplication.getStorageReference();
    private ProgressDialog uploadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);
        setUpExitDialog();

        //TODO This is not intended to be re-assigned on configuration changes
        this.mImagesPaths = new ArrayList<>();

        setUpImagesRecycler();

        uploadDialog = new ProgressDialog(this);
        uploadDialog.setCancelable(false);
        uploadDialog.setTitle(getString(R.string.uploading_recipe));
        uploadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }


    private void setUpImagesRecycler(){
        mImagesRecyclerView = findViewById(R.id.createRecipe_ImagesRecycler);

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mImagesRecyclerView.setLayoutManager(linearLayoutManager);

        //Set its adapter by creating a new adapter from the CardView layout resource
        ImageRecyclerViewAdapter imageAdapter = new ImageRecyclerViewAdapter(mImagesPaths,
                R.layout.small_image_card_view, R.id.smallImage_card, this);


        mImagesRecyclerView.setAdapter(imageAdapter);

    }


    private void setUpExitDialog(){
        exitDialog = DialogManager.createYesNoDialog(this,
                (DialogInterface dialog, int which)->finish(),
                (DialogInterface dialog, int which) -> dialog.dismiss(),
                getString(R.string.warning),
                getString(R.string.recipe_cancel_prompt));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exitDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitDialog.show();
    }

    public void cancelNewRecipe(View view) {
        exitDialog.show();
    }

    public void addImage(View view) {
        PermissionsManager manager = new PermissionsManager(this);

        if(manager.checkAndRequestStoragePermissions())
            IntentUtils.openGallery(this, PICK_IMAGE_REQUEST);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST){
            if (data != null) {
                Uri imageUri = data.getData();

                File file = PathUtil.getFileFromUri(this, imageUri);

                if (file.exists())
                    mImagesPaths.add(file.getAbsolutePath());

            }
        }
    }

    //TODO delegate logic to the Interactor
    public void createNewRecipe(View view) {
        //TODO Change this to upload all images on the array
        String imagePath = mImagesPaths.get(0);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();
        String imageName = imagePath.substring(imagePath.lastIndexOf('/') + 1);

        StorageReference newImageReference = storageReference.child("test1/" + imageName);

        UploadTask uploadTask = newImageReference.putBytes(imageBytes);

        uploadDialog.show();

        uploadTask.addOnProgressListener(this, taskSnapshot -> {
            double percentage = taskSnapshot.getBytesTransferred() * 100 / (double)taskSnapshot.getTotalByteCount();
            uploadDialog.setProgress((int)percentage);
        });

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

            result.addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                Log.d("CreateRecipeActivity", "Subida completada en: " + imageUrl);
                uploadDialog.dismiss();
                finish();
            });

        });

        uploadTask.addOnFailureListener(e -> {
            Log.d("CreateRecipeActivity", "Error en la subida: " + e.getMessage());
            uploadDialog.dismiss();
        });

    }
}
