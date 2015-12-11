package com.move10x.totem.services;

import android.content.Context;
import android.util.Log;

import com.move10x.totem.models.Customer;
import com.move10x.totem.models.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi on 10/11/2015.
 */
public class CustomerService {

    private static final String TAG = "Customer Service";
    private Context _context;

    public CustomerService(Context context) {
        this._context = context;
    }


//    public ArrayList<Customer> getCustomerList() {
//
//        ArrayList<Customer> customerList = null;
//        if (customerList == null) {
//            customerList = new ArrayList<Customer>();
//            Log.d(TAG, "Inside getCustomerList()");
//            customerList.add(new Customer("1", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            Log.d(TAG, "Inside getCustomerList()");
//            customerList.add(new Customer("2", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("3", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("4", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("5", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("6", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("7", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("8", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("9", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//            customerList.add(new Customer("10", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                    "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company"));
//
//
//        }
//        return customerList;
//    }

//    public Customer getCustomerDetails(String uId) {
//
//        return new Customer("10", "SK", "Samir", "Khan", "8888544466", "samirKhan@move10x.com", "crm",
//                "0222424548", "Mumbai", "08Feb 2015", "09Feb 2015", "max", "Thane", "NaviMumbai", "400318", "Company");
//
//
//    }


}
