package com.ajaay.app.filters.utility;

import android.content.Context;
import android.graphics.Bitmap;

import com.ajaay.app.filters.ControlActivity;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;

public class TransformImage {

    public static final int dbf=70,dsf=60,dvf=100,dcf=5;
    private String mfn;
    private Bitmap bfb,vfb,cfb,sfb;
    public static final int mbf=100,msf=5,mvf=255,mcf=100;


    public Context mcn;
    public Bitmap mbm;
    public static int fb=0,fv=1,fs=2,fc=3;
    public TransformImage(Context cn,Bitmap bm)
    {
        mbm=bm;
        mcn=cn;
        mfn=System.currentTimeMillis()+"";
    }

    public String getFileName(int f)
    {
        if(f==fb)
            return mfn+"_brightness.jpg";
        else if(f==fc)
            return mfn+"_contrast.jpg";
        else if(f==fs)
            return mfn+"_saturation.jpg";
        else if(f==fv)
            return mfn+"_vignette.jpg";
        return mfn;
    }

    public Bitmap getBitmap(int f)
    {
        if(f==fb)
            return bfb;
        else if(f==fc)
            return cfb;
        else if(f==fs)
            return sfb;
        else if(f==fv)
            return vfb;
        return mbm;
    }


    public Bitmap applybf(int b)
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap = Bitmap.createBitmap(mbm);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new BrightnessSubfilter(b));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        bfb=outputImage;
        return outputImage;

    }
    public Bitmap applyvf(int v){

        Filter myFilter = new Filter();
        Bitmap workingBitmap = Bitmap.createBitmap(mbm);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new VignetteSubfilter(mcn,v));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        vfb=outputImage;
        return outputImage;

    }
    public Bitmap applycf(int c){

        Filter myFilter = new Filter();
        Bitmap workingBitmap = Bitmap.createBitmap(mbm);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new ContrastSubfilter(c));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        cfb=outputImage;
        return outputImage;
    }

    public Bitmap applysf(int s)
    {

        Filter myFilter = new Filter();
        Bitmap workingBitmap = Bitmap.createBitmap(mbm);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new SaturationSubfilter(s));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        sfb=outputImage;
        return outputImage;
    }
}
