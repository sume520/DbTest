package com.example.sun.dbtest;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBManager {
    //数据库连接常量
    public static final String DRIVER="com.mysql.jdbc.Driver";
    public static final String USER="root";
    public static final String PASS="";
    public static final String URL="jdbc:mysql://120.78.159.172:3306/test";

    //线程池
    ExecutorService executorService= Executors.newCachedThreadPool();

    // 静态成员，支持单态模式
    private static DBManager per = null;
    private Connection conn = null;
    private Statement stmt = null;

    private static final String TAG = "DBManager";

    // 单态模式-懒汉模式
    private DBManager() {
    }

    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // 加载驱动
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 连接数据库，获取句柄+对象
    public void connectDB() {
        Log.d(TAG, "connectDB: Connecting to database...");
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
            Log.d(TAG, "connectDB: Connect to database successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "connectDB: Connect to database faild.");
        }
    }

    // 关闭数据库 关闭对象，释放句柄
    public void closeDB() {
        Log.d(TAG, "closeDB: lose connection to database..");
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "closeDB: Close connection successful");
    }

    // 查询
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // 增添/删除/修改
    public int executeUpdate(String sql) {
        int ret = 0;
        try {
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
