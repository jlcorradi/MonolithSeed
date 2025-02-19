package dev.jlcorradi.monolithseed.core.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CrudEventListener {
  CrudOperation[] supports() default {CrudOperation.UPDATE, CrudOperation.INSERT, CrudOperation.DELETE};

  int order() default Integer.MAX_VALUE;
}
