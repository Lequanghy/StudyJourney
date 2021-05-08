package com.example.game.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.game.FileOperation.Authentication;
import com.example.game.Login.LogInActivity;
import com.example.game.R;
import com.example.game.UserOperation.Authentication;
import com.example.game.UserOperation.UserCreation;

public class SignUpActivity extends AppCompatActivity{

  private EditText USERNAME;
  private EditText PASSWORD;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    USERNAME = ((EditText) findViewById(R.id.username));
    PASSWORD = ((EditText) findViewById(R.id.password));
  }

  /**
   * Event handler for the register button
   * @param view view
   */
  public void register(View view) {
    Authentication authentication =
        new Authentication(this, USERNAME.getText().toString(), PASSWORD.getText().toString());

    if (!authentication.doesUserExist()) {
      try {
        UserCreation.InitializeNewUser(
            this, USERNAME.getText().toString(), PASSWORD.getText().toString());

        redirectLogin(view);
        Toast.makeText(this, "Register Successful!!!", Toast.LENGTH_SHORT).show();

      } catch (Exception ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
      }
      return;
    }
    Toast.makeText(this, "USER NAME TAKEN!!!", Toast.LENGTH_SHORT).show();
  }

  /**
   * Event handler for the login Button
   * @param view view
   */
  public void redirectLogin(View view) {
    Intent intent = new Intent(this, LogInActivity.class);
    startActivity(intent);
    finish();
  }

  /**
   * Disable back button
   */
  @Override
  public void onBackPressed(){}
}
