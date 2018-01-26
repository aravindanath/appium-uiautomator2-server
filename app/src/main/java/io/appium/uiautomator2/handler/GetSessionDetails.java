package io.appium.uiautomator2.handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.AccessibilityScrollData;
import io.appium.uiautomator2.model.AppiumUiAutomatorDriver;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class GetSessionDetails extends SafeRequestHandler {

        public GetSessionDetails(String mappedUri) {
            super(mappedUri);
        }

        @Override
        public AppiumResponse safeHandle(IHttpRequest request) {
            try {
                JSONObject result = new JSONObject();
                AccessibilityScrollData scrollData = AppiumUiAutomatorDriver.getInstance().getSession().getLastScrollData();
                HashMap<String, Integer> scrollDataMap;
                if (scrollData == null) {
                    scrollDataMap = null;
                } else {
                    scrollDataMap = scrollData.getAsMap();
                }
                JSONObject lastScrollData = new JSONObject(scrollDataMap);
                result.put("lastScrollData", lastScrollData);
                return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, result);
            } catch (JSONException e) {
                Logger.error("Exception while reading JSON: ", e);
                Logger.error(WDStatus.JSON_DECODER_ERROR, e);
                return new AppiumResponse(getSessionId(request), WDStatus.JSON_DECODER_ERROR, e);
            }
        }
}
