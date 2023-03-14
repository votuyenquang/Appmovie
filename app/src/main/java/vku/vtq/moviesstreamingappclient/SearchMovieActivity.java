package vku.vtq.moviesstreamingappclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vku.vtq.moviesstreamingappclient.Adapter.SearchAdapter;
import vku.vtq.moviesstreamingappclient.Model.SearchModel;

public class SearchMovieActivity extends AppCompatActivity {
    RecyclerView recview;
    SearchAdapter adapter;
    DatabaseReference mDatabasereference;
    SearchView searchView;
    Toolbar toolbar;


    private List<SearchModel> uploads;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarsearch);
        setSupportActionBar(toolbar);

        recview = (RecyclerView) findViewById(R.id.recview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recview.setLayoutManager(linearLayoutManager);

        addAllMovies();
        inMovie();

//        adapter = new MovieShowAdapter(getListMovie);
//        recview.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void addAllMovies() {

        uploads = new ArrayList<>();
        mDatabasereference = FirebaseDatabase.getInstance().getReference("videos");


        mDatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SearchModel upload = postSnapshot.getValue(SearchModel.class);
                    uploads.add(upload);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inMovie() {
        adapter = new SearchAdapter(this, uploads);
        recview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public void onMovieClick(GetVideoDetails movie, ImageView imageView) {
//        Intent in = new Intent(this, MovieDetailsActivity.class);
//        in.putExtra("title",movie.getVideo_name());
//        in.putExtra("imgURL",movie.getVideo_thumb());
//        in.putExtra("imgCover",movie.getVideo_thumb());
//        in.putExtra("movieDetails",movie.getVideo_description());
//        in.putExtra("movieUrl",movie.getVideo_url());
//        in.putExtra("movieCategory",movie.getVideo_category());
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SearchMovieActivity.this,imageView,"sharedName");
//        startActivity(in,options.toBundle());
//    }


}