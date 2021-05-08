package com.example.game.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.game.FileOperation.Authentication;
import com.example.game.Dashboard.IntroActivity;
import com.example.game.R;
import com.example.game.Signup.SignUpActivity;
import com.example.game.UserOperation.Authentication;
import com.example.game.UserOperation.UserController;
import com.example.game.UserOperation.UserControllerManager;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

    }

    /**
     * Event Button for the log in button
     * @param view view
     */
    public void verify(View view){
        String USERNAME = ((EditText) findViewById(R.id.username)).getText().toString().trim();
        String PASSWORD = ((EditText) findViewById(R.id.password)).getText().toString().trim();

        Authentication authentication = new Authentication(this, USERNAME, PASSWORD);
        try{
            authentication.authenticate();
            sendUsername(USERNAME);
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * send the username to the dashboard
     * @param username inputted username
     */
    public void sendUsername(String username) {
        UserController userController = UserControllerManager.getUserController(this, username);


        Intent intent = new Intent(this, IntroActivity.class);
        intent.putExtra("key", username);

        String session = userController.getLastActiveSession();

        if (!session.isEmpty() && !session.equals("null")){
            intent.putExtra("sessionExist", true);
        }
        else{
            intent.putExtra("sessionExist", false);
        }

        startActivity(intent);
        finish();
    }

    /**
     * Event handler for the register button
     * @param view view
     */
    public void register(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed(){}
}
