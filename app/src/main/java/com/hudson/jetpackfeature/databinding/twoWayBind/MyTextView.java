package com.hudson.jetpackfeature.databinding.twoWayBind;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

/**
 * Created by Hudson on 2020/6/21.
 */
@InverseBindingMethods(@InverseBindingMethod(type = MyTextView.class,
    event = "contentAttrChanged",//方法名必须是setContentAttrChanged
    method = "getContent",//方法名必须是getContent
    attribute = "content"))//方法名必须是setContent
public class MyTextView extends AppCompatTextView {
    private InverseBindingListener mInverseBindingListener;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

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

//    //用于数据变动刷新到视图view
//    @BindingAdapter({"content"})
//    public static void setContent(MyTextView view,String content){
//        String current = view.getText().toString();
//        if(content != null && !content.equals(current)){//避免进入死循环
//            view.setText(content);
//        }
//    }
//
//    @InverseBindingAdapter(attribute = "content",event = "contentAttrChanged")
//    public static String getContent(MyTextView view){
//        return view.getText().toString();
//    }
//
//    @BindingAdapter(value = {"contentAttrChanged"},requireAll = false)
//    public static void setContentChanged(MyTextView view, InverseBindingListener listener){
//        view.mInverseBindingListener = listener;
//    }

}
