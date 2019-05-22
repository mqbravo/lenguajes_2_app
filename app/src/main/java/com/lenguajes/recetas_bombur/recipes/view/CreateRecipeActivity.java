package com.lenguajes.recetas_bombur.recipes.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.DialogManager;
import com.lenguajes.recetas_bombur.activitymanagement.IntentUtils;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;
import com.lenguajes.recetas_bombur.permissions.PermissionsManager;
import com.lenguajes.recetas_bombur.utils.PathUtil;

import java.io.File;
import java.util.ArrayList;

public class CreateRecipeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog exitDialog;
    private RecyclerView mImagesRecyclerView;
    private ArrayList<String> mImagesPaths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);
        setUpExitDialog();

        //TODO This is not intended to be re-assigned on configuration changes
        this.mImagesPaths = new ArrayList<>();

        setUpImagesRecycler();

    }


    private void setUpImagesRecycler(){
        mImagesRecyclerView = findViewById(R.id.createRecipe_ImagesRecycler);

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mImagesRecyclerView.setLayoutManager(linearLayoutManager);

        //Set its adapter by creating a new adapter from the CardView layout resource
        ImageRecyclerViewAdapter imageAdapter = new ImageRecyclerViewAdapter(mImagesPaths,
                R.layout.small_image_card_view, this);


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

                if (file.exists()) {
                    ImageRecyclerViewAdapter adapter = (ImageRecyclerViewAdapter) mImagesRecyclerView.getAdapter();
                    mImagesPaths.add(file.getAbsolutePath());
                }
            }
        }
    }
}
