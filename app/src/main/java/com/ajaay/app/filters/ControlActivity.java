package com.ajaay.app.filters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.ajaay.app.filters.ImagePreviewActvity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ajaay.app.filters.utility.Helper;
import com.ajaay.app.filters.utility.TransformImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;


public class ControlActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    Toolbar mControlToolbar;
    Uri selectedImageUri;

    ImageView mTickImageView;

    TransformImage ti;

    ImageView mCenterImageView;

    int sw,sh,cf;
    SeekBar msb;
    ImageView mCancelImageView;

    Target mApplySingleFilter=new Target(){
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            int cfv=msb.getProgress();
            if (cf == TransformImage.fb) {
                ti.applybf(cfv);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fb),ti.getBitmap(TransformImage.fb));
                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fb)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fb))).resize(0,sh/2).into(mCenterImageView);
            } else if (cf == TransformImage.fc) {
                ti.applycf(cfv);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fc),ti.getBitmap(TransformImage.fc));
                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fc))).resize(0,sh/2).into(mCenterImageView);
            } else if (cf == TransformImage.fv) {
                ti.applyvf(cfv);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv),ti.getBitmap(TransformImage.fv));
                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv))).resize(0,sh/2).into(mCenterImageView);
            } else if (cf == TransformImage.fs) {
                ti.applysf(cfv);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fs),ti.getBitmap(TransformImage.fs));
                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fs)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fs))).resize(0,sh/2).into(mCenterImageView);

            }

        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    Target mSmallTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


            ti = new TransformImage(ControlActivity.this,bitmap);
            ti.applybf(TransformImage.dbf);

            Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fb),ti.getBitmap(TransformImage.fb));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fb))).fit().centerInside().into(mFirstFilterPreviewImageView);
            //brightness
            ti.applycf(TransformImage.dcf);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fc),ti.getBitmap(TransformImage.fc));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fc))).fit().centerInside().into(mSecondFilterPreviewImageView);
//saturation
            ti.applyvf(TransformImage.dvf);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv),ti.getBitmap(TransformImage.fv));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv))).fit().centerInside().into(mThirdFilterPreviewImageView);
//vignette
            ti.applysf(TransformImage.dsf);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fs),ti.getBitmap(TransformImage.fs));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fs))).fit().centerInside().into(mFourthFilterPreviewImageView);
//contrast
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    final static int PICK_IMAGE = 2;
    final static  int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION = 3;

    ImageView mFirstFilterPreviewImageView;
    ImageView mSecondFilterPreviewImageView;
    ImageView mThirdFilterPreviewImageView;
    ImageView mFourthFilterPreviewImageView;

    private static final String TAG = ControlActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mControlToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCenterImageView = (ImageView) findViewById(R.id.centerimageView);
        msb=(SeekBar)findViewById(R.id.seekBar);

        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.drawable.icon);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        mFirstFilterPreviewImageView = (ImageView) findViewById(R.id.imageView7);
        mSecondFilterPreviewImageView = (ImageView) findViewById(R.id.imageView6);
        mThirdFilterPreviewImageView = (ImageView) findViewById(R.id.imageView5);
        mFourthFilterPreviewImageView = (ImageView) findViewById(R.id.imageView4);

        mTickImageView = (ImageView) findViewById(R.id.imageView8);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlActivity.this,ImagePreviewActvity.class);
                startActivity(intent);
            }
        });

        mCenterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermissions();

                if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        mFirstFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msb.setMax(ti.mbf);
                msb.setProgress(TransformImage.dbf);

                cf=ti.fb;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fb))).resize(0,sh/2).into(mCenterImageView);
            }
        });
        mSecondFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msb.setMax(ti.mcf);
                msb.setProgress(TransformImage.dcf);

                cf=ti.fc;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fc))).resize(0,sh/2).into(mCenterImageView);
            }
        });
        mThirdFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msb.setMax(ti.mvf);
                msb.setProgress(TransformImage.dvf);
                cf=ti.fv;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fv))).resize(0,sh/2).into(mCenterImageView);
            }
        });
        mFourthFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msb.setMax(ti.msf);
                msb.setProgress(TransformImage.dsf);
                cf=ti.fs;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,ti.getFileName(TransformImage.fs ))).resize(0,sh/2).into(mCenterImageView);
            }
        });
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(ControlActivity.this).load(selectedImageUri).into(mApplySingleFilter);
            }
        });
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        sh=dm.heightPixels;
        sw=dm.widthPixels;
    }



    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new MaterialDialog.Builder(ControlActivity.this).title("Permission Granted")
                            .content("Thank you for providing storage permission")
                            .positiveText("Ok").canceledOnTouchOutside(true).show();
                } else {
                    Log.d(TAG,"Permission denied!");
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data.getData();

            Picasso.with(ControlActivity.this).load(selectedImageUri).fit().centerInside().into(mCenterImageView);

            Picasso.with(ControlActivity.this).load(selectedImageUri).into(mSmallTarget);



     }
    }

    public void requestStoragePermissions() {
        if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new MaterialDialog.Builder(ControlActivity.this).title(R.string.permission_title)
                        .content(R.string.permission_content)
                        .negativeText(R.string.permission_cancel)
                        .positiveText(R.string.permission_agree_settings)
                        .canceledOnTouchOutside(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION);
            }
            return;
        }
    }

}
