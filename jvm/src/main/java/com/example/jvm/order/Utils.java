package com.example.jvm.order;

public class Utils {

    private static String getClassName() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        String methodName = element.getMethodName();
        if ("<clinit>".equals(methodName)) {
            methodName = "类初始化";
        } else if ("<init>".equals(methodName)) {
            methodName = "对象初始化";
        }
        String fileName = element.getFileName();
        return fileName.substring(0, fileName.length() - 5) + "---" + methodName;
    }

    public static void printSelf() {
        System.out.println("" + getClassName());
    }
}
