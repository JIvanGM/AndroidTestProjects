package com.ivan.garcia.retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ivan.garcia.retrofittest.Connetion.RestClient;
import com.ivan.garcia.retrofittest.Objects.Post;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;
    private Retrofit retrofit;
    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.resultTxt);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restClient = retrofit.create(RestClient.class);

        updatePost(null);
    }

    public void cloneArray(View view){
        ArrayList<Post> postList = new ArrayList<>();
        ArrayList<Post> postList2 = new ArrayList<>();

        Post post = new Post("titulo", "texto", "1");
        postList.add(post);

        //it doesnt works
        //postList2 = (ArrayList<Post>) postList.clone();

        //it works
        copiarArrayList(postList2, postList);


        postList2.get(0).setTitle("titulo2");

        Post p1 = postList.get(0);
        Post p2 = postList2.get(0);

        Log.i("nothing", "nothing");
    }

    public void copiarArrayList(ArrayList<Post> copia, ArrayList<Post> av) {
        try {
            copia.clear();
            for (int i = 0; i < av.size(); i++) {
                //copia.add(new Post(av.get(i)));
                copia.add((Post) av.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void getAllPosts(View view) {
        txtResult.setText("");

        Call<List<Post>> call = restClient.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    txtResult.setText("code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post P : posts) {
                    String content = "";
                    content += "ID: " + P.getId() + "\n";
                    content += "User ID: " + P.getUserId() + "\n";
                    content += "Title: " + P.getTitle() + "\n";
                    content += "Text: " + P.getText() + "\n\n";

                    txtResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                txtResult.setText(t.getMessage());
            }
        });
    }

    public void getPostById(View view) {
        txtResult.setText("");

        Call<Post> call = restClient.getPostById(1);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txtResult.setText("code: " + response.code());
                    return;
                }

                Post P = response.body();

                String content = "";
                content += "ID: " + P.getId() + "\n";
                content += "User ID: " + P.getUserId() + "\n";
                content += "Title: " + P.getTitle() + "\n";
                content += "Text: " + P.getText() + "\n\n";

                txtResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txtResult.setText(t.getMessage());
            }
        });
    }

    public void createPost(View view) {
        txtResult.setText("");

        Post post = new Post("foo", "bar", "1");

        Call<Post> call = restClient.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txtResult.setText("code: " + response.code());
                    return;
                }

                Post P = response.body();

                String content = "";
                content += "ID: " + P.getId() + "\n";
                content += "User ID: " + P.getUserId() + "\n";
                content += "Title: " + P.getTitle() + "\n";
                content += "Text: " + P.getText() + "\n\n";

                txtResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txtResult.setText(t.getMessage());
            }
        });
    }

    public void updatePost(View view) {
        txtResult.setText("");

        Post post = new Post("fooooooooooo", "baaaaaaaar", "2");

        Call<Post> call = restClient.updatePost(1, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txtResult.setText("code: " + response.code());
                    return;
                }

                Post P = response.body();

                String content = "";
                content += "ID: " + P.getId() + "\n";
                content += "User ID: " + P.getUserId() + "\n";
                content += "Title: " + P.getTitle() + "\n";
                content += "Text: " + P.getText() + "\n\n";

                txtResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txtResult.setText(t.getMessage());
            }
        });
    }

    public void deletePost(View view) {
        txtResult.setText("");

        Call<Post> call = restClient.deletePost(1);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txtResult.setText("code: " + response.code());
                    return;
                }

                Post P = response.body();

                String content = "";
                content += "ID: " + P.getId() + "\n";
                content += "User ID: " + P.getUserId() + "\n";
                content += "Title: " + P.getTitle() + "\n";
                content += "Text: " + P.getText() + "\n\n";

                txtResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txtResult.setText(t.getMessage());
            }
        });
    }
}
