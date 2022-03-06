package com.ursaccharine.nscnotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class For_Notice extends AppCompatActivity {

    private EditText notice_titl,notice_desc;
    private Button notice_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_notice);

        notice_titl = findViewById(R.id.notice_titl);
        notice_desc = findViewById(R.id.notice_desc);
        notice_btn = findViewById(R.id.notice_btn);

        notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = notice_titl.getText().toString();
                String desc = notice_desc.getText().toString();

                Intent intent = new Intent(For_Notice.this,NoticeFragment.class);
                intent.putExtra("keytitle",title);
                intent.putExtra("keydesc",desc);
                startActivity(intent);
            }
        });

    }
}