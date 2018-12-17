package com.ajaay.app.filters;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toolbar;

public class ImagePreviewActvity extends AppCompatActivity {
    android.support.v7.widget.Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview_actvity);


        tb= findViewById(R.id.toolbar2);
        tb.setTitle(getString(R.string.app_name));
        tb.setNavigationIcon(R.drawable.check );
        tb.setTitleTextColor(getResources().getColor(R.color.colorWhite));
    }
}
