package com.matrixdeveloper.tajika.parser;

import com.matrixdeveloper.tajika.model.BasicBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AkshCabs.com on 08 June, 2017.
 * Package com.matrixdeveloper.akshdriver.net.parsers
 * Project AkshCabsDriver
 */

public class RequestParser {

    public BasicBean parseBasicResponse(String wsResponseString) {

        BasicBean basicBean = new BasicBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            basicBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                basicBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                basicBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        basicBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        basicBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        basicBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        basicBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    basicBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    basicBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    basicBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    basicBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if (dataObj.has("id")) {
                            basicBean.setRequestID(dataObj.optString("id"));
                        }
                        if (dataObj.has("service_id")) {
                            basicBean.setServiceID(dataObj.optString("trip_id"));
                        }
                        if (dataObj.has("service_status")) {
                            basicBean.setServiceStatus(dataObj.optInt("trip_status"));
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return basicBean;
    }

}
