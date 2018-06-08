package com.softtek.lai.module.bodygame3.photowall;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.photowall.model.PublicDyModel;
import com.softtek.lai.module.bodygame3.photowall.model.TopicModel;
import com.softtek.lai.module.bodygame3.photowall.present.PublicDynamicManager2;
import com.softtek.lai.module.community.adapter.CommunityPhotoGridViewAdapter;
import com.softtek.lai.module.community.view.PreviewImageActivity;
import com.softtek.lai.module.picture.model.UploadImage;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.CursorChangeEditText;
import com.softtek.lai.widgets.CustomGridView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_publish_dy)
public class PublishDyActivity extends BaseActivity implements AdapterView.OnItemClickListener,ImageFileSelector.Callback,View.OnClickListener
        ,Validator.ValidationListener{
    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.cgv)
    CustomGridView cgv;
    @Required(order = 1,message = "请输入动态内容")
    @InjectView(R.id.et_content)
    CursorChangeEditText et_content;
    @InjectView(R.id.lv)
    ListView lv;

    List<TopicModel> topicModels =new ArrayList<>();
    EasyAdapter<TopicModel> topicAdapter;
    private List<UploadImage> images=new ArrayList<>();
    private ImageFileSelector imageFileSelector;
    CommunityPhotoGridViewAdapter adapter;
    HeadService headService;
    PublicDynamicManager2 manager;

    private int limit=9;
    private String classId;
    @Override
    protected void initViews() {
        cgv.setOnItemClickListener(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_title.setText("发布动态");
        tv_right.setText("发布");
        et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});
        et_content.setOnCursorChangeListener(new CursorChangeEditText.OnCursorChangeListener() {
            @Override
            public void onChange(int selStart, int selEnd) {
                int from=0;
                String content=et_content.getText().toString();
                int lastIndex=content.lastIndexOf("#");
                do {
                    //先获取第一个#号出现的下标
                    int firstIndex=content.indexOf("#",from);
                    //然后获取下一个#号出现的位置
                    int next=content.indexOf("#",firstIndex+1);
                    if(next==-1){
                        break;
                    }
                    //截取两个#号之间的字符
                    String sub=content.substring(firstIndex+1,next);
                    //将开始下标移动至下一个#号出现的位置
                    from=next;
                    for (TopicModel topic:topicModels){
                        if(sub.equals(topic.getWordKey())){
                            from=next+1;
                            //判断光标是否在话题文字范围内如果是则
                            Log.i("selStart="+selStart+";selEnd="+selEnd);
                            Log.i("firstIndex="+firstIndex+";next="+next);
                            if(selEnd>firstIndex&&selEnd<=next){
                                et_content.setSelection(content.length());
                                return;
                            }
                        }
                    }
                }while (from<lastIndex);
            }
        });

        et_content.addTextChangedListener(new TextWatcher() {

            String temp;
            Map<String,Boolean> index=new HashMap<String, Boolean>();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content=s.toString();
                if(s.toString().equals(temp)){
                    return;
                }
                temp=content;
                index.clear();
                SpannableStringBuilder builder=new SpannableStringBuilder(content);
                int from=0;
                int lastIndex=content.lastIndexOf("#");
                do {
                    //先获取第一个#号出现的下标
                    int firstIndex=content.indexOf("#",from);
                    //然后获取下一个#号出现的位置
                    int next=content.indexOf("#",firstIndex+1);
                    if(next==-1){
                        break;
                    }
                    //截取两个#号之间的字符
                    String sub=content.substring(firstIndex+1,next);
                    //将开始下标移动至下一个#号出现的位置
                    from=next;
                    for (TopicModel topic:topicModels){
                        if(sub.equals(topic.getWordKey())){
                            from=next+1;
                            builder.setSpan(new ForegroundColorSpan(0xFFFFA202), firstIndex, next+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                            index.put(topic.getWordKeyId(),true);
                            break;
                        }
                    }
                }while (from<lastIndex);
                et_content.setText(builder);
                et_content.setSelection(s.length());
                //更新列表
                for (TopicModel topic:topicModels){
                    Boolean validate=index.get(topic.getWordKeyId());
                    if(validate==null){
                        validate=new Boolean(false);
                    }
                    topic.setSelect(validate);
                }
                topicAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void initDatas() {
        doGetTopic();
        manager=new PublicDynamicManager2(images, this);
        classId=getIntent().getStringExtra("classId");
        ArrayList<UploadImage> uploadImages= getIntent().getParcelableArrayListExtra("uploadImages");
        if(uploadImages!=null&&!uploadImages.isEmpty()){
            limit-=uploadImages.size();
            for (UploadImage image:uploadImages){
                try {
                    image.setBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(image.getUri())));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                images.add(image);
            }
        }
        if(limit>0){
            images.add(new UploadImage(null, BitmapFactory.decodeResource(getResources(), R.drawable.add_img_icon)));
        }
        adapter=new CommunityPhotoGridViewAdapter(images,this);
        cgv.setAdapter(adapter);
        topicAdapter =new EasyAdapter<TopicModel>(this, topicModels,R.layout.item_topic) {
            @Override
            public void convert(ViewHolder holder, final TopicModel data, int position) {
                TextView tv_title_name=holder.getView(R.id.tv_title_name);
                tv_title_name.setText("#"+data.getWordKey()+"#");
                TextView tv_hot_num=holder.getView(R.id.tv_hot_num);
                tv_hot_num.setText(data.getThemeHot());
                CircleImageView cir_title=holder.getView(R.id.cir_title);
                final ImageView ck_select=holder.getView(R.id.ck_select);
                if(data.isSelect()){
                    ck_select.setBackgroundResource(R.drawable.selected);
                }else {
                    ck_select.setBackgroundResource(R.drawable.unselect);
                }
                if (!TextUtils.isEmpty(data.getThemePhoto()))
                {
                    Picasso.with(PublishDyActivity.this)
                            .load(AddressManager.get("photoHost")+data.getThemePhoto())
                            .fit()
                            .placeholder(R.drawable.default_icon_square)
                            .error(R.drawable.default_icon_square)
                            .into(cir_title);
                }else {
                    Picasso.with(PublishDyActivity.this)
                            .load(R.drawable.default_icon_square)
                            .placeholder(R.drawable.default_icon_square)
                            .into(cir_title);
                }
            }
        };
        lv.setAdapter(topicAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopicModel model=topicModels.get(position);
                model.setSelect(!model.isSelect());
                topicAdapter.notifyDataSetChanged(lv,position);
                if(model.isSelect()){
                    //在光标位置插入话题文字
                    int start=et_content.getSelectionStart();
                    SpannableString ss=new SpannableString("#"+model.getWordKey()+"#");
                    ss.setSpan(new ForegroundColorSpan(0xFFFFA202),0,ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    et_content.getText().insert(start,ss);
                }else {
                    //从后往前移除此话题
                    String content=et_content.getText().toString();
                    int from=content.indexOf("#");
                    int lastIndex=content.lastIndexOf("#");
                    do {
                        //先从后往前获取第二个#号出现的位置
                        int next=content.lastIndexOf("#",lastIndex-1);
                        if(next==-1){
                            break;
                        }
                        //截取两个#号之间的字符
                        String sub=content.substring(next+1,lastIndex);
                        //将开始下标移动至下一个#号出现的位置

                        if(sub.equals(model.getWordKey())){
                            et_content.getText().delete(next, lastIndex+1);
                            lastIndex=next-1;
                        }else {
                            lastIndex=next;
                        }
                    }while (lastIndex>from);
                }
            }
        });

        int px= DisplayUtil.dip2px(this,300);
        //*************************

        imageFileSelector=new ImageFileSelector(this);
        imageFileSelector.setOutPutImageSize(px,px);
        imageFileSelector.setQuality(30);
        imageFileSelector.setCallback(this);
    }

    private void doGetTopic() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        dialogShow("获取话题列表");
        headService.doGetPhWallTheme(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<TopicModel>>>() {
            @Override
            public void success(ResponseData<List<TopicModel>> listResponseData, Response response) {
                dialogDissmiss();
                int status=listResponseData.getStatus();
                switch (status) {
                    case 200:
                        if (listResponseData.getData() != null) {
                            topicModels.addAll(listResponseData.getData());
                            topicAdapter.notifyDataSetChanged();
                            ListViewUtil.setListViewHeightBasedOnChildren(lv);
                        }else {
                            Toast.makeText(PublishDyActivity.this,"获取话题列表数据为空",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                super.failure(error);
            }
        });
    }

    CharSequence[] options={"拍照","选择个人相册"};
    private static final int OPEN_PREVIEW=3;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UploadImage image=images.get(position);
        if(image.getImage()==null){
            new AlertDialog.Builder(this).setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        //检查权限
                        if(ActivityCompat.checkSelfPermission(PublishDyActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            //可以得到一个是否需要弹出解释申请该权限的提示给用户如果为true则表示可以弹
                            if(ActivityCompat.shouldShowRequestPermissionRationale(PublishDyActivity.this,Manifest.permission.CAMERA)){
                                //允许弹出提示
                                ActivityCompat.requestPermissions(PublishDyActivity.this,
                                        new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);

                            }else{
                                //不允许弹出提示
                                ActivityCompat.requestPermissions(PublishDyActivity.this,
                                        new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION);
                            }
                        }else {
                            imageFileSelector.takePhoto(PublishDyActivity.this);
                        }
                    }else if(which==1){
                        //打开图库
                        imageFileSelector.selectMutilImage(PublishDyActivity.this,limit);
                    }
                }
            }).create().show();
        }else{
            Intent intent=new Intent(this,PreviewImageActivity.class);
            intent.putExtra("uri", Uri.fromFile(image.getImage()));
            intent.putExtra("position",position);
            startActivityForResult(intent, OPEN_PREVIEW);
        }
    }
    private static final int CAMERA_PREMISSION=100;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PREMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                imageFileSelector.takePhoto(PublishDyActivity.this);

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==OPEN_PREVIEW){
                int position= data.getIntExtra("position", 0);
                images.remove(position);
                limit++;
                if(images.get(images.size()-1).getImage()!=null){
                    images.add(new UploadImage(null, BitmapFactory.decodeResource(getResources(), R.drawable.add_img_icon)));
                }
            }
            adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onSuccess(String file) {
        limit--;
        UploadImage image=new UploadImage();
        File outFile=new File(file);
        image.setImage(outFile);
        image.setBitmap(BitmapFactory.decodeFile(outFile.getAbsolutePath()));
        images.add(0, image);
        if(images.size()==10){
            images.remove(9);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMutilSuccess(List<String> files) {
        limit-=files.size();
        for (int i=files.size()-1;i>=0;i--){
            UploadImage image=new UploadImage();
            File outFile=new File(files.get(i));
            image.setImage(outFile);
            image.setBitmap(BitmapFactory.decodeFile(outFile.getAbsolutePath()));
            images.add(0, image);
        }
        if(images.size()==10){
            images.remove(9);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                //发布个人动态按钮
                validateLife.validate();
                break;

        }
    }

    @Override
    public void onValidationSucceeded() {
        SoftInputUtil.hidden(this);
        if(images.size()==1){
            new AlertDialog.Builder(this)
                    .setMessage("请选择至少一张图片上传")
                    .create().show();
            return;
        }
        //开始发布
        if(manager!=null){
            PublicDyModel model=new PublicDyModel();
            model.setContent(et_content.getText().toString().trim());
            model.setClassId(classId);
            StringBuilder builder=new StringBuilder("");
            for (TopicModel topic:topicModels){
                if(topic.isSelect()){
                    builder.append(topic.getWordKeyId());
                    builder.append(",");
                }
            }
            if(!TextUtils.isEmpty(builder)){
                builder.deleteCharAt(builder.length()-1);
            }
            Log.i("keyWord="+builder.toString());
            model.setKeywordId(builder.toString());
            model.setAccountid(UserInfoModel.getInstance().getUserId());
            manager.sendDynamic(model);
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        new AlertDialog.Builder(this)
                .setMessage(failedRule.getFailureMessage())
                .create().show();
    }
}
