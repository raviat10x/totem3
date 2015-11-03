package com.move10x.totem.services;

/**
 * Created by Ravi on 10/22/2015.
 */
public class MiscService {

    public String shortenAddress(String address) {
        String shortAdd = "";
        String addArray[] = address.split(",");
        if(addArray.length > 3) {
            for (int i = 0; i < addArray.length - 1; i++) {
                if (i == 0)
                    shortAdd = shortAdd + addArray[i].trim();
                else
                    shortAdd = shortAdd + ", " + addArray[i].trim();
            }
        }
        else {
            shortAdd = address;
        }

        return shortAdd;
    }
}
