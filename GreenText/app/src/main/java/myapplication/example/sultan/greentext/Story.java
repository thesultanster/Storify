package myapplication.example.sultan.greentext;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;


import java.util.ArrayList;
import java.util.List;


public class Story extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private StoryRecyclerAdapter adapter;
    private android.support.v7.widget.Toolbar toolbar;

    ParseObject myStory;
    ParseUser currentUser;
    ParseObject newSentence;
    ParseObject currentStory;

    List<String> sentences;

    //List<ParseObject> sentenceList;
    String objectId;
    TextView title;
    TextView newSentenceButton;
    EditText newSentenceText;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);



        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.storySwipeReresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        title = (TextView) findViewById(R.id.storyTitle);
        newSentenceButton = (TextView) findViewById(R.id.newSentenceButton);
        newSentenceText = (EditText) findViewById(R.id.newSentenceText);

        // Set RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.storyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sentences = new ArrayList<>();


        newSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSentence();
                //GetStory();
            }
        });

        if(getIntent().getExtras() != null)
            objectId = getIntent().getExtras().getString("objectId");


        //Retrieve Story data
        GetStory();


    }


    public  List<StoryRecyclerInfo> getData(){
        List<StoryRecyclerInfo> data = new ArrayList<>();

       /*
        String[] sentence = { "I was etering anew one", "yeah"};
        for(int i=0; i < sentence.length; i++)
        {
            StoryRecyclerInfo current = new StoryRecyclerInfo();
            current.sentence = sentence[i];
            data.add(current);
        }

*/
        Log.d("SIZE",String.valueOf(sentences.size()));
        for (String theSentence : sentences) {
            StoryRecyclerInfo current = new StoryRecyclerInfo();
            current.sentence = theSentence;
            data.add(current);


    }



        return data;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void GetStory(){

        // Reset values
        sentences = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    currentStory = object;
                    currentUser = ParseUser.getCurrentUser();

                    //Increment Story View
                    Log.d("NUMBER OF VIEWS", object.getString("views"));
                    int views = Integer.valueOf(object.getString("views"));
                    views++;
                    object.put("views",String.valueOf(views));
                    object.saveInBackground();

                    //Increment User views
                    if(currentUser.getString("totalViews") == null)
                        views = 0;
                    else
                        views = Integer.valueOf(currentUser.getString("totalViews"));

                    views++;
                    currentUser.put("totalViews",String.valueOf(views));
                    currentUser.saveInBackground();

                    //Set Title
                    title.setText(object.getString("title"));

                    ParseQuery<ParseObject> commentQuery = ParseQuery.getQuery("Sentence");
                    commentQuery.whereEqualTo("story", object);
                    commentQuery.orderByAscending("createdAt");
                    commentQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> sentenceList, ParseException e) {
                            // commentList now contains the last ten comments, and the "post"
                            // field has been populated. For example:

                            for (ParseObject sentence : sentenceList) {
                                Log.d("sentence", sentence.getString("sentence"));
                                sentences.add(sentence.getString("sentence"));
                            }

                           adapter = new StoryRecyclerAdapter(Story.this, getData());
                           recyclerView.setAdapter(adapter);

                        }
                    });
                } else {

                }
            }
        });








        if(mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

    }

    void AddSentence()
    {
        currentUser = ParseUser.getCurrentUser();


        newSentence = new ParseObject("Sentence");
        newSentence.put("sentence",newSentenceText.getText().toString());
        newSentence.put("order",1);
        newSentence.put("author",currentUser);
        newSentence.put("story",currentStory);
        newSentence.saveInBackground();

        ParseRelation<ParseObject> storyRelationContributors = currentStory.getRelation("contributors");
        storyRelationContributors.add(currentUser);
        currentStory.saveInBackground();


        GetStory();
        //sentences.add(newSentenceText.getText().toString());
        //adapter = new StoryRecyclerAdapter(Story.this, getData());
        //recyclerView.setAdapter(adapter);

        newSentenceText.setText("");

    }

    @Override
    public void onRefresh() {

        GetStory();
    }
}
