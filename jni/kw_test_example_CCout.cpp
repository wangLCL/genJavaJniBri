#include "kw_test_example_CCout.h"
#include<iostream>
#include<string>
JNIEXPORT void JNICALL Java_kw_test_example_CCout_cprint(JNIEnv* env, jobject object) {


//@line:4
    std::cout<< "xxxxx" << std::endl;


}


JNIEXPORT void JNICALL Java_kw_test_example_CCout_cprint1
  (JNIEnv *env, jobject,jstring str){
  


    const jchar *unicodeStr = env->GetStringChars(str, NULL);
      if (unicodeStr != NULL) {
          // 计算字符串长度
          jsize length = env->GetStringLength(str);

          // 将UTF-16格式C字符串转换为std::wstring（宽字符串）
          std::wstring wstr(unicodeStr, unicodeStr + length);

          // 释放UTF-16格式C字符串
          env->ReleaseStringChars(str, unicodeStr);

          // 将std::wstring（宽字符串）转换为std::string（字节字符串）
          std::string cpp_str(wstr.begin(), wstr.end());

          // 使用C++的std::string进行操作，例如输出
          std::cout << "Received from Java: " << cpp_str << std::endl;
      }


}