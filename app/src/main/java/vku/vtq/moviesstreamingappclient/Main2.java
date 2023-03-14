package vku.vtq.moviesstreamingappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Main2 extends AppCompatActivity {
    public androidx.appcompat.widget.Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        String ten = mAuth.getCurrentUser().getEmail();
        String[] part = ten.split("@");
        String tenhienthi = part[0];
        toolbar.setTitle(" \n"+tenhienthi.toUpperCase());
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frament_container, new HomeFragment()).commit();

        //

        //


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.dangxuat){
            mAuth.signOut();
            Intent intent = new Intent(Main2.this, MainActivity.class);
            startActivity(intent);
        } else
         if(id==R.id.timkiem){

            Intent intent = new Intent(Main2.this, SearchMovieActivity.class);
            startActivity(intent);
        }


        else if(id==R.id.thongtincanhan){
            Intent intent = new Intent(Main2.this, ThongTinCaNhan.class);
            startActivity(intent);
        }
        else if (id==R.id.action_doimatkhau){
            Intent intent = new Intent(Main2.this, DoiMatKhau.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedfragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home :
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_favorites :
                            selectedfragment = new FavoritesFragment();
                            break;


                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    transaction.replace(R.id.frament_container,selectedfragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
            };


    public void setSupportActionBar(Toolbar toolbar) {


    }
}
