/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.services;

import com.smartcards.util.UserType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Bogdan Begovic
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtectedPage {

    public UserType[] getRoles() default {};
}
