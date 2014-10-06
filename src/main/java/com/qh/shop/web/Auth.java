package com.qh.shop.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//liqiang test
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    public String value() default"user";
}
