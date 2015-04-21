package myapplication.example.sultan.greentext;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class NewStory extends ActionBarActivity {


    EditText title;
    EditText firstSentence;
    TextView createStoryButton;
    Intent intent;

    ParseUser currentUser;
    ParseObject sentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        intent = new Intent( NewStory.this, Story.class);

        final ParseObject newStory = new ParseObject("Story");

        title = (EditText) findViewById(R.id.newStoryTitle);
        firstSentence = (EditText) findViewById(R.id.newStorySentence);
        createStoryButton =(TextView) findViewById(R.id.createStoryButton);
        createStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Update to database
                currentUser = ParseUser.getCurrentUser();

                sentence = new ParseObject("Sentence");
                sentence.put("sentence",firstSentence.getText().toString());
                sentence.put("order",1);
                sentence.put("author",currentUser);
                sentence.put("story",newStory);
                sentence.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                        } else {
                            Toast.makeText(getApplicationContext(), "Save fail",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                newStory.put("title", title.getText().toString());
                newStory.put("size","1");
                newStory.put("views","0");
                newStory.put("favorites","0");
                newStory.put("likes","0");
                newStory.put("dislikes","0");

                ParseRelation<ParseObject> storyRelationContributors = newStory.getRelation("contributors");
                storyRelationContributors.add(currentUser);
                newStory.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getApplicationContext(), "Saved",
                                Toast.LENGTH_LONG).show();

                        Toast.makeText(getApplicationContext(), newStory.getObjectId(),
                                Toast.LENGTH_LONG).show();

                        intent.putExtra("objectId",newStory.getObjectId());

                        startActivity(intent);
                    }
                });



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_story, menu);
        return true;
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
}
