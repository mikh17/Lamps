package com.tjeannin.provigen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a field of a contract class that should not be null in the database.</br>
 * The value attribute should be one of the following:
 * <ul>
 * <li>{@link OnConflict#ROLLBACK}</li>
 * <li>{@link OnConflict#ABORT}</li>
 * <li>{@link OnConflict#FAIL}</li>
 * <li>{@link OnConflict#IGNORE}</li>
 * <li>{@link OnConflict#REPLACE}</li>
 * </ul>
 * This constraint is <b>only</b> applied on table creation. <br/>
 * Adding this annotation to a {@link Contract} with an already created table will have <b>no effect</b>.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
	String value();
}
