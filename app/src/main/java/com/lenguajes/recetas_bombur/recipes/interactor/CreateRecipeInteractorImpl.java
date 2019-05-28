package com.lenguajes.recetas_bombur.recipes.interactor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.recipes.presenter.CreateRecipePresenter;
import com.lenguajes.recetas_bombur.utils.FirebaseUploadUtil;
import com.lenguajes.recetas_bombur.utils.ImageUtil;
import com.lenguajes.recetas_bombur.utils.JSONUtil;
import com.lenguajes.recetas_bombur.utils.PathUtil;

import org.json.JSONObject;

import java.util.ArrayList;

public class CreateRecipeInteractorImpl implements CreateRecipeInteractor {

    private CreateRecipePresenter presenter;

    public CreateRecipeInteractorImpl(CreateRecipePresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void createNewRecipe(String name, String type, String preparation, ArrayList<String> ingredients,
                                ArrayList<String> imagePaths,int durationMinutes, AppCompatActivity activity) {


        //First upload images
        uploadImages(imagePaths, activity);

        /*

        //TODO hacer el campo para los minutos en la GUI
        Recipe recipe = new Recipe(durationMinutes, name, type, preparation, ingredients, null);

        JSONObject jsonRecipe = JSONUtil.JSONObjectFromObject(recipe);

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        */

         /*
        JSONObject jsonObject = new JSONObject();

        String postURL = "http://pruebamau.herokuapp.com/";

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, postURL,
                null,

                response -> {

                },

                error -> {

                }

        );
                requestQueue.add(postRequest);

        */
    }

    private void uploadImages(ArrayList<String> paths, AppCompatActivity activity){

        for (String path : paths) {

            //Create a bitmap from the image
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            //Compress bitmap and store the image bytes
            byte[] imageBytes = ImageUtil.getBytesFromBitmap(bitmap, Bitmap.CompressFormat.JPEG, 100);

            //Get the name with the extension
            String imageName = PathUtil.getFileNameFromPath(path, true);

            //Begin the image upload
            UploadTask uploadTask = FirebaseUploadUtil.uploadToFirebase("test", imageName, imageBytes);

            setImageUploadTaskControls(uploadTask, paths.size(), activity);
        }
    }


    private void setImageUploadTaskControls(UploadTask uploadTask, int pathsSize, AppCompatActivity activity){
        //The percentage each task takes, to get 100
        float individualTaskPercentage = 100/(float)pathsSize;


        //On progress
        uploadTask.addOnProgressListener(activity, taskSnapshot -> {
            double taskProgress = taskSnapshot.getBytesTransferred() * 100 / (double) taskSnapshot.getTotalByteCount();
            double percentageInTotal = taskProgress * individualTaskPercentage / 100;

            //Send message to update progress bar
            presenter.updateUploadProgress((int)percentageInTotal);

        });


        //On success
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

            result.addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();

                //TODO Log this
                //Log.d("XD", getString(R.string.upload_completed) + imageUrl);
            });

        });


        //On failure
        uploadTask.addOnFailureListener(e -> {
            /*
            Log.d(TAG, getString(R.string.error_image_upload) + e.getMessage());
            mUploadDialog.dismiss();

            //Set the progress to 0 again
            mCurrentUploadProgress = 0f;
            mUploadDialog.setProgress((int)mCurrentUploadProgress);

            Toast.makeText(this, getString(R.string.error_uploading_recipe), Toast.LENGTH_LONG).show();

            */
        });
    }

}
