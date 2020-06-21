# Jetpack之DataBinding
[官方地址](https://developer.android.google.cn/topic/libraries/data-binding)
## 1.简述
DataBinding是一个用于将数据绑定到应用界面布局文件中view元素的组件，能够避免我们手动重复逻辑，例如findViewById、setText等。
## 2.简单使用入门
需要注意的是，DataBinding能运行到API 14（4.0）或更高版本上；Gradle（android 版本）需要1.5.0或以上。
### 2.1 启用DataBinding
在module下的build.gradle的android节点下启用DataBinding。

	android {
        ...
        dataBinding {
            enabled = true
        }
    }
### 2.2 使用

	<?xml version="1.0" encoding="utf-8"?>
	<layout>
	    <data>
	        <variable
	            name="chapter"
	            type="com.hudson.databindingtest.entity.Chapter" />
	    </data>
	
	    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
	        xmlns:app="http://schemas.android.com/apk/res-auto"
	        xmlns:tools="http://schemas.android.com/tools"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        tools:context=".MainActivity">
	
	        <TextView
	            android:layout_width="wrap_content"
	            android:layout_height="wrap_content"
	            android:text="@{chapter.name}"
	            app:layout_constraintBottom_toBottomOf="parent"
	            app:layout_constraintLeft_toLeftOf="parent"
	            app:layout_constraintRight_toRightOf="parent"
	            app:layout_constraintTop_toTopOf="parent" />
	
	    </androidx.constraintlayout.widget.ConstraintLayout>
	
	</layout>
上面中，根节点变成了layout，旗下有两个节点，分别是data和我们原始的布局的根节点，我们的数据源定义在data节点中。
在下面的TextView中，通过DataBinding把数据Chapter的name字段绑定到它的text属性上。
点击编译之后，DataBinding会生成一个Binding类，名字与布局文件名有关。一般情况下，Activity根布局情况下，使用DataBindingUtil.setContentView获取。如果是在View、Fragment、ListView或RecyclerView适配器中使用时可以通过指定生成类的inflate或者DataBindingUtil的inflate方法获取。

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	//        setContentView(R.layout.activity_main);
        ActivityMainBinding mainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
    }


在DataBinding绑定时，可以使用一些表达式：

**算术运算符 + - / * %**

**字符串连接运算符 +**

**逻辑运算符 && ||**

**二元运算符 & | ^**

**一元运算符 + - ! ~**

**移位运算符 >> >>> <<**

**比较运算符 == > < >= <=（请注意，< 需要转义为&lt ;，此处没有空格，主要时为了避免被自动转义）**

**instanceof**

**分组运算符 ()**

**字面量运算符 - 字符、字符串、数字、null**

**类型转换**

**方法调用**

**字段访问**

**数组访问 []**

**三元运算符 ?:**

例如：

	<data>
        <import type="android.view.View"/>
        <variable
            name="chapter"
            type="com.hudson.databindingtest.entity.Chapter" />
    </data>
	
	<TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text='@{chapter.name + "extend"}'
            android:visibility="@{chapter.visible == 1 ? View.VISIBLE : View.INVISIBLE}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
如果需要引用外部的类，可以通过在data中添加import标签来引入。如果有import的类出现冲突，那么需要使用alias来标识import的类的别名。另外import表示的是导入的类，并不能直接在后面的视图布局中作为变量使用，而只能使用其静态属性或者方法，要使用变量需要通过variable标签。
值得注意一些特殊情况，例如
#####1）表达式中有 <时，需要使用&lt ;（此处没有空格，主要时为了避免被自动转义）替换
#####2）字符串拼接时，外部应该用单引号，内部使用双引号
另外，DataBinding能够自动判断字段是否是null值，如果是，则不会显示内容；如果引用对象是null，引用的该对象的字段值时并不会报空指针异常，而是直接作为无数据处理。引用数据的初始值和类成员变量初始值类似，例如int默认为0.

### 2.3 集合类型的使用

	<import type="java.util.List"/>
	<variable
            name="datas"
            type="List&lt;String>" />

        <variable
            name="index"
            type="int" />
	<TextView
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            android:text="@{datas[index]}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
在Activity中可以使用生成的类直接通过set/get方法操作绑定的数据

	ActivityMainBinding mainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);

    mainBinding.setChapter(null);
    List<String> datas = new ArrayList<>();
    datas.add("jetpack");
    mainBinding.setDatas(datas);
    mainBinding.setIndex(0);
### 2.4资源的引用
这里仅简单介绍string资源的引用，其他参考官方文档。
因为我们普通的@string方式可以在不用dataBinding的情况下直接使用。所以这里以特殊情况，带参数的格式化字符串来示例。

	<string name="format_test">名字是%1$s</string>
布局文件中如下：
	
	<TextView
            android:text="@{@string/format_test(datas[index])}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
还有一种针对英文的复数形式，例如 1 time;  2 times，那么这时time后面得多一个s，这时使用@plurals，可以参考[这篇文章](https://www.jianshu.com/p/52c4cd7b431b)
还有其他资源的引用并混合表达式，参考官方文档
### 2.5 方法关联
官方文档中归类为：事件处理、方法引用、监听器绑定。大致其实都一样，都是对布局文件中一些属性和方法的关联，最常见的也就是onClick属性，有以下两种形式：

形式一：

	public class MyHandlers {
        public void onClickFriend(View view) { ... }
    }

	<TextView android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="@{user.firstName}"
               android:onClick="@{handlers::onClickFriend}"/>


形式二：
	
	public class Presenter {
        public void onSaveClick(Task task){}
    }

	<data>
        <variable name="task" type="com.android.example.Task" />
        <variable name="presenter" type="com.android.example.Presenter" />
    </data>
	<Button 
		android:layout_width="wrap_content" 
		android:layout_height="wrap_content"
        android:onClick="@{() -> presenter.onSaveClick(task)}" />

注意：上述onClick方法会传递一个view参数，你可以选择接收或不接受，如下所示：
	
	public class Presenter {
        public void onSaveClick(View view, Task task){}
    }

	android:onClick="@{(theView) -> presenter.onSaveClick(theView, task)}"
形式一也可以按照形式二的方式写，例如：
	
	<TextView
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            android:layout_marginBottom="30dp"
            android:onClick="@{(view) -> simpleViewModel.autoChangeNoNotify(view)}"
            android:layout_width="wrap_content"
            android:minWidth="120dp"
            android:layout_height="30dp"/>

另外，关联的方法如果有返回值，则必须保持一致。而且，如果请求的对象的值为null,如上面的simpleViewModel为null，系统也会自动帮助处理，而避免报空指针异常，引用类型返回 null，int 返回 0，boolean 返回 false，等等。
此外，还可以根据情况，返回不同的结果

	android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}"

注意：
1）内部表达式不宜太复杂，否则会使得布局代码难以阅读。

2）如果不同配置（例如横向或纵向）有不同的布局文件，则变量会合并在一起。这些布局文件之间不得存在有冲突的变量定义。

3）在生成的绑定类中，每个描述的变量都有一个对应的 setter 和 getter。在调用 setter 之前，这些变量一直采用默认的托管代码值，例如引用类型采用 null，int 采用 0，boolean 采用 false，等等。
### 2.5 特殊情况 include
我们可以通过include来包含其他布局文件，这时，子布局绑定的数据可以从父布局传递过来，如：
	
	<?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:bind="http://schemas.android.com/apk/res-auto">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <LinearLayout
           android:orientation="vertical"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <include layout="@layout/name"
               bind:user="@{user}"/>
           <include layout="@layout/contact"
               bind:user="@{user}"/>
       </LinearLayout>
    </layout>

## 3.数据类
上面来看，布局绑定的值来自与数据类，如果数据类还没初始化，那么布局中值将会使用该类型的默认值，null则不显示内容。那么如果我们数据类实例设置了数据，那布局怎么知道要刷新界面呢？这就要用到可观察的数据对象了。使用了可观察的数据对象之后，数据的任何变动，都能及时反映到界面上。
### 3.1 通过实现Observable接口
通过类实现Observable接口来允许注册监听器。系统为我们封装了BaseObservable类，我们实现该类，并在对应的set方法中调用notifyPropertyChanged方法，以及在get方法上添加@Bindable注解。

	private static class User extends BaseObservable {
        private String firstName;
        private String lastName;

        @Bindable
        public String getFirstName() {
            return this.firstName;
        }

        @Bindable
        public String getLastName() {
            return this.lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
            notifyPropertyChanged(BR.firstName);
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
            notifyPropertyChanged(BR.lastName);
        }
    }

数据绑定在模块包中生成一个名为 BR 的类，该类包含用于数据绑定的资源的 ID（注意：需要get对应方法有@Bindable注解才能使用）

### 3.2 使用已有可观察对象
系统提供了以下直接使用的Observable类来使用，避免每次都需要写大量set/get方法，并在内部增加notifyPropertyChanged操作或注解。
ObservableBoolean、ObservableByte、ObservableChar、ObservableShort、ObservableInt、ObservableLong、ObservableFloat、ObservableDouble、ObservableParcelable

例如：
	
	public class Chapter {
	    public final ObservableInt visibleObservable = new ObservableInt();
	    public int visible;
	}

	<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text='@{chapter.name + "extend"}'
        android:visibility="@{chapter.visibleObservable == 1 ? View.VISIBLE : View.INVISIBLE}"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


	Chapter chapter = new Chapter();
    mainBinding.setChapter(chapter);//mainBinding是一个binding对象
    chapter.visibleObservable.set(1);

使用了这种方式后，变量必须设置为只读属性 public final ，并通过get/set方法操作数据。
此外，还有[可观察的集合](https://developer.android.google.cn/topic/libraries/data-binding/observability#observable_Collections)，就不再赘述了。

## 4.绑定类
在布局文件中绑定了视图属性和相关的数据类之后，通过make project构建项目，便会在编译期动态生成一个ViewDataBinding类，该类名与布局文件名关联。在前面我们已经说过，可以通过生成类的inflate或者DataBindingUtil来获取实例。常见形式有：
	
	 ListItemBinding binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
 

    ListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false);

	MyLayoutBinding binding = MyLayoutBinding.bind(viewRoot);


	View viewRoot = LayoutInflater.from(this).inflate(layoutId, parent, attachToParent);
    ViewDataBinding binding = DataBindingUtil.bind(viewRoot);

在生成类中，我们可以直接引用布局文件中定义的id控件，这种方式避免了通过findViewById来获取view。
另外，生成类可以直接通过set/get方法来设置和获取数据。
### 4.1特殊情况
#### 1）ViewStub
见[官方文档](https://developer.android.google.cn/topic/libraries/data-binding/generated-binding#viewstubs)

或者[这篇文章](https://blog.csdn.net/zhixuan322145/article/details/51817207)中关于ViewStub部分的介绍
#### 2)RecyclerView的ViewHolder的绑定
在RecyclerView的adapter中，onBindViewHolder中如果还不知道数据类的具体类型，这时可以通过动态变量来完成。通过BindingViewHolder来获取ViewDataBinding类，并调用setVariable方法类设置item，注意：前提是我们约定了每一项布局中对应的数据类variable变量名为item。
[UnknownTypeAdapter.java](https://github.com/HudsonAndroid/JetPackFeature/blob/master/app/src/main/java/com/hudson/jetpackfeature/databinding/adapter/UnknownTypeAdapter.java)

	@Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        //约定绑定的数据类变量名为item。
        holder.getBinding().setVariable(BR.item, mDatas.get(position));
        holder.getBinding().executePendingBindings();
    }
### 4.3 自定义绑定类名和位置
通过给data标签设置class属性来决定生成的绑定类名和位置

	在当前模块的 databinding 包中生成 ContactItem 绑定类：
	<data class="ContactItem">
        …
    </data>

	您可以在类名前添加句点和前缀，从而在其他文件包中生成绑定类。以下示例在模块包中生成绑定类：
	<data class=".ContactItem">
        …
    </data>

	您还可以使用完整软件包名称来生成绑定类。以下示例在 com.example 包中创建 ContactItem 绑定类
	<data class="com.example.ContactItem">
        …
    </data>
## 5.绑定适配器
一般情况下，我们视图属性绑定数据类时，数据类中包含了该属性的set/get方法，同时视图中包含了对应的视图属性的set/get方法，绑定类会自动帮我们把视图属性和数据类属性绑定。例如，下面的数据变量chapter所对应的类中包含了name属性，同时也存在对应的set/get方法（如果是public类型或者ObservableXX类，则没有），而text属性对应了视图的setText和getText方法。

	android:text="@{chapter.name}"

但也有例外：
1）即使不存在具有给定名称的属性，数据绑定也会起作用。然后，您可以使用数据绑定为任何 setter 创建特性。例如，支持类 DrawerLayout 没有任何属性，但有很多 setter。以下布局会自动将 setScrimColor(int) 和 setDrawerListener(DrawerListener) 方法分别用作 app:scrimColor 和 app:drawerListener 特性的 setter：

	<android.support.v4.widget.DrawerLayout
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        app:scrimColor="@{@color/scrim}"
	        app:drawerListener="@{fragment.drawerListener}">
2）如果属性名和setter方法不匹配，这时我们可以通过@BindingMethods注解来绑定属性名和setter方法。该注释使用在类上，且可以包含多个 BindingMethod 注释，每个注释对应一个绑定规则。 例如，下面使得视图属性tint与视图的setImageTintList绑定

 	@BindingMethods({
           @BindingMethod(type = "android.widget.ImageView",
                          attribute = "android:tint",
                          method = "setImageTintList"),
    })
3）如果视图不存在对应的set方法时，可以在视图中创建静态方法，结合@BindingAdapter注解，并传入视图view实例，来修改相关属性，例如，paddingLeft没有对应的setPaddingLeft方法。

	@BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
      view.setPadding(padding,
                      view.getPaddingTop(),
                      view.getPaddingRight(),
                      view.getPaddingBottom());
    }
第一个参数必须为对应的视图实例，后面的参数对应值类型。上面在注解中指明了对应的视图属性。我们可以传入多个值参数，例如：

[示例](https://github.com/HudsonAndroid/JetPackFeature/blob/master/app/src/main/java/com/hudson/jetpackfeature/databinding/bindingAdapter/BindingAdapterConfigs.java)

	@BindingAdapter(value = {"imageUrl","errorDrawable"},requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable error){
        Glide.with(imageView.getContext()).load(url).error(error).into(imageView);
    }
第二个参数对应了app:imageUrl，第三个参数对应了app:error，这里使用了app的命名空间。同时可以设置requireAll来决定布局文件中是否需要设置所有值才调用到该方法，例如上面设置false，那么可以只设置一个，也会调用到上面的loadImage方法中来。例如：
	
	<ImageView app:imageUrl="@{venue.imageUrl}" app:error="@{@drawable/venueError}" />

注意：
1）数据绑定库在匹配时会忽略自定义命名空间。
2）出现冲突时，绑定适配器会替换默认的数据绑定适配器。
3）由于BindingAdapter注解的是静态方法，因此通常用一个类统一管理。
### 5.1 BindingAdapter新旧值判断
上面的@BindingAdapter方式可以选择性在处理程序，根据旧值和新值来决定如何生效，但是定义的方法中头一个参数是旧值，后一个参数是新值。

	@BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
      if (oldPadding != newPadding) {
          view.setPadding(newPadding,
                          view.getPaddingTop(),
                          view.getPaddingRight(),
                          view.getPaddingBottom());
       }
    }
### 5.2 BindingAdapter的设置监听方法
前面通过onClick属性设置监听点击事件对应的方法时，本质是View.OnClickListener接口中的onClick方法。实际上，在布局文件中指定该视图的相关监听方法时，必须对应的是具有一种抽象方法的接口或抽象类。例如：

[示例](https://github.com/HudsonAndroid/JetPackFeature/blob/master/app/src/main/java/com/hudson/jetpackfeature/databinding/bindingAdapter/BindingAdapterConfigs.java)

	@BindingAdapter("android:onLayoutChange")
    public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener oldValue,
           View.OnLayoutChangeListener newValue) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        if (oldValue != null) {
          view.removeOnLayoutChangeListener(oldValue);
        }
        if (newValue != null) {
          view.addOnLayoutChangeListener(newValue);
        }
      }
    }


	<View android:onLayoutChange="@{() -> handler.layoutChanged()}"/>

还有一种特殊情况是，如果该监听接口或者抽象类有多个抽象方法时，该如何指定呢？如下接口，有两个方法。
	
	@TargetApi(VERSION_CODES.HONEYCOMB_MR1)
    public interface OnViewDetachedFromWindow {
      void onViewDetachedFromWindow(View v);
    }

    @TargetApi(VERSION_CODES.HONEYCOMB_MR1)
    public interface OnViewAttachedToWindow {
      void onViewAttachedToWindow(View v);
    }

这时需要在视图中对不同的方法设置不同的视图属性，并在BindingAdapter注解的静态方法中处理接口或抽象类的注册和解注册，使得这两个方法合并到一个接口或抽象类中。例如：

	@BindingAdapter({"android:onViewDetachedFromWindow", "android:onViewAttachedToWindow"}, requireAll=false)
    public static void setListener(View view, OnViewDetachedFromWindow detach, OnViewAttachedToWindow attach) {
        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1) {
            OnAttachStateChangeListener newListener;
            if (detach == null && attach == null) {
                newListener = null;
            } else {
                newListener = new OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        if (attach != null) {
                            attach.onViewAttachedToWindow(v);
                        }
                    }
                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        if (detach != null) {
                            detach.onViewDetachedFromWindow(v);
                        }
                    }
                };
            }

            OnAttachStateChangeListener oldListener = ListenerUtil.trackListener(view, newListener,
                    R.id.onAttachStateChangeListener);
            if (oldListener != null) {
                view.removeOnAttachStateChangeListener(oldListener);
            }
            if (newListener != null) {
                view.addOnAttachStateChangeListener(newListener);
            }
        }
    }
上面操作中处理了旧监听器的解注册。

### 5.3自定义转换
见[官方文档](https://developer.android.google.cn/topic/libraries/data-binding/binding-adapters#custom-conversions)

## 6.双向数据绑定
默认情况下，我们只需要在之前绑定的表达式前添加一个=号，就能实现双向绑定，需要数据类中按照之前的规定添加@Bindable注解到getter上，同时有相关的setter方法（通过ObservableField的类似）

	<CheckBox
        android:id="@+id/rememberMeCheckBox"
        android:checked="@={viewmodel.rememberMe}"
    />
但有一种情况，如果我们通过绑定适配器来完成，那么这时视图的属性就会出现和视图内部的setter方法不对应的情况，例如上面例子中的

	@BindingAdapter(value = {"imageUrl","errorDrawable"},requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable error){
        Glide.with(imageView.getContext()).load(url).error(error).into(imageView);
    }
这个时候，我们可以布局文件中绑定数据时，通过app:imageUrl和app:errorDrawable属性配置图片地址和错误状态时的placeholder。那么这种情况下，ImageView中并没有setImageUrl和setErrorDrawable方法，那么这个时候，就没法实现双向绑定了，因为无法从视图上获取imageUrl和errorDrawable，编译器将会报错提示没有对应的方法存在。
这种情况下，我们一般需要自定义控件来完成从视图获取现有视图上的数据。前面数据驱动视图的时候，在数据类中的setter方法中，我们通过notifyPropertyChanged方法来通知视图刷新，数据绑定通过@BindingAdapter注解的方法找到对应的绑定操作，在通过getter获取数据并刷新数据到视图上。
可见，在数据驱动视图的情况下，有三个必要条件：
###### 1）数据类中setter中内容变动时发出变动通知
###### 2）根据绑定的方法，调用视图类中的setter来准备刷新界面
###### 3）视图通过数据类的getter获取到数据类的数据
那么相反地，如果视图需要驱动数据变动，那么，应该也是类似的：
###### 1）视图类中与数据相关的内容变动时，需要发出变动通知
###### 2）调用数据类的setter来准备写回数据
###### 3）数据类可以通过视图类的getter获取到视图中的现有数据
首先解决第一个问题，当视图中的内容变动时，需要通知给数据类。但值得注意的是，如果这个内容变动来源与数据类，应该避免通知，以避免出现数据类又重新通知给视图类，导致死循环。那这个怎么判断数据是否来源于数据类，如果增加参数这会导致所有地方都需要这样处理。更高效的做法是，通过判断当前的数据变动内容和之前是否一致，如果一致，那么不再刷新视图，这样如果数据变动来源于数据类，即使第一次不同会重新通知回数据类，后续将不会再刷新视图。系统提供了InverseBindingListener接口来通知到数据类，需要我们通过@BindingAdapter注解对应属性的静态方法，并第二个参数以InverseBindingListener作为参数，绑定数据工具会协助我们完成视图驱动数据的工作。例如下面中，我们想要监听的是content属性。

	public class MyTextView extends AppCompatTextView {
	    private InverseBindingListener mInverseBindingListener;
	
	    @Override
	    public void setText(CharSequence text, BufferType type) {
	        super.setText(text, type);
	        if(mInverseBindingListener != null){
	            mInverseBindingListener.onChange();//通知数据类，当前视图内容变动了
	        }
	    }

		//用于数据变动刷新到视图view
		@BindingAdapter({"content"})
	    public static void setContent(MyTextView view,String content){
	        String current = view.getText().toString();
	        if(content != null && !content.equals(current)){//2
	            view.setText(content);
	        }
	    }
		
	    @BindingAdapter(value = {"contentAttrChanged"},requireAll = false)
	    public static void setContentChanged(MyTextView view, InverseBindingListener listener){//1
	        view.mInverseBindingListener = listener;
	    }
	}
上面注释1处，定义了一个方法，并通过@BindingAdapter指明了该方法所对应的监听属性变动值（该值由一般我们监听的属性+"AttrChanged"组成，且与后面的getter方法指定的event对应），这样当绑定工具就能够找到该方法，给视图变动设置监听器。注释2处判断数据是否相同，避免陷入死循环。


看第二个问题前，先看看之前在上面例子中，loadImage方法使用了数据刷新图片内容到ImageView上，那相应地，如果双向绑定，也就是说图片显示的地址变动了，应该给数据类设置新值。由于ImageView并没有存储图片地址，因此如果需要真正双向绑定，仅仅使用ImageView是无法完成的，而需要自定义控件，并能通过绑定工具拿到数据类setter方法来写回数据。


第三个问题中，需要通过@InverseBindingAdapter注解来指明通过视图获取内容的方法。

	@InverseBindingAdapter(attribute = "content",event = "contentAttrChanged")
    public static String getContent(MyTextView view){
        return view.getText().toString();
    }
最后需要注意，别忘了在布局文件中添加双向绑定表达式（增加=号）。

	public class MyTextView extends AppCompatTextView {
	    private InverseBindingListener mInverseBindingListener;
	
	    @Override
	    public void setText(CharSequence text, BufferType type) {
	        super.setText(text, type);
	        if(mInverseBindingListener != null){
	            mInverseBindingListener.onChange();//通知数据类，当前视图内容变动了
	        }
	    }
	
	    //用于数据变动刷新到视图view
	    @BindingAdapter({"content"})
	    public static void setContent(MyTextView view,String content){
	        String current = view.getText().toString();
	        if(content != null && !content.equals(current)){//避免进入死循环
	            view.setText(content);
	        }
	    }
	
	    @InverseBindingAdapter(attribute = "content",event = "contentAttrChanged")
	    public static String getContent(MyTextView view){
	        return view.getText().toString();
	    }
	
	    @BindingAdapter(value = {"contentAttrChanged"},requireAll = false)
	    public static void setContentChanged(MyTextView view, InverseBindingListener listener){
	        view.mInverseBindingListener = listener;
	    }
	}

上面方式中，都是在静态方法中添加注解完成的，还有另一种方式，直接在自定义类内部定义与属性名关联（在上面的方式中，方法名没有限定与属性值相关）的方法，这时需要通过@InverseBindingMethods类注解指定属性值、设置监听器和获取视图值的方法，而且必须严格按照注解中的值设定方法名。例如上面例子中可以改成如下：

	@InverseBindingMethods(@InverseBindingMethod(type = MyTextView.class,
    event = "contentAttrChanged",//方法名必须是setContentAttrChanged
    method = "getContent",//方法名必须是getContent
    attribute = "content"))//方法名必须是setContent
	public class MyTextView extends AppCompatTextView {
	    private InverseBindingListener mInverseBindingListener;
	
	    @Override
	    public void setText(CharSequence text, BufferType type) {
	        super.setText(text, type);
	        if(mInverseBindingListener != null){
	            mInverseBindingListener.onChange();//通知数据类，当前视图内容变动了
	        }
	    }
	
	    public void setContent(String content){
	        String current = getText().toString();
	        if(content != null && !content.equals(current)){
	            setText(content);
	        }
	    }
	
	    public String getContent(){
	        return getText().toString();
	    }
	
	    public void setContentAttrChanged(InverseBindingListener listener){
	        if(listener != null){
	            mInverseBindingListener = listener;
	        }else{
	            mInverseBindingListener = null;
	        }
	    }
	}

上述两种方式的[源码](https://github.com/HudsonAndroid/JetPackFeature/blob/master/app/src/main/java/com/hudson/jetpackfeature/databinding/twoWayBind/MyTextView.java)
