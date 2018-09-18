package www.petapp.com.thepet.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import www.petapp.com.thepet.model.User;
import www.petapp.com.thepet.R;
import www.petapp.com.thepet.util.InputVerificationTool;

public class RegistrationActivity extends AppCompatActivity {

    private final String TAG = "RegistrtationActivity";
    private final int MIN_LENGTH_PASSWORD = 6;
    private final int MAX_LENGTH_PASSWORD = 20;
    private final String USERNAME_EMPTY = "Username cannot be empty";
    private final String USERNAME_TOO_SHORT = "Username is too short";
    private final String FIRST_NAME_EMPTY = "First name cannot be empty";
    private final String LAST_NAME_EMPTY = "Last name cannot be empty";
    private final String PASSWORD_NOT_MATCH = "Passwords are not match";
    private final String PASSWORD_TOO_SHORT = "Password is too short";
    private final String EMAIL_INVALID = "Email address is invalid";
    private final String INVALID_CHARACTER = "Invalid character contained";
    private final String PASSWORD_TOO_SIMPLE = "Password is too simple";
    private final String USERNAME_EXIST = "Username has already exist";
    private final String EMAIL_EXIST = "Email has already registered";

    // for firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // set up the layout
    private EditText mEmail;
    private EditText mName;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;

    // for input value
    private Context mContext;
    private String email;
    private String name;
    private String password;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mRegister = (Button) findViewById(R.id.btn_register);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        mName = (EditText) findViewById(R.id.input_name);
        mContext = RegistrationActivity.this;
        mUser = new User();
        Log.d(TAG, "onCreate: started");

        initProgressBar();
        setupFirebaseAuth();
        init();
    }

    private void init() {

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mEmail.getText().toString();
                name = mName.getText().toString();
                password = mPassword.getText().toString();

                if (!InputVerificationTool.isEmail(email)) {
                    mEmail.setError(EMAIL_INVALID);
                }


                if (checkInputs(email, name, password, mConfirmPassword.getText().toString())) {
                    if(doStringsMatch(password, mConfirmPassword.getText().toString())){
                        registerNewEmail(email, password);
                    }else{
                        Toast.makeText(mContext, "passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    /**
     * Return true if @param 's1' matches @param 's2'
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    /**
     * Checks all the input fields for null
     * @param email
     * @param username
     * @param password
     * @return
     */
    private boolean checkInputs(String email, String username, String password, String confirmPassword){
        Log.d(TAG, "checkInputs: checking inputs for null values");
        if(email.equals("") || username.equals("") || password.equals("") || confirmPassword.equals("")){
            Toast.makeText(mContext, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void initProgressBar(){
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


        /*
        ----------------------------- Firebase setup ---------------------------------
     */


    private void setupFirebaseAuth(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is authenticated
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");

                }
                // ...
            }
        };

    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password){

        showProgressBar();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "registerNewEmail: onComplete: " + task.isSuccessful());

                        if (task.isSuccessful()){
                            //add user details to firebase database
                            addNewUser();
                            //send email verificaiton
                            sendVerificationEmail();


                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Someone with that email already exists",
                                    Toast.LENGTH_SHORT).show();
                            hideProgressBar();

                        }
                        hideProgressBar();
                        // ...
                    }
                });
    }

    /**
     * Adds data to the node: "users"
     */
    public void addNewUser(){

        //add data to the "users" node
        String userid = mAuth.getCurrentUser().getUid();

        Log.d(TAG, "addNewUser: Adding new User: \n user_id:" + userid);
        mUser.setName(name);
        mUser.setUserId(userid);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

//        insert into users node
        reference.child(getString(R.string.node_users))
                .child(userid)
                .setValue(mUser);

        //nothing add to database
        Log.e(TAG, String.valueOf(reference.child(getString(R.string.node_users))));

        mAuth.signOut();
        redirectLoginScreen();
    }

    /**
     * sends an email verification link to the user
     */
    public void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }
                            else{
                                Toast.makeText(mContext, "couldn't send email", Toast.LENGTH_SHORT).show();
                                hideProgressBar();
                            }
                        }
                    });
        }

    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
