package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.TreeMap;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final TreeMap<String,String> imgList;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId, TreeMap<String,String> imgList) {
        super(context,R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.imgList = imgList;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        if(imgList.containsKey(""+(position+1))){

            File imgFile = new File(imgList.get(""+(position+1)));
            Bitmap bitmap = decodeSampledBitmapFromResource(view.getResources(),imgFile.getAbsolutePath(),100,100);


            imageView.setImageBitmap(bitmap);




        }else {
            imageView.setImageResource(imageId[position]);
        }
        return rowView;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, String pathName,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap tmp = BitmapFactory.decodeFile(pathName, options);
        Matrix m = new Matrix();
        File f = new File(pathName);
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(f.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            switch(orientation){
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            m.postRotate(rotate);
        } catch(Exception e){
            e.printStackTrace();
        }

        return Bitmap.createBitmap(tmp,0,0,tmp.getWidth(),tmp.getHeight(),m,true);

    }
}