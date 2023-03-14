package vku.vtq.moviesstreamingappclient.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vku.vtq.moviesstreamingappclient.Model.GetVideoDetails;
import vku.vtq.moviesstreamingappclient.MovieDetailsActivity;
import vku.vtq.moviesstreamingappclient.R;

public class MovieShowAdapter extends RecyclerView.Adapter<MovieShowAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetVideoDetails> uploads;


    public MovieShowAdapter(Context mContext, List<GetVideoDetails> uploads) {
        this.mContext = mContext;
        this.uploads = uploads;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_item_new,parent,false);



        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieShowAdapter.MyViewHolder holder, int position) {
      GetVideoDetails getVideoDetails = uploads.get(position);
      holder.tvTitle.setText(getVideoDetails.getVideo_name());
        Glide.with(mContext).load(getVideoDetails.getVideo_thumb()).into(holder.ImgMovie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra("videoUri1",uploads.get(position).getVideo_url());
                intent.putExtra("videoUri",uploads.get(position).getVideo_url());
                intent.putExtra("videocat",uploads.get(position).getVideo_category());
                intent.putExtra("imgCover",uploads.get(position).getVideo_thumb());
                intent.putExtra("imgURL",uploads.get(position).getVideo_thumb());
                intent.putExtra("movieDetails",uploads.get(position).getVideo_name());
                intent.putExtra("movieUrl",uploads.get(position).getVideo_url());
                intent.putExtra("movieCategory",uploads.get(position).getVideo_category());
                intent.putExtra("title",uploads.get(position).getVideo_description());

               mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ImgMovie;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.item_movie_title);
            ImgMovie = itemView.findViewById(R.id.item_movies_img);
            container = itemView.findViewById(R.id.container);



        }
    }
}
