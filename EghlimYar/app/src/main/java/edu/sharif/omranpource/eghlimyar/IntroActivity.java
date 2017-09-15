package edu.sharif.omranpource.eghlimyar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import res.RequestSend;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Req().execute();

        //button config
        Button button = (Button) findViewById(R.id.loginbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"خوش آمدید!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
            }
        });
    }


}
