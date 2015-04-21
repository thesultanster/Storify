package myapplication.example.sultan.greentext;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

        TextView likesText;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);

            likesText = (TextView) view.findViewById(R.id.likesText);
            ParseUser currentUser = ParseUser.getCurrentUser();
            likesText.setText(currentUser.getString("totalViews"));

            return view;
        }
    }
