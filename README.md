# README.md

## 使用方法

1.修改需要处理的源代码目录

String path = "src/main/java/org";

2.GenTools执行方法

在jni下生成c源代码文件以及CMakeLists.txt文件

3.执行run脚本

1.在java的native文件的注释上写c实现

```java

public class Main {
    // @off

    static public native int add (int a, int b); /*
		return a + b;
	*/
}
```

2.调用方法

```JAVA
String pa = "src/main/java/org/example/";
File fileDescriptor = new File(pa);
NativeCodeGenerator nativeCodeGenerator = new NativeCodeGenerator();
nativeCodeGenerator.processDirectory(fileDescriptor);
```

3.结果

```
#include <org_example_Main.h>
JNIEXPORT jint JNICALL Java_org_example_Main_add(JNIEnv* env, jclass clazz, jint a, jint b) {


//@line:7

		return a + b;
	

}


```
