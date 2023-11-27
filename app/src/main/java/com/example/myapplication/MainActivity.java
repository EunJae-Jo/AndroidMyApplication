package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.helper.HttpHelper;

public class MainActivity extends AppCompatActivity {
    TextView sign;
    EditText id;
    EditText pw;
    androidx.appcompat.widget.AppCompatButton loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // id
        id = findViewById(R.id.editID);
        id.setText("cust-10"); // 테스트 용..
        // pw
        pw = findViewById(R.id.ediPassword);
        pw.setText("System123!"); // 테스트 용..

        //회원가입 버튼
        sign = findViewById(R.id.signin);

        //회원가입 버튼 클릭시, 회원가입 페이지로 이동
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPassword.class);
            startActivity(intent);
        });

        //로그인 버튼
        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(v -> {
            // 로그인 시도.
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 로그인 시도.
                    String token = HttpHelper.getInstance().login( id.getText().toString(), pw.getText().toString());
                    if(token != null) { // 로그인 성공
                        toast("로그인 성공");
                        pageMove(MainPage.class);
                    } else { // 로그인 실패
                        toast("Id 또는 Password를 다시 입력해 주세요.");
                    }
                }

            });

            th.start();

            //Intent intent = new Intent(this, MainPage.class);
            //startActivity(intent);
        });
    }

    // 페이지 이동.
    private void pageMove(java.lang.Class<?> cls){
        Intent intent = new Intent(MainActivity.this, MainPage.class);
        startActivity(intent);
    }

    private void toast(String message) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }
}