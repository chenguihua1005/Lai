package com.softtek.lai.module.laicheng_new.util;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.utils.Component;


/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {

    @Override
    public View getView(LayoutInflater inflater) {

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_frends, null);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return ll;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_END;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 10;
    }
}
