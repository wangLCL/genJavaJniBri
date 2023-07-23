#include <kw_test_example_LuojiSo.h>
JNIEXPORT jboolean JNICALL Java_kw_test_example_LuojiSo_ifTest(JNIEnv* env, jobject object, jint age) {


//@line:4

    //@line:4
    if(age > 10){
        return true;
    }else{
        return false;
    }


    

}

JNIEXPORT jstring JNICALL Java_kw_test_example_LuojiSo_ifEslseTest(JNIEnv* env, jobject object, jint age) {


//@line:14

    char *aa = "xsddsds";
    char aaa[] = "aaaaa";
    std::string str = "xxxxxxxxxxxx";
    const char *xx = str.c_str();
    jstring ss = env->NewStringUTF(aaa);

    return ss;
     

}

