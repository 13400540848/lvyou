package com.bluesimon.wbf.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authentication {

    /**
     * 权限值枚举
     *
     * @author peida
     */
    enum Authority {
        QUERY, MODIFY, CREATE, PATCH, PUBLISH, DOWNLOAD
    }

    Authority name();
}