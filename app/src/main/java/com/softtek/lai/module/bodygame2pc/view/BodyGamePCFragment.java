package com.softtek.lai.module.bodygame2pc.view;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.LazyBaseFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame_pc)
public class BodyGamePCFragment extends LazyBaseFragment implements View.OnClickListener {
@InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                startActivity(new Intent(getContext(),StuPersonDateActivity.class));
                break;
        }
    }
}
