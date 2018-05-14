/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_openbw_bwapi4j_InteractionHandler */

#ifndef _Included_org_openbw_bwapi4j_InteractionHandler
#define _Included_org_openbw_bwapi4j_InteractionHandler
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    allies_native
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_allies_1native
  (JNIEnv *, jobject);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    enemies_native
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enemies_1native
  (JNIEnv *, jobject);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    getKeyState
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getKeyState
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    leaveGame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_leaveGame
  (JNIEnv *, jobject);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    printf
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_printf
  (JNIEnv *, jobject, jstring);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    sendText
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_sendText
  (JNIEnv *, jobject, jstring);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    setLocalSpeed
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setLocalSpeed
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    enableLatCom
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableLatCom
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    enableUserInput
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableUserInput
  (JNIEnv *, jobject);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    enableCompleteMapInformation
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableCompleteMapInformation
  (JNIEnv *, jobject);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    getRandomSeed
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getRandomSeed
  (JNIEnv *, jobject);

/*
 * Class:     org_openbw_bwapi4j_InteractionHandler
 * Method:    setFrameSkip
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setFrameSkip
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
