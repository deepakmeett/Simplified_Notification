package com.example.simplifiednotification;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "simplified Coding";
    private static final String CHANNEL_DESC = "simplified Coding Notification";
    EditText email, password;
    Button button;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        button = findViewById( R.id.buttonPanel );
        progressBar = findViewById( R.id.progress_circular );
        auth = FirebaseAuth.getInstance();
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility( View.VISIBLE );
                final String email1 = email.getText().toString();
                final String pass1 = password.getText().toString();
                
                auth.createUserWithEmailAndPassword( email1, pass1 )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startProfileActivity();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        userLogin( email1, pass1 );
                                    } else {
                                        progressBar.setVisibility( View.INVISIBLE );
                                        Toast.makeText( MainActivity
                                                                .this, task.getException()
                                                                .getMessage(), Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            }
                        } );
            }
        } );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT );
            channel.setDescription( CHANNEL_DESC );
            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel( channel );
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
           startProfileActivity();
        }
    }
    
    private void startProfileActivity() {
        Intent intent = new Intent( getApplicationContext(), Main2Activity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
    }

    private void userLogin(String email, String pass) {
        auth.signInWithEmailAndPassword( email, pass )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startProfileActivity();
                        } else {
                            progressBar.setVisibility( View.INVISIBLE );
                            Toast.makeText( MainActivity
                                                    .this, task.getException()
                                                    .getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
    }

   
}
