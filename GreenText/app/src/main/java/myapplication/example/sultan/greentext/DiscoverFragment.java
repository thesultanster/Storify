package myapplication.example.sultan.greentext;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class DiscoverFragment extends Fragment implements DiscoveryRecyclerAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private DiscoveryRecyclerAdapter adapter;
    TextView newStoryButton;
    List<ParseObject> objectList;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    FetchData fetchData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View layout = inflater.inflate(R.layout.activity_discover_fragment, container, false);

        LinearLayout mainLayout=(LinearLayout) layout.findViewById(R.id.discoveryBottomBar);
        mainLayout.setVisibility(LinearLayout.GONE);

        recyclerView = (RecyclerView) layout.findViewById(R.id.discoverDrawerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.discoverSwipeReresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        //New Story button
        newStoryButton = (TextView) layout.findViewById(R.id.newStoryButton);
        newStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), NewStory.class);
                startActivity(intent);
            }
        });

        // Async Task to get data
        new FetchData().execute("");

        return layout;

    }

    @Override
    public void itemClicked(View view, int position) {

        Intent intent = new Intent(getActivity(),Story.class);
        intent.putExtra("objectId", objectList.get(position).getObjectId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        new FetchData().execute("");
    }

    private class FetchData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
            try {
                objectList = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {


            List<DiscoveryRecyclerInfo> data = new ArrayList<>();

            for (ParseObject story : objectList) {
                Log.d("STORY IDS",story.getObjectId());
                DiscoveryRecyclerInfo current = new DiscoveryRecyclerInfo();
                //current.iconId = story.getString("title");
                current.title = story.getString("title");
                current.thumbsUp = story.getString("likes");
                current.thumbsDown = story.getString("dislikes");
                current.favorites = story.getString("favorites");
                current.views = story.getString("views");
                data.add(current);
            }


            adapter = new DiscoveryRecyclerAdapter(getActivity(), data);
            adapter.setClickListener(DiscoverFragment.this);
            recyclerView.setAdapter(adapter);

            if(mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}