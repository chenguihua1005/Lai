package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.Contact;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_search_contact)
public class SearchContactActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.edit)
    EditText edit;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;
    @InjectView(R.id.lv)
    ListView lv;

    @InjectView(R.id.pb)
    ProgressBar pb;

    List<Contact> contacts = new ArrayList<>();
    EasyAdapter<Contact> adapter;

    @Override
    protected void initViews() {
        overridePendingTransition(0, 0);
        tv_title.setText("邀请小伙伴");
        ll_left.setVisibility(View.INVISIBLE);
        tv_cancel.setOnClickListener(this);
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //判断是不是搜索
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                     /*隐藏软键盘*/
                    SoftInputUtil.hidden(SearchContactActivity.this);
                    UserModel user = UserInfoModel.getInstance().getUser();
                    if (edit.length() == 0) {
                        edit.requestFocus();
                        edit.setError(Html.fromHtml("<font color=#FFFFFF>请输入姓名/手机号/资格证号</font>"));
                        return false;
                    } else if (edit.getText().toString().equals(user.getMobile())) {
                        edit.requestFocus();
                        edit.setError(Html.fromHtml("<font color=#FFFFFF>无此用户</font>"));
                        return false;
                    }
                    pb.setVisibility(View.VISIBLE);
                    ZillaApi.NormalRestAdapter.create(MoreService.class)
                            .sendSearch(UserInfoModel.getInstance().getToken(),
                                    edit.getText().toString(),
                                    new RequestCallback<ResponseData<List<Contact>>>() {
                                        @Override
                                        public void success(ResponseData<List<Contact>> data, Response response) {
                                            try {
                                                pb.setVisibility(View.GONE);
                                                if (data.getStatus() == 200) {
                                                    if (data.getData() != null && !data.getData().isEmpty()) {
                                                        contacts.clear();
                                                        Iterator<Contact> itr = data.getData().iterator();
                                                        while (itr.hasNext()) {
                                                            Contact contact = itr.next();
                                                            if (contact.getAccountId() == UserInfoModel.getInstance().getUserId() || contact.getMobile().equals(edit.getText())) {
                                                                itr.remove();
                                                            }
                                                        }
                                                        contacts.addAll(data.getData());
                                                        adapter.notifyDataSetChanged();
                                                    } else {
                                                        Util.toastMsg("无此用户只支持精确查询");
                                                    }
                                                } else {
                                                    Util.toastMsg(data.getMsg());
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            try {
                                                pb.setVisibility(View.GONE);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            super.failure(error);
                                        }
                                    });
                    return true;
                }
                return false;
            }
        });
        edit.addTextChangedListener(this);
    }

    @Override
    protected void initDatas() {
        adapter = new EasyAdapter<Contact>(this, contacts, R.layout.search_contact_item) {
            @Override
            public void convert(ViewHolder holder, Contact data, int position) {
                TextView tv_name = holder.getView(R.id.tv_name);
                tv_name.setText(data.getUserName());
                TextView tv_certificate = holder.getView(R.id.tv_certificate);
                tv_certificate.setText("(");
                tv_certificate.append(TextUtils.isEmpty(data.getCertification()) ? "暂无资格证号" : data.getCertification());
                tv_certificate.append(")");
                TextView tv_phone = holder.getView(R.id.tv_phone);
                tv_phone.setText(data.getMobile());
                CircleImageView head_image = holder.getView(R.id.head_image);
                if (TextUtils.isEmpty(data.getPhoto())) {
                    Picasso.with(SearchContactActivity.this).load(R.drawable.img_default).into(head_image);
                } else {
                    Picasso.with(SearchContactActivity.this).load(AddressManager.get("photoHost") + data.getPhoto())
                            .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
                }
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contacts.get(i);
                Intent intent = new Intent(SearchContactActivity.this, InvitationSettingActivity.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                intent.putExtra("inviterId", contact.getAccountId());
                intent.putExtra("inviterHXId", contact.getHXAccountId());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                SoftInputUtil.hidden(SearchContactActivity.this);
                finish();
                overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        isOut = true;
    }

    boolean isOut;

    @Override
    public void afterTextChanged(Editable editable) {
        isOut = false;
        String text = editable.toString();
        contacts.clear();
        if (text.trim().length() == 0) {
            adapter.notifyDataSetChanged();
            return;
        }
        Set<String> keys = ContactsActivity.datas.keySet();
        for (String key : keys) {
            if (isOut) {
                break;
            }
            for (Contact contact : ContactsActivity.datas.get(key)) {
                if (isOut) {
                    break;
                }
                if (contact.getMobile().contains(text)
                        || contact.getUserName().contains(text)
                        || contact.getMobile().contains(text)
                        || TextUtils.isEmpty(contact.getUserEn()) ? false : contact.getUserEn().toLowerCase().contains(text.toLowerCase())) {
                    contacts.add(contact);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
