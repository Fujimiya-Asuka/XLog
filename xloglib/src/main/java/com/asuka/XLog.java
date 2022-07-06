package com.asuka;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class XLog {
    private static volatile XLog mXLog;
    private static String appName;
    private static String logPath;
    private Boolean enableConsoleLog=true;
    private Boolean enableDiskLog=false;
    private static Boolean autoDeleteFile=false;
    private static int deleteDay=3;
    private static int diskLogLevel=2;
    private static int logFileSize=500;

    public static XLog getInstance(){
        if (mXLog ==null){
            synchronized (XLog.class){
                if (mXLog ==null){
                    mXLog =new XLog();
                }
            }
        }
        return mXLog;
    }

    public void init(Context context){
        appName=context.getPackageName();
        logPath=context.getFilesDir().getAbsolutePath()+ File.separatorChar+"logger";
        if (mXLog.enableConsoleLog){
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
        if (mXLog.enableDiskLog){
            Logger.addLogAdapter(new DiskLogAdapter());
        }
    }

    public static String getLogPath(){
         return logPath;
    }

    /**
     * @param b 是否控制台输出 默认是
     */
    public XLog enableConsoleLog(Boolean b){
         mXLog.enableConsoleLog=b;
         return mXLog;
    }

    /**
     * @param b 是否磁盘输出 默认否
     */
    public XLog enableDiskLog(Boolean b){
        mXLog.enableDiskLog=b;
        return mXLog;
    }

    /**
     * 设置磁盘日志记录最小级别，默认全部记录
     * VERBOSE = 2; DEBUG = 3; INFO = 4; WARN = 5; ERROR = 6; ASSERT = 7;
     */
    public XLog setDiskLogLevel(int level){
        if (level<2||level>7){
            throw new IllegalArgumentException("参数必须在2到7之间");
        }
        diskLogLevel=level;
        return mXLog;
    }

    public static int getDiskLogLevel(){
        return diskLogLevel;
    }

    /**
     *  设置日志单文件大小 默认500kb
     *  范围100-10,000KB （10MB）
     */
    public XLog setLogFileSize(int size){
        if (size<100||size>10000){
            throw new IllegalArgumentException("参数非法 范围要在100-10000之间");
        }
        logFileSize=size;
        return mXLog;
    }

    public static int getLogFileSize(){
        return logFileSize;
    }

    public static List<File> getAllLogFiles(){
        File file = new File(logPath);
        if (file.exists()){
            ArrayList<File> files = new ArrayList<>();
            Collections.addAll(files,file.listFiles());
            return files;
        }else {
            return null ;
        }
    }

    /**
     * 删除log文件
     */
    public static void deleteLogFile(){
        if (new File(logPath).exists()){
            for (File file : Objects.requireNonNull(new File(logPath).listFiles())) {
                file.delete();
            }
        }
    }


    /**
     * 设置开启自动删除日志
     * @param day 删除多少天前的日志
     */
    public XLog setAutoDeleteFile(int day){
        if (day<0){
            throw new IllegalArgumentException("参数day不能小于0");
        }
        deleteDay=day;
        autoDeleteFile=true;
        return mXLog;
    }

    /**
     * 删除x天前的日志，不填默认3天
     * @param x 多少天前
     */
    private static void deleteLogBeforeXDays(int x) {
        File[] files = new File(logPath).listFiles();
        if (files.length>0) {
            for (File file : files) {
                if (new Date(file.lastModified() + x * 24 * 3600 * 1000).before(new Date())) {
                    Logger.d("删除日志");
                }
            }
        }
    }

    public static void v(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.v(message);
    }

    public static void d(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.d(message);
    }

    public static void i(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.i(message);
    }

    public static void w(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.w(message);
    }

    public static void e(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.e(message);
    }

    public static void json(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.json(message);
    }

    public static void xml(String message){
        if (autoDeleteFile){
            deleteLogBeforeXDays(deleteDay);
        }
        Logger.xml(message);
    }

}
