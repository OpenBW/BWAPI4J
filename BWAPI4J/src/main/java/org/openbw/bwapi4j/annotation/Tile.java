package org.openbw.bwapi4j.annotation;

import org.openbw.bwapi4j.TilePosition;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * The annotated {@code int} references {@link TilePosition}.
 * <p>
 * A {@link Tile} is {@link TilePosition#SIZE_IN_PIXELS} {@link Px}.
 */
@Documented
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
public @interface Tile {
}

