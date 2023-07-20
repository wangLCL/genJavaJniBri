package kw.test.example;

public class SizeofTest {
    public native int catSizeof(Object object);/*
        if (env->IsInstanceOf(obj, env->FindClass("java/lang/String"))) {
        const char* str11 = env->GetStringUTFChars((jstring)obj, NULL);
        int size = strlen(str11);
        env->ReleaseStringUTFChars((jstring)obj, str11);
        return size;
    }else if(env->IsInstanceOf(obj,env->FindClass("java/lang/Integer"))){
//        jint intValue = env->CallIntMethod(obj,);
         jmethodID m = env->GetMethodID(env->FindClass("java/lang/Integer"), "intValue", "()I");
         jint x = env->CallIntMethod(obj, m);
         return sizeof(x);
    }else if(env->IsInstanceOf(obj,env->FindClass("java/lang/Double"))){
 //        jint intValue = env->CallIntMethod(obj,);
          jmethodID m = env->GetMethodID(env->FindClass("java/lang/Double"), "doubleValue", "()D");
          jint x = env->CallIntMethod(obj, m);
          return sizeof(x);
     }

 */

}