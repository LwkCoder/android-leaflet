package com.lwkandroid.lib.core.log;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 打印Json日志的工具.
 */
class JsonLog
{
    static final String BRACT = "{";
    static final String BRACKET = "[";

    static void printJson(String tag, String msg, String headString)
    {
        String message;
        try
        {
            if (BRACT.startsWith(msg))
            {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(KLog.JSON_INDENT);
            } else if (BRACKET.startsWith(msg))
            {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(KLog.JSON_INDENT);
            } else
            {
                message = msg;
            }
        } catch (JSONException e)
        {
            message = msg;
        }

        LogUtil.printLine(tag, true);
        message = headString + KLog.LINE_SEPARATOR + message;
        String[] lines = message.split(KLog.LINE_SEPARATOR);
        for (String line : lines)
        {
            Log.d(tag, "║ " + line);
        }
        LogUtil.printLine(tag, false);
    }
}
