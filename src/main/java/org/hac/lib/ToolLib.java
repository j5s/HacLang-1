package org.hac.lib;

import org.hac.function.NativeFunction;
import org.hac.util.EncodeUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ToolLib {
    private static final String LIB_NAME = "tool";
    public static List<NativeFunction> lib = new ArrayList<>();

    static {
        try {
            Method exec = ToolLib.class.getMethod("exec", String.class);
            lib.add(new NativeFunction(LIB_NAME + LibManager.SEP + "exec", exec));
            Method getPowershellCommand = ToolLib.class.getMethod("getPowershellCommand", String.class);
            lib.add(new NativeFunction(LIB_NAME + LibManager.SEP + "getPowershellCommand", getPowershellCommand));
            Method getBashCommand = ToolLib.class.getMethod("getBashCommand", String.class);
            lib.add(new NativeFunction(LIB_NAME + LibManager.SEP + "getBashCommand", getBashCommand));
            Method getStringCommand = ToolLib.class.getMethod("getStringCommand", String.class);
            lib.add(new NativeFunction(LIB_NAME + LibManager.SEP + "getStringCommand", getStringCommand));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String exec(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            StringBuilder outStr = new StringBuilder();
            java.io.InputStreamReader resultReader = new java.io.InputStreamReader(process.getInputStream());
            java.io.BufferedReader stdInput = new java.io.BufferedReader(resultReader);
            String s;
            while ((s = stdInput.readLine()) != null) {
                outStr.append(s).append("\n");
            }
            return outStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPowershellCommand(String cmd) {
        return EncodeUtil.getPowershellCommand(cmd);
    }

    public static String getBashCommand(String cmd) {
        return EncodeUtil.getBashCommand(cmd);
    }

    public static String getStringCommand(String cmd) {
        return EncodeUtil.getStringCommand(cmd);
    }
}
