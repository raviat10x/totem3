package com.move10x.totem.services;

import android.content.Context;

import com.move10x.totem.models.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi on 10/11/2015.
 */
public class DriverService {

    private Context _context;

    public DriverService(Context context) {
        this._context = context;
    }

    private List<Driver> driverList = null;

    public List<Driver> getDriverList() {

      if (driverList == null) {
            driverList = new ArrayList<Driver>();
            driverList.add(new Driver("1", "Samir", "Khan", "8888544466", Driver.DutyStatus_Available,
                    Driver.WorkStatus_Active, 19.006295, 72.821331, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("2","Balu", "Munde", "9938244573", Driver.DutyStatus_TOWARDS_LOADING,
                    Driver.WorkStatus_Active, 18.985519, 72.819958, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("3","Ramesh", "Pujari", "9938244573", Driver.DutyStatus_COMPLETE,
                    Driver.WorkStatus_Training, 18.955974, 72.831287, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("4","Sanjay", "Rajmane", "9938244573", Driver.DutyStatus_Offduty,
                    Driver.WorkStatus_Outside, 18.984870, 72.847767, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("5","Vijay", "Pal", "9938244573", Driver.DutyStatus_UNLOADING,
                    Driver.WorkStatus_Active, 18.932918, 72.826481, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("6","Devidas", "Vasant", "9938244573", Driver.DutyStatus_Available,
                    Driver.WorkStatus_Active, 19.203538, 72.853260, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("7","Prafulla", "Bodke", "9938244573", Driver.DutyStatus_TOWARDS_TOPICKUP,
                    Driver.WorkStatus_Active, 19.056601, 72.885876, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("8","Omkar", "Nalawade", "9938244573", Driver.DutyStatus_TOWARDS_DROP,
                    Driver.WorkStatus_Active, 19.116300, 72.932911, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("9","Mandar", "Humne", "9938244573", Driver.DutyStatus_Available,
                    Driver.WorkStatus_Active, 19.170789, 72.843990, "freelance", "Tata", "Ace", "max"));
            driverList.add(new Driver("10","Deepak", "Londhe", "9938244573", Driver.DutyStatus_TOWARDS_LOADING,
                    Driver.WorkStatus_Active, 19.107541, 72.880383, "freelance", "Tata", "Ace", "max"));
        }
        return driverList;
    }

    public Driver getDriverDetails(String uId) {
        for (Driver driver : getDriverList()) {
            if (uId.equals(driver.getuId())) {
                return driver;
            }
        }
        return null;
    }


}
