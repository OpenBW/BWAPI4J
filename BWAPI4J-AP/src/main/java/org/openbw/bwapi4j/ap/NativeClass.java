package org.openbw.bwapi4j.ap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NativeClass {

  String name() default "";

  String parentName() default "";

  String accessOperator() default "->";
}
