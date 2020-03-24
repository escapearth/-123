package org.open.market.common;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author halfdev
* @since 2020-03-23
* Controller 에서 Session 의 정보들에 접근하고 싶을 때 파라미터에 선언해준다
 * Id로 찾지 않고, @AuthenticationPrincipal 으로 조회
 *
*/
@Retention(RetentionPolicy.RUNTIME) //어노테이션 유지 범위
@Target(ElementType.PARAMETER) // 어노테이션 적용위치
@AuthenticationPrincipal(expression = "accountDto")
public @interface CurrentUser { }
