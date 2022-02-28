package com.github.viclovsky.swagger.coverage;

public class SwaggerCoverageOptions {
    private static String pathPattern = "";
    private static boolean ignoreCall = false;

    public static void reset(){
        pathPattern = "";
        ignoreCall = false;
    }

    public static void setPathPattern(String pattern){
        pathPattern = pattern;
    }

    public static String getPathPattern(){
        return pathPattern;
    }

    public static void setIgnoreCall(boolean ignore){
        ignoreCall = ignore;
    }

    public static boolean getIgnoreCall(){
        return ignoreCall;
    }
}
