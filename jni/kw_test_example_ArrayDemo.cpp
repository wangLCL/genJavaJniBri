#include <kw_test_example_ArrayDemo.h>
#include<iostream>
static inline jintArray wrapped_Java_kw_test_example_ArrayDemo_readArr
(JNIEnv* env, jclass clazz, jintArray obj_a, int* a) {

//@line:5

    jboolean isCopy;
    jint *jint1 = env->GetIntArrayElements(obj_a,&isCopy);
    jint xx[10];
    for(int i = 0; i< 10;i++){
        std::cout<< jint1[i]<<std::endl;
        xx[i] = jint1[i]*2;
    }



    jintArray out = env->NewIntArray(10);
    env->SetIntArrayRegion(out, 0, 10, xx);
    return out;
    
}

JNIEXPORT jintArray JNICALL Java_kw_test_example_ArrayDemo_readArr(JNIEnv* env, jclass clazz, jintArray obj_a) {
	int* a = (int*)env->GetPrimitiveArrayCritical(obj_a, 0);

	jintArray JNI_returnValue = wrapped_Java_kw_test_example_ArrayDemo_readArr(env, clazz, obj_a, a);

	env->ReleasePrimitiveArrayCritical(obj_a, a, 0);

	return JNI_returnValue;
}

