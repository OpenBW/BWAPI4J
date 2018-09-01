package org.openbw.bwapi4j.ap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface BridgeValue {
  String accessor() default "";

  String indirection() default "";

  boolean initializeOnly() default false;
}
