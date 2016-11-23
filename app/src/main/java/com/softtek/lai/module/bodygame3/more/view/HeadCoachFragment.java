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
import com.softtek.lai.module.bodygame3.more.model.ClassModel;

public class HeadCoachFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout rl_invitation;
    private RelativeLayout rl_class_manager;


    public HeadCoachFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_head_coach, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rl_invitation= (RelativeLayout) view.findViewById(R.id.rl_invitation);
        rl_class_manager= (RelativeLayout) view.findViewById(R.id.rl_class_manager);
        rl_invitation.setOnClickListener(this);
        rl_class_manager.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_invitation: {
                Intent intent = new Intent(getContext(), InvitationListActivity.class);
                intent.putExtra("class", getArguments());
                startActivity(intent);
            }
                break;
            case R.id.rl_class_manager: {
                Intent intent = new Intent(getContext(), ClassManagerActivity.class);
                ClassModel model=getArguments().getParcelable("class");
                intent.putExtra("classId", model.getClassId());
                intent.putExtra("className",model.getClassName());
                startActivity(intent);
            }
                break;
        }
    }
}
