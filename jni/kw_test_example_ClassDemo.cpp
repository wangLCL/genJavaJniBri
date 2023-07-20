#include <kw_test_example_ClassDemo.h>
JNIEXPORT jlong JNICALL Java_kw_test_example_ClassDemo_getPeople(JNIEnv* env, jobject object) {


//@line:4

     People *p = new Peole();
     p->setAge(100);
     return  return reinterpret_cast<jlong>(p);
     

}

