package com.tencent.customer.deskdemo;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tencent.customer.deskdemo.databinding.ActivityMainBinding;
import com.tencent.customer.deskdemo.debug.GenerateTestUserSig;
import com.tencentcloud.tencentcloudcustomer.Callbacks.TencentAiDeskCustomerLoginCallback;
import com.tencentcloud.tencentcloudcustomer.Config.TencentAiDeskCustomerLoginConfig;
import com.tencentcloud.tencentcloudcustomer.TencentAiDeskCustomer;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        initBtnEvent();
    }

    private void initBtnEvent() {
        Button btnShowDesk = findViewById(R.id.btnDesk);
        if (btnShowDesk == null) {
            return;
        }
        int sdkAppID = GenerateTestUserSig.SDKAPPID;
        String userID = "TestForAndroid";
        String userSign = GenerateTestUserSig.genTestUserSig(userID);
        TencentAiDeskCustomerLoginConfig config = new TencentAiDeskCustomerLoginConfig(); // config可选填

        btnShowDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                TencentAiDeskCustomer.getInstance().login(context, sdkAppID, userID, userSign, config, new TencentAiDeskCustomerLoginCallback() {
                    @Override
                    public void onSuccess() {
                        // 登录成功后跳转到聊天页面
                        startActivity(TencentAiDeskCustomer.getInstance().getTencentCloudCustomerChatIntent(context));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        Toast.makeText(v.getContext(), "login error: " + code + " " + desc, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}