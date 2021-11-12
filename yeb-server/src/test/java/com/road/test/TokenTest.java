package com.road.test;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: zhouc
 * @Date: 2021/9/9 15:26
 * @Since： 1.0
 */
public class TokenTest {

    public static void main(String[] args) {
        String bearer = "Bearer";
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE2MzExNzIyMzc2ODEsImV4cCI6MTYzMTc3NzAzN30.Jl3mQq6sy1in0rBEWbCPZl2JLPVsriWCMte3lhbfuHY";
        System.out.println(token.substring(bearer.length()+1));
    }

    static void test1(){

    }
}
