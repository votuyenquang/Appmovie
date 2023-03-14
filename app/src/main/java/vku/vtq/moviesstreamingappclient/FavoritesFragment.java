package vku.vtq.moviesstreamingappclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

import vku.vtq.moviesstreamingappclient.Adapter.MovieShowAdapter;
import vku.vtq.moviesstreamingappclient.Model.GetVideoDetails;


public class FavoritesFragment extends Fragment {

    RecyclerView recyclerView;
    MovieShowAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<GetVideoDetails> arrayList;
    FirebaseAuth mAuth;
    GetVideoDetails movies;
    TextView txt_trongfav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

      final View view = inflater.inflate(R.layout.fragment_favorites, container, false);

      recyclerView= view.findViewById(R.id.phim_yeuthich);
      layoutManager = new LinearLayoutManager(getContext());
      txt_trongfav = view.findViewById(R.id.txt_trongfavorites);
      mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String uid= currentUser.getUid();

        arrayList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Yeuthich").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot data : snapshot.getChildren()){
                    movies = data.getValue(GetVideoDetails.class);
                    movies.setId(data.getKey());
                    arrayList.add(movies);
                }

                if (arrayList.size()<=0){
                    txt_trongfav.setText("Chưa thêm các bộ phim yêu thích...");
                }

                 adapter = new MovieShowAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Load Data thất bại", Toast.LENGTH_LONG).show();

            }
        });
      return view;
    }
}