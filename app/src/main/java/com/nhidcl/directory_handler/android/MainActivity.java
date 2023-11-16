package com.nhidcl.directory_handler.android;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.directory2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button1,register;
    ImageButton helperph,helperem;
    EditText id;
    //Query query1;
    TextInputEditText pswd;
    public static String EMAIL,PIN;
    public static Class parent;
    public static boolean admin=false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteRef=db.collection("contacts");
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

    ArrayList<String[]> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = MainActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        EMAIL = sharedPref.getString("email", "");
        Log.d("sharedpref", sharedPref.getString("email", ""));
        /*if (!sharedPref.getString("email", "").equals("")) {
            Log.d("in if", "in if");
            Intent intent = new Intent(MainActivity.this, LoggedInUser.class);
            startActivity(intent);
            finish();
        }*/
        setContentView(R.layout.activity_main);
        register = findViewById(R.id.link);
        button1 = findViewById(R.id.button);
        helperem = findViewById(R.id.emailh);
        helperph = findViewById(R.id.phoneh);
        id = findViewById(R.id.id);
        pswd = findViewById(R.id.pswd);
        id.setText(sharedPref.getString("email", "") == null ? "" : sharedPref.getString("email", ""));

        helperem.setOnClickListener(new View.OnClickListener() {
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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMAIL = id.getText().toString();
                PIN = pswd.getText().toString();
                Query query = noteRef.whereEqualTo(Email, EMAIL).whereEqualTo("PIN", PIN);
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Wrong ID or Pin", Toast.LENGTH_SHORT).show();
                        } else {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Log.e("Type", Type);
                                if (doc.getData().get("TYPE").toString().equals(new String("admin"))) {
                                    MainActivity.admin = true;
                                } else {
                                    MainActivity.admin = false;
                                }
                            }
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("email", EMAIL);
                            editor.apply();
                            Log.d("mainActivity", sharedPref.getString("email", ""));
                            Log.d("MainActivity admin", MainActivity.admin ? "true" : "false");
                            //Intent intent = new Intent(MainActivity.this, page1.class);
                            //startActivity(intent);
                            //finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("mainActivity ", "inquery");
                        //Log.e("tage", e.toString());
                    }
                });
            }
        });
    }
}