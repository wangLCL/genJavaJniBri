#include <org_example_Main.h>
JNIEXPORT jint JNICALL Java_org_example_Main_add(JNIEnv* env, jclass clazz, jint a, jint b) {


//@line:8

		return a + b;
	

}

JNIEXPORT jint JNICALL Java_org_example_Main_min(JNIEnv* env, jclass clazz, jint a, jint b) {


//@line:12

		return a - b;
	

}

JNIEXPORT jint JNICALL Java_org_example_Main_max(JNIEnv* env, jclass clazz, jint a, jint b) {


//@line:16

	    return a > b ? a : b;
//		return a + b;
	

}

static inline jintArray wrapped_Java_org_example_Main_readArr
(JNIEnv* env, jclass clazz, jintArray obj_a, int* a) {

//@line:21

 jboolean isCopy;
    jint *jint1 = env->GetIntArrayElements(obj_a,&isCopy);
    jintArray out = env->NewIntArray(10);
      std::cout<<"-0----------------------"<<std::endl;
    env->SetIntArrayRegion(out, 0, 10, jint1);
    return out;
    
}

JNIEXPORT jintArray JNICALL Java_org_example_Main_readArr(JNIEnv* env, jclass clazz, jintArray obj_a) {
	int* a = (int*)env->GetPrimitiveArrayCritical(obj_a, 0);

	jintArray JNI_returnValue = wrapped_Java_org_example_Main_readArr(env, clazz, obj_a, a);

	env->ReleasePrimitiveArrayCritical(obj_a, a, 0);

	return JNI_returnValue;
}

