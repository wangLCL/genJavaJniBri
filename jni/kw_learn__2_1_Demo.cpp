#include <kw_learn__2_1_Demo.h>
#include<iostream>
JNIEXPORT jstring JNICALL Java_kw_learn__12_11_1Demo_getLine(JNIEnv* env, jobject object, jstring obj_prompt) {
	const char* prompt = env->GetStringUTFChars(obj_prompt, 0);


//@line:4
	std::cout<< prompt << std::endl;

    
	env->ReleaseStringUTFChars(obj_prompt, prompt);

    std::string xx = 0;
    std::cin >> xx;
	
    return env->NewStringUTF(xx);

}

