package com.lenguajes.recetas_bombur.recipes.interactor;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.RecetasBomburApplication;
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
    private ArrayList<String> mImageURLs;
    private String TAG = "CreateRecipeInteractor";

    public CreateRecipeInteractorImpl(CreateRecipePresenter presenter) {
        this.presenter = presenter;
        this.mImageURLs = new ArrayList<>();
    }


    @Override
    public void createNewRecipe(String name, String type, String preparation, ArrayList<String> ingredients,
                                ArrayList<String> imagePaths,int durationMinutes, AppCompatActivity activity) {


        //First upload images
        uploadImages(name, type, preparation, ingredients, imagePaths, durationMinutes, activity);

    }

    @Override
    public void sendErrorMessage() {
        presenter.sendErrorMessage();
    }

    @Override
    public void finishUploadProcess() {
        presenter.finishUploadProcess();
    }

    private void uploadImages(String name, String type, String preparation, ArrayList<String> ingredients,
                                ArrayList<String> paths, int durationMinutes, AppCompatActivity activity){

        for (String path : paths) {

            //Create a bitmap from the image
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            //Compress bitmap and store the image bytes
            byte[] imageBytes = ImageUtil.getBytesFromBitmap(bitmap, Bitmap.CompressFormat.JPEG, 100);

            //Get the name with the extension
            String imageName = PathUtil.getFileNameFromPath(path, true);

            //Begin the image upload
            UploadTask uploadTask = FirebaseUploadUtil.uploadToFirebase("test", imageName, imageBytes);

            setImageUploadTaskControls(uploadTask, name, type, preparation, ingredients, paths.size(),
                                        durationMinutes, activity);
        }
    }


    private void setImageUploadTaskControls(UploadTask uploadTask, String name, String type, String preparation, ArrayList<String> ingredients,
                                            int pathsSize,int durationMinutes, AppCompatActivity activity){

        //The percentage each task takes, to get 50
        float individualTaskPercentage = 50/(float) pathsSize;


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
                //Add the download URL to the urls
                String imageUrl = uri.toString();
                mImageURLs.add(imageUrl);

                //Log the upload event
                //Log.d(TAG, Resources.getSystem().getString(R.string.upload_completed) + imageUrl);

                //One image is uploaded, check if it's the last one
                if (pathsSize == mImageURLs.size()){
                    postToAPI(name, type, preparation, ingredients, durationMinutes, activity);
                }

            });

        });


        //On failure
        uploadTask.addOnFailureListener(e -> {

            Log.d(TAG, Resources.getSystem().getString(R.string.error_image_upload) + e.getMessage());


            sendErrorMessage();
            finishUploadProcess();

        });
    }

    private void postToAPI(String name, String type, String preparation, ArrayList<String> ingredients, int durationMinutes,
                           AppCompatActivity activity){



        //Create recipe and Json from it
        Recipe recipe = new Recipe(durationMinutes, name, type, preparation, ingredients, mImageURLs);

        //TODO Si se logra arreglar el problema con las listas del API, descomentar esto
        /*
        String jsonString = JSONUtil.jsonStringFromObject(recipe);
        JSONObject jsonRecipe = JSONUtil.JSONObjectFromString(jsonString);

        */


        Log.d(TAG, mImageURLs.get(0));

        JSONObject jsonRecipe = JSONUtil.JSONObjectFromString(recipe.formatForBackend());

        //Log the generated Json string
        Log.d(TAG, "To send: " + jsonRecipe.toString());

        //Create the request to the API
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String postURL = RecetasBomburApplication.getURL().concat("/api/recetas?username="+RecetasBomburApplication.getSessionUsername()+"&token="+RecetasBomburApplication.getSessionToken());
        Log.d("URL",postURL);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, postURL,
                jsonRecipe,

                response -> {
                    Log.d(TAG, response.toString());
                    presenter.updateUploadProgress(50);
                    finishUploadProcess();
                },

                error -> {
                    error.printStackTrace();
                    Log.d(TAG, error.getMessage());
                }

        );

        //Post the POST request
        requestQueue.add(postRequest);

    }
}
