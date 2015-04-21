package myapplication.example.sultan.greentext;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


// the <> contains my custom viewholder; MyViewHolder, and this class will return MyViewHolder as well
public class DiscoveryRecyclerAdapter extends RecyclerView.Adapter<DiscoveryRecyclerAdapter.MyViewHolder>{

    // emptyList takes care of null pointer exception
    List<DiscoveryRecyclerInfo> data = Collections.emptyList();
    LayoutInflater inflator;
    Context context;
    private ClickListener clickListener;

    public DiscoveryRecyclerAdapter(Context context, List<DiscoveryRecyclerInfo> data){
        this.context=context;
        inflator = LayoutInflater.from(context);
        this.data = data;
    }

    // Called when the recycler view needs to create a new row
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.discovery_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // Setting up the data for each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // This gives us current information list object
        DiscoveryRecyclerInfo current = data.get(position);

        holder.title.setText(current.title);
        holder.favorites.setText(current.favorites);
        holder.thumbsUp.setText(current.thumbsUp);
        holder.thumbsDown.setText(current.thumbsDown);
        holder.views.setText(current.views);
        //holder.image.setImageResource(current.iconId);


    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    // Created my custom view holder
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView thumbsUp;
        TextView thumbsDown;
        TextView favorites;
        TextView views;
        //ImageView image;


        // itemView will be my own custom layout View of the row
        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            //Link the objects
            title = (TextView) itemView.findViewById(R.id.discoveryItemTitle);
            thumbsUp = (TextView) itemView.findViewById(R.id.thumbsUpCount);
            thumbsDown = (TextView) itemView.findViewById(R.id.thumbsDownCount);
            favorites = (TextView) itemView.findViewById(R.id.storyLoveCount);
            views = (TextView) itemView.findViewById(R.id.storyViewCount);
            //image = (ImageView) itemView.findViewById(R.id.discoveryItemImage);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, Story.class));
            if(clickListener != null)
            {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener
    {
        public void itemClicked(View view, int position);
    }
}
