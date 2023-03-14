package vku.vtq.moviesstreamingappclient;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vku.vtq.moviesstreamingappclient.Adapter.MovieShowAdapter;
import vku.vtq.moviesstreamingappclient.Adapter.SliderPagerAdapterNew;
import vku.vtq.moviesstreamingappclient.Model.GetVideoDetails;
import vku.vtq.moviesstreamingappclient.Model.SliderSide;


public class HomeFragment extends Fragment {

    MovieShowAdapter movieShowAdapter;
    DatabaseReference mDatabasereference;
    private List<GetVideoDetails> uploads, uploadslistLatests, uploadsListPopular;
    private List<GetVideoDetails> actionsmovies, sportsmovies, comedymovies, romanticmovies, advanturemovies;
    private ViewPager sliderPager;
    private List<SliderSide> uploadsSlider;
    private TabLayout indicator, tabmoviesactions;
    private RecyclerView MoviesRv, moviesRvWeek, tab;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    LinearLayoutManager layoutManager;
    String CALL_PHONE;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tabmoviesactions = v.findViewById(R.id.tabActionMovies);
        sliderPager = v.findViewById(R.id.slider_pager);
        indicator = v.findViewById(R.id.indicator);
        moviesRvWeek = v.findViewById(R.id.rv_movies_week);
        MoviesRv = v.findViewById(R.id.rv_movies);
        tab = v.findViewById(R.id.tabrecyler);


        addAllMovies();


        return v;




    }
    private void addAllMovies(){
    uploads = new ArrayList<>();
    uploadslistLatests = new ArrayList<>();
    uploadsListPopular = new ArrayList<>();
    actionsmovies = new ArrayList<>();
    advanturemovies = new ArrayList<>();
    comedymovies = new ArrayList<>();
    sportsmovies = new ArrayList<>();
    romanticmovies = new ArrayList<>();
    uploadsSlider = new ArrayList<>();

    mDatabasereference = FirebaseDatabase.getInstance().getReference("videos");

        mDatabasereference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                GetVideoDetails upload = postSnapshot.getValue(GetVideoDetails.class);
                SliderSide slide = postSnapshot.getValue(SliderSide.class);

                upload.setId(postSnapshot.getKey());

                if (upload.getVideo_type().equals("Latest Movies")){
                    uploadslistLatests.add(upload);
                }
                if (upload.getVideo_type().equals("Best Popular Movies")){
                    uploadsListPopular.add(upload);

                }
                if (upload.getVideo_category().equals("Action")){
                    actionsmovies.add(upload);
                }
                if (upload.getVideo_category().equals("Adventure")){
                    advanturemovies.add(upload);
                }
                if (upload.getVideo_category().equals("Comedy")){
                    comedymovies.add(upload);

                }
                if (upload.getVideo_category().equals("Romantic")){
                    romanticmovies.add(upload);

                }
                if (upload.getVideo_category().equals("Sports")){
                    sportsmovies.add(upload);

                }
                if (upload.getVideo_slide().equals("Slide Movies")){
                    uploadsSlider.add(slide);
                }

                uploads.add(upload);

            }

            iniSlider();
            iniPopularMovies();
            iniWeekMovies();
            moviesViewTab();
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


    });

}
    private void iniSlider() {

        SliderPagerAdapterNew adapterNew = new SliderPagerAdapterNew(getContext(), uploadsSlider);
        sliderPager.setAdapter(adapterNew);
        adapterNew.notifyDataSetChanged();
        //setup thoi gian

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragment.SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager(sliderPager, true);

    }

    private void iniWeekMovies(){
        movieShowAdapter = new MovieShowAdapter(getContext(), uploadslistLatests);
        moviesRvWeek.setAdapter(movieShowAdapter);
        moviesRvWeek.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }

    private void iniPopularMovies(){
        movieShowAdapter = new MovieShowAdapter(getActivity(),uploadsListPopular);
        MoviesRv.setAdapter(movieShowAdapter);
        MoviesRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }

    private void moviesViewTab(){
        getActionsMovies();
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Action"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Adventure"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Comedy"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Romantic"));
        tabmoviesactions.addTab(tabmoviesactions.newTab().setText("Sports"));


        tabmoviesactions.setTabGravity(TabLayout.GRAVITY_FILL);
        tabmoviesactions.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

        tabmoviesactions.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getActionsMovies();
                        getActionsMovies();
                        break;
                    case 1:
                        getAdvantureMovies();
                        break;
                    case 2:
                        getComedyMovies();
                        break;
                    case 3:
                        getRomanticMovies();
                        break;
                    case 4:
                        getSportsMovies();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onMoviesClick(GetVideoDetails movie, ImageView imageView) {

        Intent in = new Intent(getContext(), MovieDetailsActivity.class);
        in.putExtra("title", movie.getVideo_name());
        in.putExtra("imgURL", movie.getVideo_thumb());
        in.putExtra("imgCover",movie.getVideo_thumb());
        in.putExtra("movieDetails",movie.getVideo_description());
        in.putExtra("movieUrl",movie.getVideo_url());
        in.putExtra("movieCategory",movie.getVideo_category());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,"sharedName");
        startActivity(in,options.toBundle());
    }

    public class SliderTimer extends TimerTask {
        public void run(){
          getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderPager.getCurrentItem()<uploadsSlider.size()-1){

                        sliderPager.setCurrentItem(sliderPager.getCurrentItem()+1);
                    }else {
                        sliderPager.setCurrentItem(0);
                    }
                }
            });
        }
    }



    private void getActionsMovies(){
        movieShowAdapter = new MovieShowAdapter(getActivity(), actionsmovies);
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }

    private void getSportsMovies(){
        movieShowAdapter = new MovieShowAdapter(getActivity(), sportsmovies);
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }

    private void getRomanticMovies(){
        movieShowAdapter = new MovieShowAdapter(getActivity(), romanticmovies );
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }

    private void getComedyMovies(){
        movieShowAdapter = new MovieShowAdapter(getActivity(), comedymovies );
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }

    private void getAdvantureMovies(){
        movieShowAdapter = new MovieShowAdapter(getActivity(), advanturemovies );
        tab.setAdapter(movieShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        movieShowAdapter.notifyDataSetChanged();
    }



}