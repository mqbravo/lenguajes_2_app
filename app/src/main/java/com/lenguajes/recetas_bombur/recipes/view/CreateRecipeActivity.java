package com.lenguajes.recetas_bombur.recipes.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.DialogManager;
import com.lenguajes.recetas_bombur.activitymanagement.IntentUtils;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;
import com.lenguajes.recetas_bombur.permissions.PermissionsManager;

import com.lenguajes.recetas_bombur.recipes.presenter.CreateRecipePresenter;
import com.lenguajes.recetas_bombur.recipes.presenter.CreateRecipePresenterImpl;
import com.lenguajes.recetas_bombur.utils.FirebaseUploadUtil;
import com.lenguajes.recetas_bombur.utils.ImageUtil;
import com.lenguajes.recetas_bombur.utils.PathUtil;


import java.io.File;
import java.util.ArrayList;

public class CreateRecipeActivity extends AppCompatActivity implements CreateRecipeView{

    private static final String TAG = "CreateRecipeActivityTAG";
    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog exitDialog;
    private ArrayList<String> mImagesPaths;
    private ArrayList<String> mIngredients;
    private ProgressDialog mUploadDialog;
    private float mCurrentUploadProgress = 0f;
    private RecyclerView mIngredientsRecyclerView;
    private TextInputLayout mName;
    private TextInputLayout mType;
    private TextInputLayout mPreparation;
    private TextInputLayout mNewIngredient;

    private CreateRecipePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);
        setUpExitDialog();

        //TODO This is not intended to be re-assigned on configuration changes
        this.mImagesPaths = new ArrayList<>();
        this.mIngredients = new ArrayList<>();

        mName = findViewById(R.id.createRecipe_NameTextInputLayout);
        mPreparation = findViewById(R.id.createRecipe_preparationTextInputLayout);
        mNewIngredient = findViewById(R.id.createRecipe_NewIngredientTextInputLayout);
        mType = findViewById(R.id.createRecipe_TypeTextInputLayout);


        //Set up recyclers
        setUpImagesRecycler();
        setUpIngredientsRecycler();

        //Set up presenter
        this.presenter = new CreateRecipePresenterImpl(this);

        //Set up the progress bar dialog
        mUploadDialog = new ProgressDialog(this);
        mUploadDialog.setCancelable(false);
        mUploadDialog.setTitle(getString(R.string.uploading_recipe));
        mUploadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }


    private void setUpImagesRecycler(){
        RecyclerView mImagesRecyclerView = findViewById(R.id.createRecipe_ImagesRecycler);

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mImagesRecyclerView.setLayoutManager(linearLayoutManager);

        //Set its adapter by creating a new adapter from the CardView layout resource
        ImageRecyclerViewAdapter imageAdapter = new ImageRecyclerViewAdapter(mImagesPaths,
                R.layout.small_image_card_view, R.id.smallImage_card, this);


        mImagesRecyclerView.setAdapter(imageAdapter);

    }

    private void setUpIngredientsRecycler(){
        mIngredientsRecyclerView = findViewById(R.id.createRecipe_IngredientsRecycler);

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mIngredientsRecyclerView.setLayoutManager(linearLayoutManager);

        //Set the custom adapter
        CreateRecipeIngredientTextAdapter adapter = new CreateRecipeIngredientTextAdapter(mIngredients, R.layout.text_card_view
                                                ,this);

        mIngredientsRecyclerView.setAdapter(adapter);

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
    public void addIngredient(View view) {

        if(isValidNewIngredient()) {
            EditText newIngredientEditText = mNewIngredient.getEditText();

            mIngredients.add(newIngredientEditText.getText().toString());
            newIngredientEditText.setText("");

            mIngredientsRecyclerView.getAdapter().notifyItemInserted(mIngredients.size() - 1);
            mIngredientsRecyclerView.scrollToPosition(mIngredients.size() - 1);
        }
    }

    public void createNewRecipe(View view) {
        if(validateInputs())
            createNewRecipe_aux();
    }

    private void createNewRecipe_aux(){
        mUploadDialog.show();

        String name = mName.getEditText().getText().toString();
        String preparation = mPreparation.getEditText().getText().toString();
        String type = mType.getEditText().getText().toString();

        presenter.createNewRecipe(name, type, preparation, mIngredients, mImagesPaths, 0, this);

    }


    private boolean isValidName(){
        String nameInput = mName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()){
            mName.setError(getString(R.string.field_empty_message));
            return false;
        }

        else if (nameInput.length() < 4){
            mName.setError(getString(R.string.name_too_short));
            return false;
        }

        else{
            mName.setError(null);
            return true;
        }
    }


    private boolean isValidPreparation(){
        String preparationInput = mPreparation.getEditText().getText().toString().trim();

        if (preparationInput.isEmpty()){
            mPreparation.setError(getString(R.string.field_empty_message));
            return false;
        }

        else if (preparationInput.length() < 30){
            mPreparation.setError(getString(R.string.specify_further));
            return false;
        }

        else{
            mPreparation.setError(null);
            return true;
        }
    }

    private boolean isValidImages(){
        if(mImagesPaths.size() == 0) {
            Toast.makeText(this, R.string.no_images_uploaded, Toast.LENGTH_LONG).show();
            return false;
        }

        else
            return true;
    }

    private boolean isValidNewIngredient(){
        String newIngredientInput = mNewIngredient.getEditText().getText().toString().trim();

        if(newIngredientInput.isEmpty()){
            mNewIngredient.setError(getString(R.string.field_empty_message));
            return false;
        }

        else{
            mNewIngredient.setError(null);
            return true;
        }
    }

    private boolean validateInputs(){
        return !(!isValidImages() | !isValidName() | !isValidPreparation());
    }

    @Override
    public void updateUploadProgress(int newProgress) {
        mCurrentUploadProgress += newProgress;
        mUploadDialog.setProgress((int) mCurrentUploadProgress);
    }

    @Override
    public void finishUploadProcess() {
        mUploadDialog.dismiss();
        finish();
    }

    @Override
    public void showErrorMessage() {
        //Set the progress to 0 again
        mCurrentUploadProgress = 0f;
        mUploadDialog.setProgress((int)mCurrentUploadProgress);

        Toast.makeText(this, getString(R.string.error_uploading_recipe), Toast.LENGTH_LONG).show();

    }
}
