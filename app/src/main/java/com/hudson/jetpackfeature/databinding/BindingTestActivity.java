package com.hudson.jetpackfeature.databinding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.hudson.jetpackfeature.R;
import com.hudson.jetpackfeature.databinding.adapter.TypeOneAdapter;
import com.hudson.jetpackfeature.databinding.adapter.TypeTwoAdapter;
import com.hudson.jetpackfeature.databinding.adapter.UserAdapterRecyclerView;
import com.hudson.jetpackfeature.databinding.entity.ImageData;
import com.hudson.jetpackfeature.databinding.entity.UnknownTypeOneData;
import com.hudson.jetpackfeature.databinding.entity.UnknownTypeTwoData;
import com.hudson.jetpackfeature.databinding.entity.User;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class BindingTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_binding_test);
        ActivityBindingTestBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_binding_test);
        bindAdapterTest(binding);
//        bindUserType(binding);
        bindUnknownTypeTwo(binding);
    }

    private void bindAdapterTest(ActivityBindingTestBinding binding){
        ImageData imageData = new ImageData();
        imageData.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592741441500&di=5ce51f59cda30b5770dfcf741720b9bd&imgtype=0&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D1034079650%2C495529395%26fm%3D214%26gp%3D0.jpg");
        imageData.setErrorDrawable(new ColorDrawable(Color.RED));
        binding.setImageData(imageData);
    }


    private void bindUnknownTypeOne(ActivityBindingTestBinding binding){
        TypeOneAdapter adapter = new TypeOneAdapter();
        binding.rvContainer.setAdapter(adapter);
        List<UnknownTypeOneData> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            UnknownTypeOneData item = new UnknownTypeOneData();
            item.mValue.set(i);
            datas.add(item);
        }
        adapter.refresh(datas);
    }

    private void bindUnknownTypeTwo(ActivityBindingTestBinding binding){
        TypeTwoAdapter adapter = new TypeTwoAdapter();
        binding.rvContainer.setAdapter(adapter);
        List<UnknownTypeTwoData> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            UnknownTypeTwoData item = new UnknownTypeTwoData();
            item.mFlag.set((i & 1) == 0);
            datas.add(item);
        }
        adapter.refresh(datas);
    }

    private void bindUserType(ActivityBindingTestBinding binding){
        final UserAdapterRecyclerView adapter = new UserAdapterRecyclerView();
        binding.rvContainer.setAdapter(adapter);
        fetchData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.refresh(users);
            }
        });
    }

    //这一部分内容不应该写在activity内部，应该由ViewModel通过Repository后台获取，此处仅为演示
    private LiveData<List<User>> fetchData(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("namePrefix" + i);
            user.setAge(i);
            users.add(user);
        }
        MutableLiveData<List<User>> liveData = new MutableLiveData<>();
        liveData.setValue(users);
        return liveData;
    }


    public static void start(Context from){
        from.startActivity(new Intent(from,BindingTestActivity.class));
    }
}
