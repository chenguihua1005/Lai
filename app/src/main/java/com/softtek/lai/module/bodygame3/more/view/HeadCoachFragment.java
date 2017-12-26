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

public class HeadCoachFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rl_invitation;
    private RelativeLayout rl_class_manager;
    private RelativeLayout rl_honor;

    private RelativeLayout rl_love_student;//爱心学员
    private RelativeLayout rl_support_team;// 服务团队


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
        rl_invitation = (RelativeLayout) view.findViewById(R.id.rl_invitation);
        rl_class_manager = (RelativeLayout) view.findViewById(R.id.rl_class_manager);
        rl_honor = (RelativeLayout) view.findViewById(R.id.rl_honor);
        rl_love_student = (RelativeLayout) view.findViewById(R.id.rl_love_student);
        rl_support_team = (RelativeLayout) view.findViewById(R.id.rl_support_team);

        rl_invitation.setOnClickListener(this);
        rl_class_manager.setOnClickListener(this);
        rl_honor.setOnClickListener(this);
        rl_love_student.setOnClickListener(this);
        rl_support_team.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ClassModel model = getArguments().getParcelable("class");
        switch (view.getId()) {
            case R.id.rl_invitation: {
                Intent intent = new Intent(getContext(), InvitationListActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;
            case R.id.rl_class_manager: {
                Intent intent = new Intent(getContext(), ClassManagerActivity.class);
                intent.putExtra("class", model);
                startActivity(intent);
            }
            break;
            case R.id.rl_honor: {
//                Honor2Activity.startHonorActivity(getContext(), model.getClassId());
                HonorActivity.startHonorActivity(getContext(), model.getClassId());

            }
            break;

            case R.id.rl_love_student: {
                Intent intent = new Intent(getContext(), LoveStudentActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;

            case R.id.rl_support_team: {
                Intent intent = new Intent(getContext(), SupportTeamActivity.class);
                intent.putExtra("classId", model.getClassId());
                startActivity(intent);
            }
            break;
        }
    }
}
