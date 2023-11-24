package com.nhidcl.directory_handler.android;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button1;
    ImageButton helperph,helperem;
    EditText id;
    //Query query1;
    TextInputEditText pswd;
    public static String EMAIL,PIN;
    public static Class parent;
    public static boolean admin=false;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference noteRef=db.collection("contacts");
    public static final String Name="NAME";
    public static final String Email="EMAIL";
    public static final String Designation="DESIGNATION";
    public static final String Department="DEPARTMENT";
    public static final String State="STATE";
    public static final String Address="ADDRESS";
    public static final String Mobile="MOBILE";
    public static final String Landline="LANDLINE";
    public static final String Password="PASSWORD";
    public static final String Type="TYPE";
    public static final String Pin="PIN";
    FirebaseAuth auth;

    ArrayList<String[]> list;

    /*void loginUser() {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(EMAIL, PIN).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success", "signInWithEmail:success");
                    FirebaseUser user = auth.getCurrentUser();
                    Intent i = new Intent(MainActivity.this, LoggedIn.class);
                    startActivity(i);
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("failure", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }*/
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = MainActivity.this;
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        helperem = findViewById(R.id.emailh);
        helperph = findViewById(R.id.phoneh);
        /*id = findViewById(R.id.id);
        pswd = findViewById(R.id.pswd);*/

        helperem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //emailIntent.putExtra(Intent.EXTRA_, " ");
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sinha.shreya@nhidcl.com"});
                emailIntent.putExtra(Intent.EXTRA_CC, "");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Problem with NHIDCL Directory");
                emailIntent.putExtra(Intent.EXTRA_TEXT, " ");
                startActivity(emailIntent);
            }
        });
        helperph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "01123461600"));
                startActivity(intent);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*EMAIL=id.getText().toString().trim();
                PIN=pswd.getText().toString().trim();
                loginUser();*/
                /*if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Intent i = new Intent(MainActivity.this, LoggedIn2.class);
                    startActivity(i);
                    finish();
                }
                else{

                }*/
                List<AuthUI.IdpConfig> provider = Arrays.asList(
                  new AuthUI.IdpConfig.GoogleBuilder().build()
                );
                Intent i = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider).setLogo(R.drawable.logo2).build();
                signInLauncher.launch(i);
            }
        });
    }
}