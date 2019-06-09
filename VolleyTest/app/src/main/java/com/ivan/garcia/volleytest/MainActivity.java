package com.ivan.garcia.volleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ivan.garcia.volleytest.Objects.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";

    private TextView textView;
    //private String url = "https://httpstat.us/200?sleep=5000 ";
    private String url = "https://www.google.com ";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.mainTxt);
    }

    public void newRequestQueue(View view) {
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work! " + error.toString());
            }
        });
        stringRequest.setTag(TAG);

        textView.setText("start request");

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    public void cancelRequest(View view) {
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
            textView.setText("canceled");
        }
    }


    /**
     * Set up a network and cache
     */
    public void newRequestAndCache(View view) {
        final RequestQueue _requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        _requestQueue = new RequestQueue(cache, network);

        // Start the queue
        _requestQueue.start();

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText("Response is: " + response.substring(0, 500));
                        _requestQueue.stop(); //es de un solo uso por eso de detiene
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        textView.setText("That didn't work! " + error.toString());
                        _requestQueue.stop(); //es de un solo uso por eso de detiene
                    }
                });

        // Add the request to the RequestQueue.
        _requestQueue.add(stringRequest);
    }

    /**
     * Use a singleton pattern
     */
    public void newRequestSingleton(View view) {
        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        // Reque st a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work! " + error.toString());
            }
        });

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * Json Request
     */
    public void requestJsonObject(View view) {
        String url = "http://date.jsontest.com/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work! " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void requestJsonArray(View view) {
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        String jsonOutput = response.toString();
                        Type listType = new TypeToken<List<User>>() {
                        }.getType();
                        List<User> users = gson.fromJson(jsonOutput, listType);

                        textView.setText("Response: " + users.size() + " objects" + "\n" + users.get(0).toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work! " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}