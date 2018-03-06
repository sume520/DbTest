package com.example.sun.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_conn;
    private Button bt_close;
    private Button bt_query;
    private Button bt_add;
    private Button bt_modify;
    private Button bt_del;
    private DBManager dbManager;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonInit();
        dbManager=DBManager.createInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect:
                dbManager.connectDB();
                break;
            case R.id.close:
                dbManager.closeDB();
                break;
            case R.id.query:
                ResultSet rs;
                rs=dbManager.executeQuery("select * from student");
                printResult(rs);
                break;
            case R.id.delete:
                break;
            case R.id.add:
                break;
            case R.id.modify:
                break;
        }
    }

    private void buttonInit(){
        bt_add=(Button)findViewById(R.id.add);
        bt_add.setOnClickListener(this);
        bt_close=(Button)findViewById(R.id.close);
        bt_close.setOnClickListener(this);
        bt_conn=(Button)findViewById(R.id.connect);
        bt_conn.setOnClickListener(this);
        bt_del=(Button)findViewById(R.id.delete);
        bt_del.setOnClickListener(this);
        bt_modify=(Button)findViewById(R.id.modify);
        bt_modify.setOnClickListener(this);
        bt_query=(Button)findViewById(R.id.query);
        bt_query.setOnClickListener(this);
    }

    private void printResult(ResultSet rs) {
        if(rs!=null){
            try {
                while(rs.next()){
                    int Id=rs.getInt(1);
                    String user=rs.getString(2);
                    String pass=rs.getString(3);
                    Log.d(TAG, "printResult: Id: "+Id+" user: "+user+" password: "+pass);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Log.d(TAG, "printResult: rs is null");
        }
    }
}
