package com.softtek.lai.module.bodygame3.more.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygamest.view.StudentHonorGridActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements View.OnClickListener{


    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout rl_level= (RelativeLayout) view.findViewById(R.id.rl_level);
        rl_level.setOnClickListener(this );
        RelativeLayout rl_media= (RelativeLayout) view.findViewById(R.id.rl_media);
        rl_media.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_level:{
                Intent intent=new Intent(getContext(),LossWeightAndFatActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.rl_media:{
                startActivity(new Intent(getContext(), StudentHonorGridActivity.class));
            }
                break;
        }
    }
}
