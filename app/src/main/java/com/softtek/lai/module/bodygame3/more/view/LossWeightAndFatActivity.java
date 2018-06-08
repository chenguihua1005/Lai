package com.softtek.lai.module.bodygame3.more.view;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.LevelModel;
import com.softtek.lai.module.bodygame3.more.model.LossWeightAndFat;
import com.softtek.lai.module.bodygame3.more.model.WeightAndFat;
import com.softtek.lai.module.bodygame3.more.net.MoreService;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_loss_weight_and_fat)
public class LossWeightAndFatActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;
    TextView tv_weight_level;
    TextView tv_weight_level_des;
    TextView tv_fat_level;
    TextView tv_fat_level_des;

    ImageView iv_weight_media_bg;
    ImageView iv_fat_media_bg;

    EasyAdapter<WeightAndFat> adapter;
    private List<WeightAndFat> models=new ArrayList<>(10);
    //当前减重和减脂等级
    private int weightLevel;
    private int fatLevel;

    @Override
    protected void initViews() {
        tv_title.setText("减重等级");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View head= LayoutInflater.from(this).inflate(R.layout.head_weight_fat_loss,null);
        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf=Typeface.createFromAsset(mgr, "font/BrushScriptStd.ttf");//根据路径得到Typeface
        tv_weight_level= (TextView) head.findViewById(R.id.tv_weight_level);
        tv_weight_level_des= (TextView) head.findViewById(R.id.tv_weight_level_des);
        tv_fat_level= (TextView) head.findViewById(R.id.tv_fat_level);
        tv_fat_level_des= (TextView) head.findViewById(R.id.tv_fat_level_des);
        iv_weight_media_bg= (ImageView) head.findViewById(R.id.iv_weight_media_bg);
        iv_fat_media_bg= (ImageView) head.findViewById(R.id.iv_fat_media_bg);
        tv_weight_level.setTypeface(tf);
        tv_fat_level.setTypeface(tf);
        lv.addHeaderView(head);

    }

    @Override
    protected void initDatas() {
        setData();
        adapter=new EasyAdapter<WeightAndFat>(this,models,R.layout.item_loss_weight_fat) {
            @Override
            public void convert(ViewHolder holder, WeightAndFat data, int position) {
                TextView tvWeightLevelName=holder.getView(R.id.tv_weight_level_name);
                tvWeightLevelName.setText(data.getWeightLevel().getLevelName());
                TextView tvLossWeight=holder.getView(R.id.tv_loss_weight);
                tvLossWeight.setText("减重");
                SpannableString ss=new SpannableString(data.getWeightLevel().getLossCount());
                ss.setSpan(new AbsoluteSizeSpan(30,true),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvLossWeight.append(ss);
                tvLossWeight.append("斤");
                TextView tvNationWeightPerson=holder.getView(R.id.tv_nation_weight_person);
                tvNationWeightPerson.setText(String.valueOf(data.getWeightLevel().getReachCount()));
                tvNationWeightPerson.append("人达成");
                if(data.getWeightLevel().getLevel()<=weightLevel){
                    tvWeightLevelName.setTextColor(0XFF333333);
                    tvLossWeight.setTextColor(0XFF333333);
                }else {
                    tvWeightLevelName.setTextColor(0XFF999999);
                    tvLossWeight.setTextColor(0XFF999999);
                }

                TextView tvFatLevelName=holder.getView(R.id.tv_fat_level_name);
                tvFatLevelName.setText(data.getFatLlevel().getLevelName());
                TextView tvLossFat=holder.getView(R.id.tv_loss_fat);
                tvLossFat.setText("减脂");
                SpannableString per=new SpannableString(data.getFatLlevel().getLossCount());
                per.setSpan(new AbsoluteSizeSpan(30,true),0,per.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvLossFat.append(per);
                tvLossFat.append("%");
                TextView tvNationFatPerson=holder.getView(R.id.tv_nation_fat_person);
                tvNationFatPerson.setText(String.valueOf(data.getFatLlevel().getReachCount()));
                tvNationFatPerson.append("人达成");
                if(data.getFatLlevel().getLevel()<=fatLevel){
                    tvFatLevelName.setTextColor(0XFF333333);
                    tvLossFat.setTextColor(0XFF333333);
                }else {
                    tvFatLevelName.setTextColor(0XFF999999);
                    tvLossFat.setTextColor(0XFF999999);
                }

            }
        };
        lv.setAdapter(adapter);
        dialogShow("获取数据");
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getLossLevel(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new Callback<ResponseData<LossWeightAndFat>>() {
                            @Override
                            public void success(ResponseData<LossWeightAndFat> data, Response response) {
                                dialogDissmiss();
                                if(data.getStatus()==200){
                                    dealResult(data.getData());
                                }else {
                                    Util.toastMsg(data.getMsg());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                ZillaApi.dealNetError(error);
                            }
                        });
    }

    private void dealResult(LossWeightAndFat data){
        try {
            weightLevel=data.getWeightLevel();
            fatLevel=data.getFatLevel();
            tv_weight_level.setText("W");
            tv_weight_level.append(String.valueOf(data.getWeightLevel()==0?1:data.getWeightLevel()));
            if(data.getWeightLevel()==0){
                iv_weight_media_bg.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_media_dark));
                tv_weight_level.setTextColor(0xFF878787);
                if ("0".equals(data.getIsHasClass()))
                {
                    tv_weight_level_des.setText("参加体管赛班级"+"\n"+"才能累积减重等级");
                }
                else {
                    tv_weight_level_des.setText("减重5斤才能点亮勋章");
                }
            }else {
                tv_weight_level.setTextColor(0xFFF3EE7B);
                iv_weight_media_bg.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_media));

                tv_weight_level_des.setText("当前减重等级为W"+data.getWeightLevel());
            }
            tv_fat_level.setText("Z");
            tv_fat_level.append(String.valueOf(data.getFatLevel()==0?1:data.getFatLevel()));
            if(data.getFatLevel()==0){
                tv_fat_level.setTextColor(0xFF878787);
                iv_fat_media_bg.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_media_dark));
                if (data.getIsHasClass().equals("0"))
                {
                    tv_fat_level_des.setText("参加体管赛班级"+"\n"+"才能累积减脂等级");
                }
                else {
                    tv_fat_level_des.setText("减脂1%才能点亮勋章");
                }
            }else {
                tv_fat_level.setTextColor(0xFFF3EE7B);
                iv_fat_media_bg.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_media));
                tv_fat_level_des.setText("当前减脂等级为Z"+data.getFatLevel());
            }
            List<LossWeightAndFat.LossWeightLevel> weightLevels=data.getLossWeightLevelList();
            List<LossWeightAndFat.LossFatLevel> fatLevels=data.getLossFatLevelList();
            for (int i=0;i<models.size();i++){
                LossWeightAndFat.LossWeightLevel weight=weightLevels.get(i);
                LossWeightAndFat.LossFatLevel fat=fatLevels.get(i);
                LevelModel weightLevel=models.get(i).getWeightLevel();
                weightLevel.setReachCount(weight.getWeightReachCount());
                LevelModel fatLevel=models.get(i).getFatLlevel();
                fatLevel.setReachCount(fat.getFatReachCount());
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setData(){
        WeightAndFat model=new WeightAndFat();
        LevelModel weight=new LevelModel();
        weight.setLevelName("W1等级");
        weight.setLossCount("5");
        LevelModel fat=new LevelModel();
        fat.setLevelName("Z1等级");
        fat.setLossCount("1");
        model.setWeightLevel(weight);
        model.setFatLlevel(fat);
        weight.setLevel(1);
        fat.setLevel(1);
        models.add(model);

        WeightAndFat model2=new WeightAndFat();
        LevelModel weight2=new LevelModel();
        weight2.setLevelName("W2等级");
        weight2.setLossCount("10");
        LevelModel fat2=new LevelModel();
        fat2.setLevelName("Z2等级");
        fat2.setLossCount("2");
        model2.setWeightLevel(weight2);
        model2.setFatLlevel(fat2);
        weight2.setLevel(2);
        fat2.setLevel(2);
        models.add(model2);

        WeightAndFat model3=new WeightAndFat();
        LevelModel weight3=new LevelModel();
        weight3.setLevelName("W3等级");
        weight3.setLossCount("15");
        LevelModel fat3=new LevelModel();
        fat3.setLevelName("Z3等级");
        fat3.setLossCount("3");
        model3.setWeightLevel(weight3);
        model3.setFatLlevel(fat3);
        weight3.setLevel(3);
        fat3.setLevel(3);
        models.add(model3);

        WeightAndFat model4=new WeightAndFat();
        LevelModel weight4=new LevelModel();
        weight4.setLevelName("W4等级");
        weight4.setLossCount("30");
        LevelModel fat4=new LevelModel();
        fat4.setLevelName("Z4等级");
        fat4.setLossCount("6");
        model4.setWeightLevel(weight4);
        model4.setFatLlevel(fat4);
        weight4.setLevel(4);
        fat4.setLevel(4);
        models.add(model4);

        WeightAndFat model5=new WeightAndFat();
        LevelModel weight5=new LevelModel();
        weight5.setLevelName("W5等级");
        weight5.setLossCount("50");
        LevelModel fat5=new LevelModel();
        fat5.setLevelName("Z5等级");
        fat5.setLossCount("10");
        model5.setWeightLevel(weight5);
        model5.setFatLlevel(fat5);
        weight5.setLevel(5);
        fat5.setLevel(5);
        models.add(model5);

        WeightAndFat model6=new WeightAndFat();
        LevelModel weight6=new LevelModel();
        weight6.setLevelName("W6等级");
        weight6.setLossCount("60");
        LevelModel fat6=new LevelModel();
        fat6.setLevelName("Z6等级");
        fat6.setLossCount("12");
        model6.setWeightLevel(weight6);
        model6.setFatLlevel(fat6);
        weight6.setLevel(6);
        fat6.setLevel(6);
        models.add(model6);

        WeightAndFat model7=new WeightAndFat();
        LevelModel weight7=new LevelModel();
        weight7.setLevelName("W7等级");
        weight7.setLossCount("70");
        LevelModel fat7=new LevelModel();
        fat7.setLevelName("Z7等级");
        fat7.setLossCount("14");
        model7.setWeightLevel(weight7);
        model7.setFatLlevel(fat7);
        weight7.setLevel(7);
        fat7.setLevel(7);
        models.add(model7);

        WeightAndFat model8=new WeightAndFat();
        LevelModel weight8=new LevelModel();
        weight8.setLevelName("W8等级");
        weight8.setLossCount("80");
        LevelModel fat8=new LevelModel();
        fat8.setLevelName("Z8等级");
        fat8.setLossCount("16");
        model8.setWeightLevel(weight8);
        model8.setFatLlevel(fat8);
        weight8.setLevel(8);
        fat8.setLevel(8);
        models.add(model8);

        WeightAndFat model9=new WeightAndFat();
        LevelModel weight9=new LevelModel();
        weight9.setLevelName("W9等级");
        weight9.setLossCount("90");
        LevelModel fat9=new LevelModel();
        fat9.setLevelName("Z9等级");
        fat9.setLossCount("18");
        model9.setWeightLevel(weight9);
        model9.setFatLlevel(fat9);
        weight9.setLevel(9);
        fat9.setLevel(9);
        models.add(model9);

        WeightAndFat model0=new WeightAndFat();
        LevelModel weight0=new LevelModel();
        weight0.setLevelName("W10等级");
        weight0.setLossCount("100");
        LevelModel fat0=new LevelModel();
        fat0.setLevelName("Z10等级");
        fat0.setLossCount("20");
        model0.setWeightLevel(weight0);
        model0.setFatLlevel(fat0);
        weight0.setLevel(10);
        fat0.setLevel(10);
        models.add(model0);

    }
}
