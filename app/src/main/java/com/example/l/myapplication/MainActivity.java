package com.example.l.myapplication;


import java.io.InputStream;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String PATH = "/data/data/pack.coderzheaven/";

    Button refresh,save;
    ImageView pic;
    Notification notification;
    AlertDialog.Builder alert;
    NotificationManager notifManager;
    Notification.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new Notification.Builder(MainActivity.this);
        builder.setSmallIcon(R.drawable.vasavi).setContentTitle("ERROR!").setContentText("Connect To Internet To Complete This Action");
        notification = builder.getNotification();
        alert = new AlertDialog.Builder(this);
        alert.setTitle("Save the Image to Gallery?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
                if(isStoragePermissionGranted())
                {
                    saveImageToGallery();
                }
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });


        pic=(ImageView)findViewById(R.id.pic);
//        DownloadFromUrl(PATH + "dwn_img.jpg");
        refresh=(Button)findViewById(R.id.refresh);
        save=(Button)findViewById(R.id.save);
        refresh.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Snackbar.make(view, "Updated Image Is Being Fetched !!!", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           if (testConnection()) {
                                               new DownloadImageTask(pic)
                                                       .execute("http://mahikanthnag.net23.net/test.png");
                                               Toast.makeText(MainActivity.this, "Wait for 5 seconds", Toast.LENGTH_LONG).show();

                                               Handler handler = new Handler();
                                               handler.postDelayed(new Runnable() {
                                                   @Override
                                                   public void run() {
//                                                       Toast.makeText(MainActivity.this, "After fetching Screenshot", Toast.LENGTH_SHORT).show();
                                     //                  saveImageToGallery();
                                                       //notifManager.notify(R.drawable.vasavi, notification);
//                                                   addImageToGallery();
                                                       //MediaStore.Images.Media.insertImage(getContentResolver(), pic, yourTitle , yourDescription);
                                                   }
                                               }, 5000);

                                           }
                                           else
                                           {

                                               notifManager.notify(R.drawable.vasavi, notification);
                                               Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                               startActivity(intent);
                                           }
                                       }
                                   }
        );
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.show();

            }
        });


    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

//                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
            return true;
        }


    }
    public boolean testConnection() {

        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                // display error
                return false;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

//    public static void addImageToGallery(final String filePath, final Context context) {
//
//        ContentValues values = new ContentValues();
//
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(MediaStore.MediaColumns.DATA, filePath);
//
//        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//    }
    private void saveImageToGallery(){
        pic.setDrawingCacheEnabled(true);
//        Toast.makeText(MainActivity.this, "In saveImageToGallery method", Toast.LENGTH_SHORT).show();
        Bitmap b = pic.getDrawingCache();

        MediaStore.Images.Media.insertImage(this.getContentResolver(), b, "test image", "add to android gallery");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

//    public void DownloadFromUrl(String fileName) {
//        try {
//            URL url = new URL("http://mahikanthnag.net23.net/test.png"); //you can write here any link
//            File file = new File(fileName);
//
//            long startTime = System.currentTimeMillis();
//            tv.setText("Starting download......from " + url);
//            URLConnection ucon = url.openConnection();
//            InputStream is = ucon.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//                    /*
//                     * Read bytes to the Buffer until there is nothing more to read(-1).
//                     */
//            ByteArrayBuffer baf = new ByteArrayBuffer(50);
//            int current = 0;
//            while ((current = bis.read()) != -1) {
//                baf.append((byte) current);
//            }
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(baf.toByteArray());
//            pic.setImage
//            fos.close();
//            tv.setText("Download Completed in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
//        } catch (IOException e) {
//            tv.setText("Error: " + e);
//        }
//    }

}


