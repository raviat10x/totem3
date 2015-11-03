package com.move10x.totem.services;

import android.util.Log;

import com.move10x.totem.models.User;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ravi on 10/9/2015.
 */
public class UserService {

    /*public User GetLocalUserDetails(){

    }*/

    public User Login(String userId, String password){
        Map<String, String> returnMap = new HashMap<String, String>();
        try {
            Map<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("userId", userId);
            paramMap.put("password", password);

            // Every webservice varies in value of Rest_WsConstants(webservice path) and paramMap-key/value mapping

            //HttpPost postReq = createRequest(Rest_WsConstants.LOGIN_WS.path, paramMap);
/*
            StringEntity entity = new StringEntity(addInfoData);
            postReq.setEntity(entity);

            HttpResponse wsResponse = wsClient.execute(postReq);
*/

            //returnMap = processResponse(wsResponse);

        }catch (Exception e) {
            Log.e("userService", "Error while login, reason:" + e.getMessage());
        }
        return null;
    }
}
