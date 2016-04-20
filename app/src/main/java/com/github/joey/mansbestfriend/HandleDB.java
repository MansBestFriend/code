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
        OutputStream output = new FileOutputStream(outName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = input.read(buffer)) > 0){
            output.write(buffer, 0, length);
        }

        output.flush();
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
        SQLiteDatabase checkDb = null;
        try {

            checkDb = SQLiteDatabase.openDatabase(db_path + db_name, null, SQLiteDatabase.OPEN_READONLY);
            checkDb.close();
        } catch(Exception e){

        }
        return checkDb != null;
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldv, int newV){

    }
}
