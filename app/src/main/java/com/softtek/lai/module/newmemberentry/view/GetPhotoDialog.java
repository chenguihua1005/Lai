package com.softtek.lai.module.newmemberentry.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.softtek.lai.R;

/**
 * Created by julie.zhu on 3/26/2016.
 */
public class GetPhotoDialog extends Dialog  implements View.OnClickListener {
    private ImageButton camera,pic;


    private GetPhotoDialogListener listener;

    public interface GetPhotoDialogListener{
        public void onClick(View view);
    }


    public GetPhotoDialog(Context context,GetPhotoDialogListener listener) {
        super(context);
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoselect);
        initViews();
    }

    private void initViews(){
        camera = (ImageButton)findViewById(R.id.imgbtn_camera);
        pic = (ImageButton)findViewById(R.id.imgbtn_pic);
        camera.setOnClickListener(this);
        pic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
        dismiss();
    }

}
