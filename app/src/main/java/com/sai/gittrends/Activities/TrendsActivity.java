package com.sai.gittrends.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.sai.gittrends.Models.GitAnswer;
import com.sai.gittrends.Models.Repository;
import com.sai.gittrends.R;
import com.sai.gittrends.Utilities.RetrofitClient;
import com.sai.gittrends.Utilities.Settings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TrendsActivity extends AppCompatActivity implements TrendsRecyclerViewAdapater.IRecDelegate {
    private List<Repository> repositories = new ArrayList<>();
    private TrendsRecyclerViewAdapater adapter;
    RecyclerView recyclerView;
    private View mProgressCatView;
    Button retryBT;
    GitTrendsService gitTrendsService;
    public static int currentPage = 1;

    public interface GitTrendsService {

        @GET(Settings.URLs.bestTrends)
        Call<GitAnswer> getTrendOfGithub(@Query("page") int page);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        recyclerView = findViewById(R.id.at_rv_repos);
        mProgressCatView = findViewById(R.id.at_pb_loading);
        retryBT = findViewById(R.id.at_bt_retry);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TrendsRecyclerViewAdapater(repositories, getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
        populateTrends(1);
        currentPage = 1;

    }

    public void onRetry(View v) {
        populateTrends(currentPage);
    }

    private void populateTrends(int page) {
        retryBT.setVisibility(View.INVISIBLE);
        mProgressCatView.setVisibility(View.VISIBLE);
        gitTrendsService = RetrofitClient.getClient(Settings.URLs.root).create(GitTrendsService.class);
        gitTrendsService.getTrendOfGithub(page).enqueue(new Callback<GitAnswer>() {
            @Override
            public void onResponse(Call<GitAnswer> call, Response<GitAnswer> response) {

                if (response.isSuccessful()) {
                    GitAnswer answer = response.body();
                    repositories.addAll(answer.getItems());
//
//                    adapter.addList(answer.getItems());
                    adapter.notifyDataSetChanged();
                    mProgressCatView.setVisibility(View.INVISIBLE);

//                    mAdapter.updateAnswers(response.body().getItems());
                    Log.d("Trends: ", "posts loaded from API" + repositories.size());
                } else {
                    mProgressCatView.setVisibility(View.INVISIBLE);
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<GitAnswer> call, Throwable t) {
//                    showErrorMessage();
                Log.d("MainActivity", "error loading from API");
                retryBT.setVisibility(View.VISIBLE);
                mProgressCatView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onEndScroll(int pos) {
        int tempLast = (currentPage - 1) * Settings.PageItem;
        if (pos > tempLast) {
            currentPage++;
            populateTrends(currentPage);
        }
    }

    @Override
    public void onRepoClicked(int pos) {
        try {
            Intent i = new Intent(TrendsActivity.this, RepositoryDetailsActivity.class);
            Repository rep = repositories.get(pos);
            Gson gson = new Gson();
            String repJS = gson.toJsonTree(rep).toString();
            i.putExtra("rep", repJS);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
//
    }
}

