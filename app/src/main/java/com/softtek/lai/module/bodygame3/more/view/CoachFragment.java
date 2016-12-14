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
import com.softtek.lai.module.bodygame3.head.view.HonorActivity;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoachFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout rl_invitation;
    private RelativeLayout rl_honor;

    public CoachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rl_invitation= (RelativeLayout) view.findViewById(R.id.rl_invitation);
        rl_invitation.setOnClickListener(this);
        rl_honor= (RelativeLayout) view.findViewById(R.id.rl_honor);
        rl_honor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ClassModel model=getArguments().getParcelable("class");
        switch (view.getId()){
            case R.id.rl_invitation: {
                Intent intent = new Intent(getContext(), InvitationListActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;
            case R.id.rl_honor:{
                HonorActivity.startHonorActivity(getContext(),model.getClassId());
            }
            break;
        }
    }
}
