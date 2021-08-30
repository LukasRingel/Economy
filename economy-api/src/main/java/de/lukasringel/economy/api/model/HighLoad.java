package de.lukasringel.economy.api.model;

import java.lang.annotation.*;

/**
 * This annotation just indicates methods with high load
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HighLoad {
}
