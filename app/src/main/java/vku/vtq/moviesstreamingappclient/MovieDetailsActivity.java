package vku.vtq.moviesstreamingappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vku.vtq.moviesstreamingappclient.Adapter.MovieShowAdapter;
import vku.vtq.moviesstreamingappclient.Model.GetVideoDetails;

import static com.androidquery.util.AQUtility.getContext;

public class MovieDetailsActivity extends AppCompatActivity  {

     private ImageView MoviesThumbNail , MoviesCoverImg;
     TextView tv_title, tv_description;
     FloatingActionButton play_fab;
     RecyclerView RvCast, recyclerView_similarMovies;
     MovieShowAdapter movieShowAdapter;
     DatabaseReference mDatabaseReference;
     List<GetVideoDetails> uploads,actionsmovies,sportsmovies,comedymovies,
                       romanticmovies, adventuremovies;
     String current_video_url;
     String current_video_category;
        Toolbar toolbar;
     FirebaseAuth mAuth;
     GetVideoDetails movies;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
         Intent intent = getIntent();
         if (intent != null){
             current_video_url = intent.getStringExtra("videoUri1");
             current_video_category = intent.getStringExtra("videocat");
         }
        inView();

         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setTitle("HQ Movies Center");


        similarmoviesRecycler();
        similarMovies();

        play_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this,MoviePlayerActivity.class);
                intent.putExtra("videoUri",current_video_url);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.yeuthich,menu);





        return super.onCreateOptionsMenu(menu);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case  R.id.yeuthich:
                themvaoyeuthich();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void themvaoyeuthich() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("YeuThich");
        HashMap<String,Object> yeuthich =new HashMap<>();
        String name = movies.getVideo_name();
        String cate = movies.getVideo_category();
        String des = movies.getVideo_description();
        String img = movies.getVideo_thumb();
        String id= movies.getId();



        yeuthich.put("name",name);
        yeuthich.put("category",cate);
        yeuthich.put("descripton",des);
        yeuthich.put("image",img);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String uid=currentUser.getUid();

        myRef.child(uid).child(id).updateChildren(yeuthich).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MovieDetailsActivity.this, "Thêm vào danh sách yêu thích thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }





    private void similarMovies() {
        Intent intent = getIntent();
        if (intent != null){
            current_video_url = intent.getStringExtra("videoUri1");
            current_video_category = intent.getStringExtra("videocat");
            System.out.println("get data cato : "+ current_video_category);
        }
         if (current_video_category.equals("Action")){
             movieShowAdapter= new MovieShowAdapter(this, actionsmovies);
          recyclerView_similarMovies.setAdapter(movieShowAdapter);
          recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                  LinearLayoutManager.HORIZONTAL,false));
          movieShowAdapter.notifyDataSetChanged();
         }

        if (current_video_category.equals("Sports")){
            movieShowAdapter= new MovieShowAdapter(this, sportsmovies);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();
        }
        if (current_video_category.equals("Adventure")){
            movieShowAdapter= new MovieShowAdapter(this, adventuremovies);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();
        }
        if (current_video_category.equals("Comedy")){
            movieShowAdapter= new MovieShowAdapter(this, comedymovies);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();
        }
        if (current_video_category.equals("Romatic")){
            movieShowAdapter= new MovieShowAdapter(this, romanticmovies);
            recyclerView_similarMovies.setAdapter(movieShowAdapter);
            recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL,false));
            movieShowAdapter.notifyDataSetChanged();
        }
    }

    private void similarmoviesRecycler() {
         uploads = new ArrayList<>();
         sportsmovies = new ArrayList<>();
         comedymovies= new ArrayList<>();
         romanticmovies = new ArrayList<>();
         adventuremovies= new ArrayList<>();
         actionsmovies= new ArrayList<>();
          mDatabaseReference= FirebaseDatabase.getInstance().getReference("videos");
          mDatabaseReference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot postsnapshot : snapshot.getChildren() ){
                      GetVideoDetails upload = postsnapshot.getValue(GetVideoDetails.class);
                      if (upload.getVideo_category().equals("Action")){
                          actionsmovies.add(upload);
                      }
                      if (upload.getVideo_category().equals("Sports")){
                          sportsmovies.add(upload);
                      }
                      if (upload.getVideo_category().equals("Adventure")){
                          adventuremovies.add(upload);
                      }
                      if (upload.getVideo_category().equals("Comedy")){
                          comedymovies.add(upload);
                      }
                      if (upload.getVideo_category().equals("Romantic")){
                          romanticmovies.add(upload);
                      }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
    }

    private void inView() {
        String moviesTitle = getIntent().getExtras().getString("title");
        String imgRecoresId= getIntent().getExtras().getString("imgURL");
        String imageCover = getIntent().getExtras().getString("imgCover");
        String moviesDetailstext = getIntent().getExtras().getString("movieDetails");
        String moviesUrl = getIntent().getExtras().getString("movieUrl");
        String moviesCategory = getIntent().getExtras().getString("movieCategory");
        current_video_url = moviesUrl;
        current_video_category= moviesCategory;


        toolbar = (Toolbar) findViewById(R.id.toolbar3);
         play_fab = findViewById(R.id.play_fab);
         tv_title= findViewById(R.id.detail_movie_title);
         tv_description=  findViewById(R.id.detail_movie_desc);
          MoviesThumbNail = findViewById(R.id.details_movies_img);
          MoviesCoverImg = findViewById(R.id.detail_movies_cover);
         recyclerView_similarMovies = findViewById(R.id.recyler_similar_movies);
         tv_title.setText(moviesTitle);
        tv_description.setText(moviesDetailstext);
        GlideApp.with(this).load(imgRecoresId).into(MoviesThumbNail);
        GlideApp.with(this).load(imageCover).into(MoviesCoverImg);
//    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailsActivity.this,
//             MoviesCoverImg,"sharedName" );
//    options.toBundle();


    }



}