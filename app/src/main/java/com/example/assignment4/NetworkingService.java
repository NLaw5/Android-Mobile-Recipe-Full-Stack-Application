package com.example.assignment4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {

    private String multipleRecipesURL = "https://api.spoonacular.com/recipes/complexSearch?";
    private String singleRecipeURl = "https://api.spoonacular.com/recipes/";
    private String recipeDetail = "";
    private String iconURL ="";
    private String key = "apiKey=9b5296bb122849b69547843c900ed342";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkHandler = new Handler (Looper.getMainLooper());

    interface NetworkingListener {
        void dataListener(String jsonString);
        void imageListener(Bitmap image);
    }

    public NetworkingListener listener;
    public void searchForRecipes(String queryUrl)
    {
        String urlString = multipleRecipesURL + key + "&query=" + queryUrl + "&number=50";
        connect(urlString);
    }

    public void recipeDetails(String id)
    {
       String urlString = singleRecipeURl + id + "/summary?" + key;
       connect(urlString);
    }

    public void getImageData(String imageURl) {
        String urlstr = imageURl;
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlObj = new URL(urlstr);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) urlObj.getContent());

                    networkHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // any code here will run in main thread
                            listener.imageListener(bitmap);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void connect(String url) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run(){
                HttpURLConnection httpURLConnection = null;
                try{
                    String jsonData = "";
                    URL urlObj = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");

                    //The Inputstreamreader will allow us to get our json data
                    //The inputstream in contains the httpURLConnection.getInputstream() aka json
                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    int inputSteamData = 0;
                    while ( (inputSteamData = reader.read()) != -1){// there is data in this stream
                        char current = (char)inputSteamData;
                        jsonData += current;
                    }

                    //Converts all the data to string finaldata
                    final String finalData = jsonData;
                    // the data is ready
                    // Handler will allow us to access main thread/update main thread
                    networkHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // any code here will run in main thread
                            //set the dataListener in our class to the json
                            listener.dataListener(finalData);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    //remember to disconnect connection
                    httpURLConnection.disconnect();
                }
            }
        });
    }
}
