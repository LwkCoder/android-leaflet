package com.lwkandroid.lib.core.log;


import com.lwkandroid.lib.core.BuildConfig;
import com.lwkandroid.lib.core.utils.common.AppUtils;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import androidx.annotation.Nullable;

/**
 * Github开源项目KLog
 */
public final class KLog
{
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "KLog";
    private static final String SUFFIX = ".java";

    public static final int JSON_INDENT = 4;

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static final int JSON = 0x7;
    private static final int XML = 0x8;

    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final int STACK_TRACE_INDEX_4 = 4;

    private static String mGlobalTag = AppUtils.getAppName();
    private static boolean IS_SHOW_LOG = BuildConfig.DEBUG;

    public static void init(boolean isShowLog)
    {
        IS_SHOW_LOG = isShowLog;
    }

    public static void init(boolean isShowLog, @Nullable String tag)
    {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
    }

    public static void v()
    {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg)
    {
        printLog(V, null, msg);
    }

    public static void v(String tag, Object... objects)
    {
        printLog(V, tag, objects);
    }

    public static void d()
    {
        printLog(D, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg)
    {
        printLog(D, null, msg);
    }

    public static void d(String tag, Object... objects)
    {
        printLog(D, tag, objects);
    }

    public static void i()
    {
        printLog(I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg)
    {
        printLog(I, null, msg);
    }

    public static void i(String tag, Object... objects)
    {
        printLog(I, tag, objects);
    }

    public static void w()
    {
        printLog(W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg)
    {
        printLog(W, null, msg);
    }

    public static void w(String tag, Object... objects)
    {
        printLog(W, tag, objects);
    }

    public static void e()
    {
        printLog(E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg)
    {
        printLog(E, null, msg);
    }

    public static void e(String tag, Object... objects)
    {
        printLog(E, tag, objects);
    }

    public static void a()
    {
        printLog(A, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg)
    {
        printLog(A, null, msg);
    }

    public static void a(String tag, Object... objects)
    {
        printLog(A, tag, objects);
    }

    public static void json(String jsonFormat)
    {
        printLog(JSON, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat)
    {
        printLog(JSON, tag, jsonFormat);
    }

    public static void xml(String xml)
    {
        printLog(XML, null, xml);
    }

    public static void xml(String tag, String xml)
    {
        printLog(XML, tag, xml);
    }

    public static void file(File targetDirectory, Object msg)
    {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg)
    {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg)
    {
        printFile(tag, targetDirectory, fileName, msg);
    }

    public static void debug()
    {
        printDebug(null, DEFAULT_MESSAGE);
    }

    public static void debug(Object msg)
    {
        printDebug(null, msg);
    }

    public static void debug(String tag, Object... objects)
    {
        printDebug(tag, objects);
    }

    public static void trace()
    {
        printStackTrace();
    }

    private static void printStackTrace()
    {
        if (!IS_SHOW_LOG)
        {
            return;
        }

        Throwable tr = new Throwable();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        String message = sw.toString();

        String[] traceString = message.split("\\n\\t");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String trace : traceString)
        {
            if (trace.contains("at com.lwkandroid.lib.core.log.KLog"))
            {
                continue;
            }
            sb.append(trace).append("\n");
        }
        String[] contents = wrapperContent(STACK_TRACE_INDEX_4, null, sb.toString());
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(D, tag, headString + msg);
    }

    private static void printLog(int type, String tagStr, Object... objects)
    {
        if (!IS_SHOW_LOG)
        {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type)
        {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                BaseLog.printDefault(type, tag, headString + msg);
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                XmlLog.printXml(tag, msg, headString);
                break;
            default:
                break;
        }
    }

    private static void printDebug(String tagStr, Object... objects)
    {
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(D, tag, headString + msg);
    }

    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg)
    {
        if (!IS_SHOW_LOG)
        {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }

    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects)
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0)
        {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$"))
        {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0)
        {
            lineNumber = 0;
        }

        String tag = (tagStr == null ? className : tagStr);
        if (StringUtils.isTrimEmpty(tag))
        {
            tag = mGlobalTag;
        }

        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects)
    {
        if (objects.length > 1)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++)
            {
                Object object = objects[i];
                if (object == null)
                {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else
                {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else if (objects.length == 1)
        {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        } else
        {
            return NULL;
        }
    }
}
