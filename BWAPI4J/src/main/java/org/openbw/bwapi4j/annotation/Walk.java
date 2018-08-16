package org.openbw.bwapi4j.annotation;

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * The annotated {@code int} references {@link TilePosition}.
 * <p>
 * A {@link Walk} is {@link WalkPosition#SIZE_IN_PIXELS} {@link Px}.
 */
@Documented
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
public @interface Walk {
}

