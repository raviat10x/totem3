package com.move10x.totem.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class UserFunctions {

    private JSONParser jsonParser;

    private static String gcmURL = "http://www.move10x.com/webservice/gcm.php";
    private static String gcmAcknowledgeURL = "http://www.move10x.com/webservice/";
    private static String sendGcmRegId_tag = "sendGcmRegId";
    private static String gcmAcknowledge_tag = "pushBack";

    private static String GOOGLE_API_SERVER_KEY = "AIzaSyA3nhT9WNvVAZ9oRt4Ct2iLC32A7lXWp04";

    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    
    public JSONObject sendGcmRegId(String token, String crmUid){
        // Building Parameters
         List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", sendGcmRegId_tag));
        params.add(new BasicNameValuePair("regId", token));
        params.add(new BasicNameValuePair("crmId", crmUid));
        JSONObject json = jsonParser.getJSONFromUrl(gcmURL, params);
        return json;
    }

    public JSONObject acknowledgeGCM(String gcmRegid, String booking_ref, String crmUid) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", gcmAcknowledge_tag));
        params.add(new BasicNameValuePair("crmRegId", gcmRegid));
        params.add(new BasicNameValuePair("booking_ref", booking_ref));
        params.add(new BasicNameValuePair("crmId", crmUid));

        JSONObject json = jsonParser.getJSONFromUrl(gcmAcknowledgeURL,params);
        return json;
    }
}
