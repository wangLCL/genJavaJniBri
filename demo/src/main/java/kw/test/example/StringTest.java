package kw.test.example;

public class StringTest {
    public native String getStr();/*
        const char *ch = "hello";
    jstring js = env->NewStringUTF(ch);
    return js;
    */

    public native String sendStr(String str);/*

	char* str = (char*)env->GetStringUTFChars(obj_str, 0);

    strcat(str,"xx");
    jstring js = env->NewStringUTF(str);

	env->ReleaseStringUTFChars(obj_str, str);
    return js;
    */
}
