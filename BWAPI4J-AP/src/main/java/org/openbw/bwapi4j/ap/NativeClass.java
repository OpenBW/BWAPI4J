package org.openbw.bwapi4j.ap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface NativeClass {

  String name() default "";
}
