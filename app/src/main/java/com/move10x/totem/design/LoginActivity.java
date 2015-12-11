package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.multidex.MultiDexApplication;
import android.support.multidex.MultiDex;

import android.support.v7.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.models.User;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;
import com.move10x.totem.services.MiscService;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Move10xActivity {

    private View loginFormView;
    private View progressView;
    private EditText txtUserId;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set context for later use in entire application.
        MiscService.context = this.getApplicationContext();

        txtUserId= (EditText) findViewById(R.id.UserId);
        txtPassword = (EditText) findViewById(R.id.password);

        //Check if user has already registered, if yes redirect to another activity.
        Log.d("login", "Check if profile exists");
        CurrentProfile currentProfile = new CurrentProfileService(this.getApplicationContext()).getCurrentProfile();
        Log.d("login", "Profile: " + currentProfile);
        if(currentProfile.getUserId() == null){
            //Login
            Log.d("login", "User Not Registered.");
        }else{
            //User has already logged in..
            //Redirect to profile page..
            Log.d("login", "User has already Registered.");
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
        }

        txtUserId= (EditText) findViewById(R.id.UserId);
        txtPassword = (EditText) findViewById(R.id.password);
        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    initLogin();
                    return true;
                }
                return false;
            }
        });

        Button loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    /**
     * Validate Login form and authenticate.
     */
    public void initLogin() {

        txtUserId.setError(null);
        txtPassword.setError(null);

        String userId = txtUserId.getText().toString();
        String password = txtPassword.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && password.length() > 25) {
            txtPassword.setError(getString(R.string.invalid_password));
            focusView = txtPassword;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(userId)) {
            txtUserId.setError(getString(R.string.field_required));
            focusView = txtUserId;
            cancelLogin = true;
        } else if (userId.length() > 20) {
            txtUserId.setError(getString(R.string.invalid_userId));
            focusView = txtUserId;
            cancelLogin = true;
        }

        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
            showProgress(true);

            Log.d("login", "Validating user");
            //Request Parameters.
            RequestParams loginParameters = new RequestParams();
            loginParameters.put("username", userId);
            loginParameters.put("password", password);
            //Async Login
            AsyncHttpService.post(Url.apiBaseUrl + "?tag=vrm_employee_login", loginParameters, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    Log.d("loginActivity", "Parsing login response: " + response.toString());
                    try {

                        if (response.getString("success") != null && response.getString("success").equals("1")
                                && response.getString("isUserValid").equals("1")) {
                            //Valid user
                            Log.d("loginActivity", "valid user.");
                            User user = User.decodeJson(response.getJSONObject("user"));
                            Log.d("loginActivity", "User: " + user.toString());
                            //Store profile in sqlite.
                            CurrentProfile profile = new CurrentProfile(user.UId, user.FirstName, user.LastName, user.FirstName + " " + user.LastName, user.PhoneNo, user.Department, user.ImagePath);
                            new CurrentProfileService(getApplicationContext()).CreateProfile(profile);
                            //Redirect to main activity.
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else if (response.getString("success") != null && response.getString("success").equals("1")
                                && !response.getString("isUserValid").equals("1")) {
                            Log.d("loginActivity", "invalid user.");
                            //Invalid user
                            txtUserId.setError("Invalid username or password.");
                        }
                    } catch (JSONException ex) {

                    }

                    showProgress(false);
                }

               /* @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    JSONObject firstEvent = timeline.get(0);
                    String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    System.out.println(tweetText);
                }*/
            });
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
