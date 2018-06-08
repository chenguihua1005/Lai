package com.softtek.lai.module.bodygame3.conversation.view;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.conversation.database.ContactUtil;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.softtek.lai.module.bodygame3.head.view.PersonDetailActivity2;
import com.softtek.lai.utils.SoftInputUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_search_conversation)
public class ContactSearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private static final String TAG = "ContactSearchActivity";
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.edit)//输入搜索字段
            EditText edit;
    @InjectView(R.id.tv_cancel)
    TextView tv_cancel;
    @InjectView(R.id.lv)
    ListView lv;

    @InjectView(R.id.pb)
    ProgressBar pb;


    List<ChatContactModel> contacts = new ArrayList<>();
    List<ChatContactModel> contacts_static = new ArrayList<>();

    EasyAdapter<ChatContactModel> adapter;

    @Override
    protected void initViews() {
        overridePendingTransition(0, 0);
        tv_title.setText("通讯录搜索");
        edit.setHint("请输入姓名或手机号进行搜索");

        ll_left.setVisibility(View.INVISIBLE);
        tv_cancel.setOnClickListener(this);
//        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                //判断是不是搜索
//                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                     /*隐藏软键盘*/
//                    SoftInputUtil.hidden(ContactSearchActivity.this);
//                    //lv.setVisibility(View.INVISIBLE);
//                    pb.setVisibility(View.VISIBLE);
//
//                    //从数据库获取数据
//                    ContactDao dao = new ContactDao(ContactSearchActivity.this);
//                    List<ChatContactModel> contacts_temp = dao.getAllConatct();
//                    Log.i(TAG, "contacts_temp22 = " + contacts_temp);
//
//                    if (contacts_temp != null && !contacts_temp.isEmpty()) {
//                        contacts.clear();
//                        contacts.addAll(contacts_temp);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    pb.setVisibility(View.GONE);
//
//
//                    return true;
//                }
//                return false;
//            }
//        });
        edit.addTextChangedListener(this);
    }

    @Override
    protected void initDatas() {
//        ContactDao dao = new ContactDao(ContactSearchActivity.this);
//        contacts = dao.getAllConatct();
//        contacts_static.clear();
//        contacts_static.addAll(contacts);


        contacts_static.clear();
        contacts_static.addAll(ContactUtil.getInstance().getAllConatct());

        adapter = new EasyAdapter<ChatContactModel>(this, contacts, R.layout.expandable_child_contact_item) {
            @Override
            public void convert(ViewHolder holder, ChatContactModel data, int position) {
                TextView name = holder.getView(R.id.tv_name);
                name.setText(data.getUserName());
                CircleImageView head_image = holder.getView(R.id.head_image);

                if (TextUtils.isEmpty(data.getPhoto())) {
                    Picasso.with(ContactSearchActivity.this).load(R.drawable.img_default).into(head_image);
                } else {
                    Picasso.with(ContactSearchActivity.this).load(AddressManager.get("photoHost") + data.getPhoto())
                            .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
                }
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChatContactModel model = contacts.get(i);
                Intent intent = new Intent(ContactSearchActivity.this, PersonDetailActivity2.class);
                intent.putExtra("isFriend", 1);//1： 好友
                intent.putExtra("AccountId", model.getAccountId());
                intent.putExtra("HXAccountId", model.getHXAccountId());
                intent.putExtra("UserName", model.getUserName());
                intent.putExtra("AFriendId", model.getAFriendId());

                intent.putExtra("comeFrom", Constants.FROM_CONTACT);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                SoftInputUtil.hidden(ContactSearchActivity.this);
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

        Log.i(TAG, "text = " + text + ": " + text.trim().length());
        if (text.trim().length() == 0) {
            contacts.clear();
//            contacts.addAll(contacts_static);
            adapter.notifyDataSetChanged();
            return;
        }
//        Set<String> keys = ContactFragment.datas.keySet();
//        for (String key : keys) {
//            if (isOut) {
//                break;
//            }
//            for (ChatContactModel contact : ContactFragment.datas.get(key)) {
//                if (isOut) {
//                    break;
//                }
//                if (contact.getMobile().contains(text)
//                        || contact.getUserName().contains(text)
//                        || contact.getMobile().contains(text)
//                        || TextUtils.isEmpty(contact.getUserEn()) ? false : contact.getUserEn().toLowerCase().contains(text.toLowerCase())) {
//                    contacts.add(contact);
//                }
//            }
//        }

        List<ChatContactModel> contacts_temp = new ArrayList<>();

        for (ChatContactModel contact : contacts_static) {
            if (contact.getMobile().contains(text)
                    || contact.getUserName().contains(text)
                    || contact.getUserName().toLowerCase().contains(text.toLowerCase())) {
                contacts_temp.add(contact);
            }
        }

        contacts.clear();
        contacts.addAll(contacts_temp);
        adapter.notifyDataSetChanged();
    }
}
