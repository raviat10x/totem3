package com.move10x.totem.models;

/**
 * Created by Ravi on 11/4/2015.
 */
public class AppError {

    private String message;
    private String activity;
    private String errorDetails;
    private String additionalInfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        String response = "";
        response += "messgae: " + (getMessage() == null ? "null" : getMessage());
        response += "activity: " + (getMessage() == null ? "null" : getActivity());
        response += "errorDetails: " + (getMessage() == null ? "null" : getErrorDetails());
        response += "additionalInfo: " + (getMessage() == null ? "null" : getAdditionalInfo());
        return response;
    }
}
