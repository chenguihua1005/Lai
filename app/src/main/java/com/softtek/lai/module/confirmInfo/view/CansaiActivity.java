package com.softtek.lai.module.confirmInfo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.confirmInfo.Adapter.MemberAdapter;
import com.softtek.lai.module.confirmInfo.EventModel.ConinfoEvent;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;
import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;
import com.softtek.lai.module.confirmInfo.presenter.GuwenClassImp;
import com.softtek.lai.module.confirmInfo.presenter.GuwenClassPre;
import com.softtek.lai.module.confirmInfo.presenter.IUpConfirmInfopresenter;
import com.softtek.lai.module.confirmInfo.presenter.UpConfirmInfoImpl;
import com.softtek.lai.module.message.model.MessageDetailInfo;
import com.softtek.lai.module.newmemberentry.view.EventModel.ClassEvent;
import com.softtek.lai.module.newmemberentry.view.model.PargradeModel;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.widgets.WheelView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_cansai)
public class CansaiActivity extends BaseActivity implements View.OnClickListener {

    //toolbar
    //标题
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;


    //弹框点击事件
    @InjectView(R.id.ll_birthday)
    LinearLayout ll_birthday;

    @InjectView(R.id.ll_sex)
    LinearLayout ll_sex;

    @InjectView(R.id.ll_classname)
    LinearLayout ll_classname;

    @InjectView(R.id.ll_weight)
    LinearLayout ll_weight;

    @InjectView(R.id.ll_pysical)
    LinearLayout ll_pysical;

    @InjectView(R.id.ll_fat)
    LinearLayout ll_fat;

    @InjectView(R.id.ll_circum)
    LinearLayout ll_circum;

    @InjectView(R.id.ll_waistline)
    LinearLayout ll_waistline;

    @InjectView(R.id.ll_hiplie)
    LinearLayout ll_hiplie;

    @InjectView(R.id.ll_uparmgirth)
    LinearLayout ll_uparmgirth;

    @InjectView(R.id.ll_upleggirth)
    LinearLayout ll_upleggirth;

    @InjectView(R.id.ll_doleggirth)
    LinearLayout ll_doleggirth;

    //确认参赛信息
    @InjectView(R.id.et_name)
    EditText et_name;

    @InjectView(R.id.tv_birthday)
    TextView tv_birthday;

    @InjectView(R.id.tv_sex)
    TextView tv_sex;

    @InjectView(R.id.et_mobile)
    TextView et_mobile;

    @InjectView(R.id.tv_classname)
    TextView tv_classname;

    @InjectView(R.id.tv_weight)
    TextView tv_weight;

    @InjectView(R.id.et_pysical)
    TextView et_pysical;

    @InjectView(R.id.et_fat)
    TextView et_fat;

    //身体围度
    @InjectView(R.id.tv_circum)
    TextView tv_circum;

    @InjectView(R.id.tv_waistline)
    TextView tv_waistline;

    @InjectView(R.id.tv_hiplie)
    TextView tv_hiplie;

    @InjectView(R.id.tv_uparmgirth)
    TextView tv_uparmgirth;

    @InjectView(R.id.tv_upleggirth)
    TextView tv_upleggirth;

    @InjectView(R.id.tv_doleggirth)
    TextView tv_doleggirth;

    //确认照片信息photo
    @InjectView(R.id.img1)
    ImageView img1;

    @InjectView(R.id.img_photoupload)
    ImageView img_photoupload;

    @InjectView(R.id.img_delete)
    ImageView img_delete;

    //确定按钮
    @InjectView(R.id.btn_sure)
    Button btn_sure;
    private GuwenClassPre guwenClassPre;

    private ConinfoModel coninfoModel;
    private GetConfirmInfoModel getConfirmInfoModel;

    private IUpConfirmInfopresenter iUpConfirmInfopresenter;

    private String photo;
    private String change_photo;

    private String name;

    private String birthday;

    private String sex;

    private String mobile;

    private String classname;

    private String weight;

    private String pysical;

    private String fat;

    private String circum;

    private String waistline;

    private String hiplie;

    private String uparmgirth;

    private String upleggirth;

    private String doleggirth;

    //获取当前日期
    Calendar ca = Calendar.getInstance();
    int myear = ca.get(Calendar.YEAR);//获取年份
    int mmonth = ca.get(Calendar.MONTH);//获取月份
    int mday = ca.get(Calendar.DATE);//获取日

    private List<String> gradeList = new ArrayList<String>();
    private List<String> gradeIDList = new ArrayList<String>();
    private String select_grade = "";
    private String grade_id = "";

    String path = "";
    private static final int PHOTO = 1;

    @InjectView(R.id.list_cansaibanji)
    ListView list_cansaibanji;
    private List<PargradeModel> pargradeModelList = new ArrayList<PargradeModel>();

    long accoutid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        iUpConfirmInfopresenter = new UpConfirmInfoImpl(this);
        UserInfoModel userInfoModel = UserInfoModel.getInstance();
        accoutid = Long.parseLong(userInfoModel.getUser().getUserid());
        accoutid = 341;
//        Intent intent = getIntent();
//        MessageDetailInfo messageDetailInfo = (MessageDetailInfo) intent.getSerializableExtra("messageDetailInfo");
//        String classid = messageDetailInfo.getClassId();
        String classid = "35";
        //获取classid
        Log.i("获取classid:--messageDetailInfo------------------->" + classid);

        //参数:---accountid 学员id,classid  班级id
        iUpConfirmInfopresenter.getConfirmInfo(accoutid, Long.parseLong(classid));//130,1-------17,30,32
        guwenClassPre = new GuwenClassImp();

        //managerId顾问id
        guwenClassPre.doGetGuwenClass(accoutid);//36
//        MemberAdapter memberAdapter = new MemberAdapter(this, R.layout.member_item, pargradeModelList);
//        list_cansaibanji.setAdapter(memberAdapter);
//        list_cansaibanji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PargradeModel pargradeModel = pargradeModelList.get(position);
//                tv_classname.setText(pargradeModel.getClassName());
//                tv_classname.setError(null);
//                list_cansaibanji.setVisibility(View.INVISIBLE);
//            }
//        });

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        tv_title.setText("报名参赛");
        ll_left.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
        addGrade();
        ll_sex.setOnClickListener(this);
        //ll_classname.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
        ll_pysical.setOnClickListener(this);
        ll_fat.setOnClickListener(this);
        ll_circum.setOnClickListener(this);
        ll_waistline.setOnClickListener(this);
        ll_hiplie.setOnClickListener(this);
        ll_uparmgirth.setOnClickListener(this);
        ll_upleggirth.setOnClickListener(this);
        ll_doleggirth.setOnClickListener(this);
        img_photoupload.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ClassEvent classEvent) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + classEvent.getPargradeModels());
//        List<PargradeModel> pargradeModels = classEvent.getPargradeModels();
//        for (PargradeModel cl : pargradeModels) {
//            System.out.println("dsfsdfsdfsdfsdfsdf?????/?????>>》》》》》》》》》》》》》》" + "ClassIdModel:" + cl.getClassId() + "ClassName:" + cl.getClassName());
//            PargradeModel p1 = new PargradeModel(cl.getClassId(), cl.getClassName());
//            pargradeModelList.add(p1);
//        }
    }

    @Subscribe
    public void onEvent(PhotModel photModel) {
        System.out.println("photModel:" + photModel);
        change_photo = photModel.getImg();
    }

    @Subscribe
    public void onEvent(ConinfoEvent coninfoEvent) {
        System.out.println("classEvent.getPargradeModels()>>》》》》》》》》》》》》》》" + coninfoEvent.getConfirmInfoModel());
        GetConfirmInfoModel getConfirmInfoModel = coninfoEvent.getConfirmInfoModel();

        name = getConfirmInfoModel.getUserName();
        birthday = getConfirmInfoModel.getBirthday();
        sex = getConfirmInfoModel.getGender();
        mobile = getConfirmInfoModel.getMobile();
        classname = getConfirmInfoModel.getClassName();
        weight = getConfirmInfoModel.getWeight();
        pysical = getConfirmInfoModel.getPysical();
        fat = getConfirmInfoModel.getFat();
        circum = getConfirmInfoModel.getCircum();
        waistline = getConfirmInfoModel.getWaistline();
        hiplie = getConfirmInfoModel.getHiplie();
        uparmgirth = getConfirmInfoModel.getUpArmGirth();
        upleggirth = getConfirmInfoModel.getUpLegGirth();
        doleggirth = getConfirmInfoModel.getDoLegGirth();
        photo = getConfirmInfoModel.getPhoto();

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        //String path= AddressManager.get("photoHost","http://172.16.98.167/FileUpload/PostFile/");
        if (!TextUtils.isEmpty(getConfirmInfoModel.getPhoto())) {
            Picasso.with(this).load(path + getConfirmInfoModel.getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img1);
        } else {
            Picasso.with(this).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(img1);
        }

        Log.i("获取照片地址：》》》》》》" + path + getConfirmInfoModel.getPhoto());
        et_name.setText(name);
        tv_birthday.setText(birthday);
        tv_sex.setText(sex.equals("0") ? "男" : "女");
        et_mobile.setText(mobile);
        tv_classname.setText(classname);
        tv_weight.setText(String.valueOf(weight));
        et_pysical.setText(String.valueOf(pysical));
        et_fat.setText(String.valueOf(fat));
        tv_circum.setText(String.valueOf(circum));
        tv_waistline.setText(String.valueOf(waistline));
        tv_hiplie.setText(String.valueOf(hiplie));
        tv_uparmgirth.setText(String.valueOf(uparmgirth));
        tv_upleggirth.setText(String.valueOf(upleggirth));
        tv_doleggirth.setText(String.valueOf(doleggirth));
    }

    private CharSequence[] items = {"拍照", "照片"};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_birthday:
                show_birth_dialog();
                break;
            case R.id.ll_sex:
                showGradeDialog();
                break;
            case R.id.ll_classname:
//                if (list_cansaibanji.getVisibility() == View.VISIBLE) {
//                    list_cansaibanji.setVisibility(View.INVISIBLE);
//                } else if (list_cansaibanji.getVisibility() == View.INVISIBLE) {
//                    list_cansaibanji.setVisibility(View.VISIBLE);
//                }
                break;
            case R.id.ll_weight:
                show_weight_dialog();
                break;
            case R.id.ll_pysical:
                show_pysical_dialog();
                break;
            case R.id.ll_fat:
                show_fat_dialog();
                break;
            case R.id.ll_circum:
                show_circum_dialog();
                break;
            case R.id.ll_waistline:
                show_waistline_dialog();
                break;
            case R.id.ll_hiplie:
                show_hiplie_dialog();
                break;
            case R.id.ll_uparmgirth:
                show_uparmgirth_dialog();
                break;
            case R.id.ll_upleggirth:
                show_upleggirth_dialog();
                break;
            case R.id.ll_doleggirth:
                show_doleggirth_dialog();
                break;
            case R.id.img_photoupload:
                AlertDialog.Builder builder = new AlertDialog.Builder(CansaiActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //拍照
                            //先验证手机是否有sdcard
                            String status = Environment.getExternalStorageState();
                            if (status.equals(Environment.MEDIA_MOUNTED)) {
                                try {
                                    takecamera();
                                } catch (ActivityNotFoundException e) {
                                    Util.toastMsg("没有找到存储目录");
                                }
                            } else {
                                Util.toastMsg("没有存储卡");
                            }
                        } else if (which == 1) {
                            //照片
                            //startActivityForResult(SystemUtils.openPicture(),OPEN_PICTURE_REQUEST);
                            takepic();
                        }
                    }
                }).create().show();

                break;
            case R.id.btn_sure:
                String token = SharedPreferenceService.getInstance().get("token", "");
//                Intent intent = getIntent();
//                MessageDetailInfo messageDetailInfo = (MessageDetailInfo) intent.getSerializableExtra("messageDetailInfo");
//                String classid = messageDetailInfo.getClassId();
                String classid = "35";

                coninfoModel = new ConinfoModel();
                //设置classid
                coninfoModel.setClassid(classid);

                coninfoModel.setAccountid(accoutid);
                coninfoModel.setNickname(et_name.getText().toString());
                coninfoModel.setBirthday(tv_birthday.getText().toString());
                coninfoModel.setGender(tv_sex.getText().toString() == "男" ? 0 : 1);
                coninfoModel.setPhoto(change_photo);
                coninfoModel.setWeight(Double.parseDouble(tv_weight.getText().toString()));
                if (et_pysical.getText().toString().equals("")) {
                    coninfoModel.setPysical(0.0);
                } else {
                    coninfoModel.setPysical(Double.parseDouble(et_pysical.getText().toString()));
                }

                if (et_fat.getText().toString().equals("")) {
                    coninfoModel.setFat(0.0);
                } else {
                    coninfoModel.setFat(Double.parseDouble(et_fat.getText().toString()));
                }

                if (tv_circum.getText().toString().equals("")) {
                    coninfoModel.setCircum(0.0);
                } else {
                    coninfoModel.setCircum(Double.parseDouble(tv_circum.getText().toString()));
                }

                if (tv_waistline.getText().toString().equals("")) {
                    coninfoModel.setWaistline(0.0);
                } else {
                    coninfoModel.setWaistline(Double.parseDouble(tv_waistline.getText().toString()));
                }

                if (tv_hiplie.getText().toString().equals("")) {
                    coninfoModel.setHiplie(0.0);
                } else {
                    coninfoModel.setHiplie(Double.parseDouble(tv_hiplie.getText().toString()));
                }

                if (tv_uparmgirth.getText().toString().equals("")) {
                    coninfoModel.setUparmgirth(0.0);
                } else {
                    coninfoModel.setUparmgirth(Double.parseDouble(tv_uparmgirth.getText().toString()));
                }

                if (tv_upleggirth.getText().toString().equals("")) {
                    coninfoModel.setUpleggirth(0.0);
                } else {
                    coninfoModel.setUpleggirth(Double.parseDouble(tv_upleggirth.getText().toString()));
                }

                if (tv_doleggirth.getText().toString().equals("")) {
                    coninfoModel.setDoleggirth(0.0);
                } else {
                    coninfoModel.setDoleggirth(Double.parseDouble(tv_doleggirth.getText().toString()));
                }
                System.out.println("coninfoModel:" + coninfoModel);
                iUpConfirmInfopresenter.changeUpConfirmInfo(token, coninfoModel);
                break;
        }
    }


    public void takecamera() {
        path = (Environment.getExternalStorageDirectory().getPath()) + "/123.jpg";
        File file = new File(path.toString());
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PHOTO);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img_delete.setVisibility(View.VISIBLE);
        img1.setVisibility(View.VISIBLE);
        img1.setImageBitmap(bitmap);
        Log.i("path:" + path);
    }

    public void takepic() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PHOTO) {
            Bitmap bm = BitmapFactory.decodeFile(path.toString());
            Util.toastMsg(bm + "");
            //  bitmap = compressBitmap(getResources(), R.drawable.img3, 100, 100);
//                Log.v("zxy", "compressBitmap,width=" + bitmap.getWidth() + ",height=" + bitmap.getHeight());
//                mResizeImageView.setImageBitmap(bitmap);

            img1.setImageBitmap(bm);
            dialogShow("正在上传照片，请稍后。。。");
            iUpConfirmInfopresenter.upload(path.toString());
        }
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            img_delete.setVisibility(View.VISIBLE);
            img1.setVisibility(View.VISIBLE);
            img1.setImageBitmap(bitmap);
            dialogShow("正在上传照片，请稍后。。。");
            iUpConfirmInfopresenter.upload(picturePath.toString());
            Log.i("picturePath------------------------------------------------:" + picturePath);
            c.close();
        }
    }

    // 生日对话框
    public void show_birth_dialog() {
        final android.support.v7.app.AlertDialog.Builder birdialog = new android.support.v7.app.AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.birth_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np1.setMaxValue(myear);
        np1.setValue(myear);
        np1.setMinValue(1900);
        np1.setWrapSelectorWheel(false);

        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np2.setMaxValue(12);
        np2.setValue(mmonth + 1);
        np2.setMinValue(1);
        np2.setWrapSelectorWheel(false);

        final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
        np3.setMaxValue(31);
        np3.setValue(mday);
        np3.setMinValue(1);
        np3.setWrapSelectorWheel(false);

        birdialog.setTitle("选择生日(年-月-日)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (np1.getValue() == myear && np2.getValue() > (mmonth + 1)) {
                    show_warn_dialog();
                }
                if (np1.getValue() == myear && np2.getValue() == (mmonth + 1) && np3.getValue() > mday) {
                    show_warn_dialog();
                } else {
                    tv_birthday.setText(String.valueOf(np1.getValue()) + "-" + String.valueOf(np2.getValue()) + "-" + String.valueOf(np3.getValue()));
                    tv_birthday.setError(null);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    //生日警告对话框
    public void show_warn_dialog() {
        Dialog dialog = new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage("生日不能大于当前日期,请重新选择")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                show_birth_dialog();
                            }
                        }).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    //性别对话框
    private void addGrade() {
        gradeList.add("男");
        gradeList.add("女");
        gradeIDList.add("0");
        gradeIDList.add("1");
    }

    public void showGradeDialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select_grade, null);
        final WheelView wheel_grade = (WheelView) view.findViewById(R.id.wheel_grade);
        wheel_grade.setOffset(1);
        wheel_grade.setItems(gradeList);
        wheel_grade.setSeletion(0);
        select_grade = "";
        wheel_grade.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                select_grade = item;
                grade_id = gradeIDList.get(selectedIndex - 1);
            }
        });
        birdialog.setTitle("选择性别").setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(select_grade)) {
                            select_grade = gradeList.get(0);
                            grade_id = gradeIDList.get(0);
                        }
                        tv_sex.setText(select_grade);
                        select_grade = "";
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create()
                .show();
    }

    //体脂dialog
    public void show_pysical_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(99);
        np1.setValue(50);
        np1.setMinValue(0);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体脂").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_pysical.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
    //内脂dialog
    public void show_fat_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(200);
        np1.setValue(100);
        np1.setMinValue(0);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择体脂").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_fat.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
    //围度dialog
    public void show_circum_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择胸围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_circum.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_waistline_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择腰围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_waistline.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_hiplie_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择臀围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_hiplie.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_uparmgirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择上臂围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_uparmgirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

    public void show_upleggirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择大腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_upleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void show_doleggirth_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dimension_dialog, null);
        final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        np1.setMaxValue(220);
        np1.setValue(90);
        np1.setMinValue(50);
        np1.setWrapSelectorWheel(false);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setMinValue(0);
        np2.setWrapSelectorWheel(false);

        birdialog.setTitle("选择小腿围").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_doleggirth.setText(String.valueOf(np1.getValue()) + "." + String.valueOf(np2.getValue()));
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    //体重对话框
    public void show_weight_dialog() {
        final AlertDialog.Builder birdialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        final NumberPicker np = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np.setMaxValue(220);
        if (tv_sex.getText().toString().equals("男")) {
            np.setValue(150);
        } else if (tv_sex.getText().toString().equals("女")) {
            np.setValue(100);
        } else {
            np.setValue(150);
        }
        np.setMinValue(20);
        np.setWrapSelectorWheel(false);
        birdialog.setTitle("选择体重(单位：斤)").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (np.getValue() < 80) {
                    Dialog dialog1 = new AlertDialog.Builder(CansaiActivity.this)
                            .setMessage("体重单位为斤,是否确认数值?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                                            tv_weight.setError(null);
                                        }
                                    })

                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            show_weight_dialog();
                                        }
                                    }).create();
                    dialog1.show();
                    dialog1.setCanceledOnTouchOutside(false);
                } else {
                    tv_weight.setText(String.valueOf(np.getValue())); //set the value to textview
                    tv_weight.setError(null);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();

    }
}
