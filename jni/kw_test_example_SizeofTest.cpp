#include "kw_test_example_SizeofTest.h"
#include<string>
#include<iostream>
JNIEXPORT jint JNICALL Java_kw_test_example_SizeofTest_catSizeof(JNIEnv* env, jobject object, jobject obj) {


//   if (env->IsInstanceOf(obj, env->FindClass("java/lang/String"))) {
//         // 处理传入的是 String 类型的情况
//         const char* str = env->GetStringUTFChars((jstring)obj, NULL);
//         // 使用字符串数据（注意要释放）
//         int size= sizeof(str);
//         env->ReleaseStringUTFChars((jstring)obj, str);
//         return size;
//     } else if (env->IsInstanceOf(obj, env->FindClass("java/lang/Integer"))) {
//         // 处理传入的是 Integer 类型的情况
//         jint intValue = env->CallIntMethod(obj, env->GetMethodID(object, "intValue", "()I"));
//         // 使用整数数据
//         return sizeof(intValue);
//     } else if (env->IsInstanceOf(obj, env->FindClass("java/lang/Double"))) {
//         // 处理传入的是 Double 类型的情况
//         jdouble doubleValue = env->CallDoubleMethod(obj, env->GetMethodID(object, "doubleValue", "()D"));
//         // 使用双精度数据
//         return sizeof(doubleValue);
//     } else {
//         // 处理未知类型的情况或其他错误
//         // ...
//         return -1;
//     }
    if (env->IsInstanceOf(obj, env->FindClass("java/lang/String"))) {
       
        const char* str11 = env->GetStringUTFChars((jstring)obj, NULL);
        // int size = strlen(str11);
    
        // env->ReleaseStringUTFChars((jstring)obj, str11);
    


//    const char* str11 = env->GetStringUTFChars((jstring)obj, NULL);
        // 使用字符串数据（注意要释放）
        int size = strlen(str11);
        // std::cout << "str11: " << str11 << " Size: " << size << std::endl;
        env->ReleaseStringUTFChars((jstring)obj, str11);
    
    
        return 1;
    }else{
        return 0;
    }
}

