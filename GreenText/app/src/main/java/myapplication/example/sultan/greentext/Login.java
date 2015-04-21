package myapplication.example.sultan.greentext;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Login extends ActionBarActivity {


    EditText username;
    EditText password;
    EditText email;
    Button loginButton;
    Button signUnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Pz49eN1Hz0nZRlDvokXahaoX45haXXpYtaHpzJ49", "rdOAtXCGjbHcZZ6SmgSCyo0ikHz5PORLzBOckFsa");

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Intent intent = new Intent( getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            // show the signup or login screen
        }

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUnButton = (Button) findViewById(R.id.signUpButton);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { loginUser(); }
        });


        signUnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { signUpUser(); }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void loginUser()
    {

        Toast.makeText(getApplicationContext(), "Sign In",
                Toast.LENGTH_LONG).show();

        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Toast.makeText(getApplicationContext(), "Login SUCCESS",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login FAIL",
                            Toast.LENGTH_LONG).show();
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });

    }

    private void signUpUser()
    {
        Toast.makeText(getApplicationContext(), "Sign Up",
                Toast.LENGTH_LONG).show();

        ParseUser user = new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.put("totalViews","0");
        user.setEmail(email.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(getApplicationContext(), "Sign Up SUCCESS",
                            Toast.LENGTH_LONG).show();
                    loginUser();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Toast.makeText(getApplicationContext(), "Sign Up FAIL",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
