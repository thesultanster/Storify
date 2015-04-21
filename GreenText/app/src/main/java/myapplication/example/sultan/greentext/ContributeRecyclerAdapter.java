package myapplication.example.sultan.greentext;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


// the <> contains my custom viewholder; MyViewHolder, and this class will return MyViewHolder as well
public class ContributeRecyclerAdapter extends RecyclerView.Adapter<ContributeRecyclerAdapter.MyViewHolder>{

    // emptyList takes care of null pointer exception
    List<ContributeRecyclerInfo> data = Collections.emptyList();
    LayoutInflater inflator;

    public ContributeRecyclerAdapter(Context context, List<ContributeRecyclerInfo> data){
        inflator = LayoutInflater.from(context);
        this.data = data;
    }

    // Called when the recycler view needs to create a new row
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflator.inflate(R.layout.contribute_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // Setting up the data for each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // This gives us current information list object
        ContributeRecyclerInfo current = data.get(position);

        holder.title.setText(current.title);
        holder.image.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Created my custom view holder
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;


        // itemView will be my own custom layout View of the row
        public MyViewHolder(View itemView) {
            super(itemView);

            //Link the objects
            title = (TextView) itemView.findViewById(R.id.contributeItemTitle);
            image = (ImageView) itemView.findViewById(R.id.contributeItemImage);
        }
    }
}
