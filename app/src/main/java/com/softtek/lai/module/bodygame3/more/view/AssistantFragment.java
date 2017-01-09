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
public class AssistantFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rl_love_student;//爱心学员
    private RelativeLayout rl_support_team;// 服务团队


    public AssistantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assistant, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout rl_honor = (RelativeLayout) view.findViewById(R.id.rl_honor);
        rl_honor.setOnClickListener(this);

        rl_love_student = (RelativeLayout) view.findViewById(R.id.rl_love_student);
        rl_love_student.setOnClickListener(this);
        rl_support_team = (RelativeLayout) view.findViewById(R.id.rl_support_team);
        rl_support_team.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        ClassModel model = getArguments().getParcelable("class");
        switch (view.getId()) {
            case R.id.rl_honor: {
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
