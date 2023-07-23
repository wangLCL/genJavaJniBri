#include <kw_test_example_StringTest.h>
JNIEXPORT jstring JNICALL Java_kw_test_example_StringTest_getStr(JNIEnv* env, jobject object) {


//@line:4

        const char *ch = "hello";
    jstring js = env->NewStringUTF(ch);
    return js;
    

}

static inline jstring wrapped_Java_kw_test_example_StringTest_sendStr
(JNIEnv* env, jobject object, jstring obj_str, char* str) {

//@line:10


	char* str = (char*)env->GetStringUTFChars(obj_str, 0);

    strcat(str,"xx");
    jstring js = env->NewStringUTF(str);

	env->ReleaseStringUTFChars(obj_str, str);
    return js;
    
}

JNIEXPORT jstring JNICALL Java_kw_test_example_StringTest_sendStr(JNIEnv* env, jobject object, jstring obj_str) {
	char* str = (char*)env->GetStringUTFChars(obj_str, 0);

	jstring JNI_returnValue = wrapped_Java_kw_test_example_StringTest_sendStr(env, object, obj_str, str);

	env->ReleaseStringUTFChars(obj_str, str);

	return JNI_returnValue;
}

