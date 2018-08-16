package com.sai.gittrends.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sai.gittrends.Models.Repository;
import com.sai.gittrends.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class RepositoryDetailsActivity extends AppCompatActivity {
    Repository repository;
    TextView repTitleTV;
    ImageView ownerIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);
        repTitleTV = findViewById(R.id.ard_tv_title);
        ownerIV = findViewById(R.id.ard_iv_image);
        try {
            String repJS = getIntent().getStringExtra("rep");
            Gson gson = new Gson();
            repository = gson.fromJson(repJS, Repository.class);
            repTitleTV.setText(repository.getFullName());
            Picasso.with(getApplicationContext())
                    .load(repository.getOwner().getAvatarUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ownerIV);
        } catch (Exception err)

        {

        }


    }
}
