## android

LaiApp Android 版本

###开发工具
工具：Android Studio

版本号：2.0 beta 6

Android SDK版本号：23.0.1

Gradle 插件版本号：2.0.0-beta6

## Dev

###Include


**compile 'com.rainim:libzilla:1.0.59'**


### LibZilla


If you want to know more about libzilla,please visit <https://github.com/zillachan/LibZilla>

###Application
```
1.You need an Application,and onCreate(),you need call 

new Zilla().setCallBack(this).initSystem(this);

for example:

public class ZillaApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        new Zilla().setCallBack(this).initSystem(this);
    }

    /**
     * init
     *
     * @param context Context
     */
    @Override
    public void onInit(Context context) {
        initApi();
        DBHelper.getInstance().setDbUpgradeListener(this);
    }

    /**
     * Config API info
     */
    private void initApi() {
        ZillaApi.NormalRestAdapter = ZillaApi.getRESTAdapter(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
            }
        });
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate(SQLiteDatabase db)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade(SQLiteDatabase db)");
    }
}

```
###DB

####Config
1. assets/config/system.properties 
```
dbversion:if upgrade the database,dbversion+1.
dbname:the name of database.
```
2. assets/config/upgrade.sql
```
upgrade sql by hand
```
3. upgrade
```
if you change po Model, the table will change automatic.
```

####@Id
```
@Id annotation
Each po need an Id annotation,otherwise it will throw a RuntimeException.

@Table("t_user")
public class User implements Serializable {

    @Id
    private int id;

    private String name;

    private String email;

    private String address;

    public User() {

    }
    getter
    setter
}

By default if the type of the Id is Integer,it will AUTOINCREMENT.
```
####@Table
```
@Table annotation
By default,Each po can create a table by it's className,if you add @Table annotion on a class,the table name will be the name you give.

@Table("t_user")
public class User implements Serializable {
	...
	...
}
    
```
####DBOperator
```
A Singleton,It provide the CURD operations for datebase.

//Delete all rows
DBOperator.getInstance().deleteAll(User.class);

//save
User userModel = new User();
userModel.setName("user1");
userModel.setEmail("user1@example.com");
userModel.setAddress("user1 address");
DBOperator.getInstance().save(userModel);

//save list
List<User> userList = new ArrayList<User>();
for (int i = 0; i < 1000; i++) {
    User u = new User();
    u.setName("name" + i);
    u.setEmail("name" + i + "@example.com");
    u.setAddress("address" + i);
    userList.add(u);
}
DBOperator.getInstance().saveList(userList);

//Query
User user1 = DBOperator.getInstance().query(User.class,"address = ?",new String[]{"address1"});
Log.i("user1:"+user1.toString());

DBOperator.getInstance().update(user1);

//query all rows
List<User> users = DBOperator.getInstance().queryAll(User.class);
for (User u : users) {
    Log.i(u.toString());
}
    
```

###File
####AddressManager
    
```
Read file assets/config/address.properties,It will be called automatic by ZillaApi    
```

####FileHelper
Some static functons

1. createFile
2. deleteFile
3. copyFile
4. saveFile
5. formateFileSize
6. getRealPath by uri


####PropertiesManager
```
Read file assets/config/system.properties,you can also add your own property in system.properties,and read it by PropertiesManager.
```

####SharedPreferenceService

```
The packaging of SharedPreference,you only need call put or get
```

###LifeCircle
LifeCircle Manager,It provide the life circle manager for any object implements the ILifeCircle interface.

```
You want control an loading dialog when activity onCreate,init the dialog;whe the activity call onDestroy,dismiss the dialog.

public class MainActivity extends Activity {

    @LifeCircleInject
    public LoadingDialog loadingDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        	...
            LifeCircle.onCreate(this);
            ...
        }
        ...
        @Override
    protected void onDestroy() {
        super.onDestroy();
        LifeCircle.onDestory(this);
    }
}


And then you can call loadingDialog.show() or loadingDialog.dismiss() anywhere.
```

###ZillaApi
A packaging of retrofit.

###ZillaAdapter
This provide a very effictive function for AdapterView(ListView,ExpandableView,GridView)

```
When you use ListView,you need only 3 things
1.Create your listView item Model
2.Create your listView item viewholder
3.Create your adapter by ZillaAdapter
4.set listview's adapter

protected void initDatas() {
        for (int i = 0; i < 20; i++) {
            User userModel = new User();
            userModel.setName("User" + i);
            userModel.setEmail("userModel" + i + "@example.com");
            userModel.setAddress("address" + i);
            userList.add(userModel);
        }
        adapter = new ZillaAdapter<User>(this, userList, R.layout.user_item, ViewHolder.class);
        listView.setAdapter(adapter);
    }
    
Your viewholder:the name must be same as the model.

static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.email)
        TextView email;
        @InjectView(R.id.address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
}
    
```
This can also support checkbox,button event,linearlayout hide/visiable,image,ratingbar.

###ZillaBinding

An util for binding model value to view

```
Views:

	@InjectView(R.id.user_id)
    @InjectBinding("id")
    TextView userId;

    @InjectView(R.id.user_name)
    @InjectBinding("name")
    TextView userName;

    @InjectView(R.id.user_email)
    @InjectBinding("email")
    TextView userEmail;

    @InjectView(R.id.user_address)
    @InjectBinding("address")
    TextView userAddress;
    
Binding:

	userModel = new User();
    userModel.setId(123);
    userModel.setName("zilla")
    userModel.setEmail("zillachan@gamil.com");
    userModel.setAddress("beijing china");

    ZillaBinding.binding(this, userModel);

```

###ZListView


```
@InjectLayout(R.layout.activity_zlistviewtest)
public class ZListViewActivity extends BaseActivity {

    private ZListViewWraper<Org> xListViewWraper;
    GitHubService service;

    @Override
    protected void initViews() {
        service = ZillaApi.NormalRestAdapter.create(GitHubService.class);
        xListViewWraper = new ZListViewWraper<Org>(getWindow().getDecorView(), R.layout.item_zlistview, ViewHolder.class) {
            @Override
            public void loadData() {
                service.getRepos("octokit", new Callback<List<Org>>() {
                    @Override
                    public void success(List<Org> orgs, Response response) {
                        xListViewWraper.setModelList(orgs);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        xListViewWraper.refreshFail();
                    }
                });
            }

            @Override
            public void loadMore() {
                service.getRepos("octokit", new Callback<List<Org>>() {
                    @Override
                    public void success(List<Org> orgs, Response response) {
                        xListViewWraper.addModelList(orgs);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        xListViewWraper.refreshFail();
                    }
                });
            }
        };
    }

    @Override
    protected void initDatas() {

    }

    static class ViewHolder {
        @InjectView(R.id.item_org_name)
        TextView name;
        @InjectView(R.id.item_org_full_name)
        TextView full_name;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

```

###Others
####BusProvider
If you use otto before,then you can understand it easy.

### Config


#### PackageName

```
PackageName can be configed in config.properties file;
```

#### Gradle

```
VERSION_NAME=1.0
VERSION_CODE=1
BUILD_TOOLS_VERSION=23.0.2
COMPILE_SDK_VERSION=23
MINI_SDK_VERSION = 16
GRADLE_PLUGIN_VERSION = 2.0.0-beta6
```

#### LocalInfo

```
sdk.dir=/Users/Zilla/Documents/Dev/android
#keystore info
keystore.password=xxxx
key.password=xxx
```

###TODO

###ZillaAdapter ImageView's PlaceHolder

### Customise NetError

### Finish All Service