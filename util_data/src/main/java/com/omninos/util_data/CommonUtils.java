package com.omninos.util_data;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class CommonUtils {

    public static String EnableInternet = "1";
    private static Dialog progressDialog;

    public static void showProgress(Activity activity) {
        progressDialog = new Dialog(activity);
        progressDialog.setTitle("Please wait...");
        progressDialog.setContentView(R.layout.spinkit);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window dialogWindow = progressDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgress() {
        progressDialog.dismiss();
    }

    public static boolean isNetworkConnected(Context context) {
        boolean data;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            data = cm.getActiveNetworkInfo() != null;
        } catch (Exception e) {
            data = false;
        }
        return data;
    }

    public static void showSnackbarAlert(View view, String message) {
        TSnackbar snackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void CheckService(Activity activity) {
        activity.stopService(new Intent(activity, MyService.class));
        Intent intent = new Intent(activity, MyService.class);
        if (!isMyServiceRunning(intent.getClass(), activity)) {
            activity.startService(intent);
        }
    }

    private static boolean isMyServiceRunning(Class<?> serviceClass, Activity activity) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public static RequestBody getRequestBodyText(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    public static MultipartBody.Part getImgdData(String path, String parameter) {
        if (path != null) {
            File file = new File(path);
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return MultipartBody.Part.createFormData(parameter, file.getName(), requestFile);
        } else {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            return MultipartBody.Part.createFormData(parameter, "", requestFile);
        }
    }

    public static void ShowMsg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
