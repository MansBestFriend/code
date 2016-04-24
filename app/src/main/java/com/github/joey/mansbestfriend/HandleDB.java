package com.github.joey.mansbestfriend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Andrew on 4/6/2016.
 */



public class HandleDB extends SQLiteOpenHelper {

    public String db_path = null;
    public final static String db_name = "mansbestfriend.db";
    private static final int db_version = 1;
    private SQLiteDatabase myDatabase;
    private Context myContext = null;

    public HandleDB(Context c){
        super(c,db_name,null,db_version);
        this.myContext = c;
        db_path = "/data/data/" + c.getPackageName() + "/databases/";
    }

    public void createDataBase() throws IOException{

        this.getReadableDatabase();

        try{
            copyDatabase();
        } catch(Exception e){
            throw new Error("Error copying database");
        }
    }

    public void copyDatabase() throws IOException{

        InputStream input = myContext.getAssets().open(db_name);

        String outName = db_path + db_name;
        String out2Name = db_path + "1";
        OutputStream output = new FileOutputStream(outName);
        OutputStream output2 = new FileOutputStream(out2Name);
        byte[] buffer = new byte[1024];
        int length;
        while((length = input.read(buffer)) > 0){
            output.write(buffer, 0, length);
        }

        output.flush();
        output2.close();
        output.close();
        input.close();
    }

    @Override
    public synchronized void close(){
        if(myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }

    public boolean checkDatabase(Context chk){
        boolean checkDb = true;
        try {
            File database = chk.getDatabasePath(db_path + "1");
            if(!database.exists()){
                checkDb = false;
                Log.e("e","false");
            } else {
                checkDb = true;
                Log.e("e","true");
            }

        } catch(Exception e){

        }

        return checkDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldv, int newV){

    }
}
