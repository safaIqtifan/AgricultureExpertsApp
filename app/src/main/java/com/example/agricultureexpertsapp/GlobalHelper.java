package com.example.agricultureexpertsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.ProgressBar;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GlobalHelper {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Activity activity, String message) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    progressDialog = null;
                }
            });
        }
    }

    public static void hideProgressDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public static String GetTimeString(Date d) {

        DateFormat parser = new SimpleDateFormat("hh:mm aa", Locale.US);
        try {
            return parser.format(d);
        } catch (Exception e) {

        }
        return "";
    }

    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String getChatId(String myUserId, String friendUserId) {

        String idLong = "";
        for (char c : myUserId.toCharArray()) {
            int cc = c;
            idLong += cc;
        }

        String id2Long = "";
        for (char c : friendUserId.toCharArray()) {
            int cc = c;
            id2Long += cc;
        }

        System.out.println("Log idLong " + idLong);
        System.out.println("Log id2Long " + id2Long);
        BigDecimal id1 = new BigDecimal(idLong);
        BigDecimal id2 = new BigDecimal(id2Long);
//        double id1 = Double.parseDouble(idLong);
//        double id2 = Double.parseDouble(id2Long);
        if (id1.compareTo(id2) < 0) {
//        if (id1 < id2) {
            return idLong + "-" + id2Long;
        } else {
            return id2Long + "-" + idLong;
        }

    }

}
