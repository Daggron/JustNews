package com.example.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




public class newsAdapter extends ArrayAdapter<news> {
        public newsAdapter(Context context, int resource, ArrayList<news> news) {
            super(context, 0,news);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= LayoutInflater.from(getContext()).inflate(R.layout.news,parent,false);
            }
            news eachnews=getItem(position);

            TextView title=(TextView) convertView.findViewById(R.id.title);
            title.setText(eachnews.getTitle());

            TextView tag = convertView.findViewById(R.id.Source);
            tag.setText(eachnews.getTag());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            String dateInString = eachnews.getDate();

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss a");

            Date dates=null;
            try {

                dates = formatter.parse(dateInString.replaceAll("Z$", "+0000"));


            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView date = convertView.findViewById(R.id.Date);
            date.setText((format.format(dates)));

            TextView author = convertView.findViewById(R.id.author);
            if(eachnews.getAuthor()=="null"){
                author.setText("Unkown author");
            }
            else {
                author.setText(eachnews.getAuthor());
            }

            /*ImageView imageView = convertView.findViewById(R.id.Image);

            if (eachnews.getImage() != null) {
                new DownloadImageTask(imageView).execute(eachnews.getImage());
            }
            else{
                imageView.setImageResource(R.drawable.steve);
            }

            */

            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backdrop));

            return convertView;
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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


