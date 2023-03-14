package vku.vtq.moviesstreamingappclient.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vku.vtq.moviesstreamingappclient.Model.SearchModel;
import vku.vtq.moviesstreamingappclient.MovieDetailsActivity;
import vku.vtq.moviesstreamingappclient.R;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable
{

    private Context mContext;
    private List<SearchModel> uploads;
    private List<SearchModel> getUploads;




    public SearchAdapter(Context mContext, List<SearchModel> uploads) {
        this.mContext = mContext;
        this.uploads = uploads;
        this.getUploads = uploads;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.singlerow, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {

        SearchModel searchModel = uploads.get(position);
        holder.name.setText(searchModel.getVideo_name());
        holder.cou.setText(searchModel.getVideo_category());
        holder.email.setText(searchModel.getVideo_description());
        Glide.with(mContext).load(searchModel.getVideo_thumb()).into(holder.ImgMovie);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    uploads = getUploads;
                } else {
                    List<SearchModel> list = new ArrayList<>();
                    for (SearchModel searchModel : getUploads){
                        if (searchModel.getVideo_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(searchModel);
                        }
                    }
                    uploads = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = uploads;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                uploads = (List<SearchModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, cou, email;
        ImageView ImgMovie;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nametext);
            cou = itemView.findViewById(R.id.coursetext);
            email = itemView.findViewById(R.id.emailtext);
            ImgMovie = itemView.findViewById(R.id.img1);
            container = itemView.findViewById(R.id.container);
        }
    }

}
