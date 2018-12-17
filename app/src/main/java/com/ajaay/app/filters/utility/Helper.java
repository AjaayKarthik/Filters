package com.ajaay.app.filters.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.ajaay.app.filters.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Helper {
    public static Boolean writeDataIntoExternalStorage(Context context, String fn, Bitmap bm){
        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+context.getString(R.string.app_name));
        if(!dir.exists() && !dir.mkdir()) {
            return false;
        }
        File f=new File(dir.getAbsolutePath()+"/"+ fn);
        if(f.exists() && !(f.canWrite())){
            return false;
        }
        try
        {
            FileOutputStream fos=new FileOutputStream(f);
            return bm.compress(Bitmap.CompressFormat.PNG,100,fos);
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static File getFileFromExternalStorage(Context context,String filename)
    {
        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+context.getString(R.string.app_name));
        File f=new File(dir.getAbsolutePath()+"/"+ filename);
        if(!f.exists() || !f.canRead())
        {
            return null;
        }
        return f;
    }

}
