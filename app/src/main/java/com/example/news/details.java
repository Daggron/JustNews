package com.example.news;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Bundle  extras =getIntent().getExtras();
        TextView title = findViewById(R.id.title);
        title.setText(extras.getString("Key_Title"));

        String image_Url=extras.getString("Key_Image");
        new DownloadImageTask((ImageView) findViewById(R.id.Image))
                .execute(image_Url);

        TextView date = findViewById(R.id.Date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String dateInString = extras.getString("Key_Date");

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss a");

        Date dates=null;
        try {

            dates = formatter.parse(dateInString.replaceAll("Z$", "+0000"));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        date.setText(format.format(dates));

        TextView author = findViewById(R.id.Author);
        if((extras.getString("Key_Author").compareTo("null"))==0){
            author.setText(R.string.unkown_author);
        }
        else {
            author.setText(extras.getString("Key_Author"));
        }

        Button button = findViewById(R.id.readmore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(extras.getString("Key_Url")));
                startActivity(i);
            }
        });

    }

    private  class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
