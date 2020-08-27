package com.example.mcqapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuizActivity extends AppCompatActivity {
    public static ResultDatabase resultDatabase;
    private AppBarConfiguration mAppBarConfiguration;
    //My changes
    FirebaseAuth mAuth;
    public TextView email;
    public TextView name;
    public ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Set user name and email
//        Toast.makeText(this, "Name Id is : "+R.id.names+"\n Email Id is: "+R.id.email, Toast.LENGTH_LONG).show();
        mAuth=FirebaseAuth.getInstance();
        View headerLayout =navigationView.getHeaderView(0);
        name = headerLayout.findViewById(R.id.names);
//        name=findViewById(R.id.names);
        photo=headerLayout.findViewById(R.id.photo);
        email = headerLayout.findViewById(R.id.emails);
        if(mAuth.getCurrentUser()==null){
            //login

        }
        else {
            FirebaseUser user= mAuth.getCurrentUser();
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            ImageDownloader task = new ImageDownloader();
            Bitmap myImage;
            try {
                myImage=task.execute(user.getPhotoUrl().toString()).get();
                photo.setImageBitmap(myImage);
            }
            catch (Exception e){
            }
        }
        resultDatabase= Room.databaseBuilder(getApplicationContext(),ResultDatabase.class,"resulto").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    class ImageDownloader extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream in= urlConnection.getInputStream();
                Bitmap myBitmap= BitmapFactory.decodeStream(in);
                return myBitmap;
            }
            catch (Exception e){
                Log.e("Pranshu Error: ",e.toString());
                return null;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent= new Intent(QuizActivity.this,LoginActivity.class);
            QuizActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
