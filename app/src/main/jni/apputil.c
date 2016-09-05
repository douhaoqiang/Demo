/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <android/log.h>
/* Header for class com_dhq_demo_utils_JNIUtils */

#ifndef _Included_com_dhq_demo_utils_JNIUtils
#define _Included_com_dhq_demo_utils_JNIUtils

#define LOG_TAG "Hellojni"
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_dhq_demo_utils_JNIUtils
 * Method:    getSignature
 * Signature: (Ljava/lang/Object;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_dhq_demo_utils_JNIUtils_getSignature
  (JNIEnv * env ,jclass jclass , jobject obj){
    LOGE("这里是来自c的string1");
    return (*env)->NewStringUTF(env,"这里是来自c的string1");
  }

/*
 * Class:     com_dhq_demo_utils_JNIUtils
 * Method:    getPackname
 * Signature: (Ljava/lang/Object;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_dhq_demo_utils_JNIUtils_getPackname
  (JNIEnv * env , jclass jclass , jobject obj){
    LOGE("这里是来自c的string2");
  return (*env)->NewStringUTF(env,"这里是来自c的string2");
  }

#ifdef __cplusplus
}
#endif
#endif
