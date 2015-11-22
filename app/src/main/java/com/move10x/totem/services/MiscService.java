package com.move10x.totem.services;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ravi on 10/22/2015.
 */
public class MiscService {

    private static String btnDOB;
    private String logTag = "miscService";
    public static Context context;
    public static Activity currentActivity;
    static InputMethodManager imm;
    public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public String shortenAddress(String address) {
        String shortAdd = "";
        String addArray[] = address.split(",");
        if (addArray.length > 3) {
            for (int i = 0; i < addArray.length - 1; i++) {
                if (i == 0)
                    shortAdd = shortAdd + addArray[i].trim();
                else
                    shortAdd = shortAdd + ", " + addArray[i].trim();
            }
        } else {
            shortAdd = address;
        }

        return shortAdd;
    }


    public static boolean isValidName(String pass) {
        if (pass != null && pass.length() > 1) {
            return true;
        }
        return false;
    }


    public static boolean isValidPin(String pin) {
        if (pin != null && pin.length() == 6) {
            return true;
        }
        return false;
    }


    public static boolean isValidEmail(String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidMobile(String phone) {


        return !(TextUtils.isEmpty(phone) || !(phone.matches("[789][0-9]{9}")));
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static void hideSoftKeyboard(TextView t) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(t.getWindowToken(), 0);
    }

}
