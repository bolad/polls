package com.bolad.polls.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/*/
 * This is a wrapper around @AuthenticationPrincipal annotation of Spring Security
 * Using this meta-annotation reduces the dependency on the Spring Security so if we decide to
 * remove Spring Security from our project we can easily do it by changing the CurrentUser annotation
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
	

}
