package com.jeeplus.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

    /**
     * 获取请求Request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        if ( RequestContextHolder.getRequestAttributes ( ) == null ) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes ( )).getRequest ( );
        return request;
    }

    /**
     * 获取请求header中的值
     *
     * @return
     */
    public static String getHeader(String key) {
        if ( RequestContextHolder.getRequestAttributes ( ) == null ) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes ( )).getRequest ( );
        if ( request != null ) {
            return request.getHeader ( key );
        }
        return null;
    }

    /**
     * 获取请求cookie中的值
     *
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if(key.equals ( cookie.getName () )){
                    return cookie.getValue ();
                }
            }
        }
        return null;
    }


    /**
     * 获取请求cookie中的值
     *
     * @return
     */
    public static String getCookie(String key) {
        if ( RequestContextHolder.getRequestAttributes ( ) == null ) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes ( )).getRequest ( );
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if(key.equals ( cookie.getName () )){
                    return cookie.getValue ();
                }
            }
        }
        return null;
    }

}
