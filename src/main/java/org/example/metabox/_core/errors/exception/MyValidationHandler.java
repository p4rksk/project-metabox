package org.example.metabox._core.errors.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

public class MyValidationHandler {
    // Advice (부가 로직 hello 메서드)
    // Advice가 수행될 위치 === PointCut
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")  // <이걸 바로 PointCut 이라고 함
    public void myAOP(JoinPoint jp) {
        Object[] args = jp.getArgs(); //파라미터 (매개변수)
        System.out.println("크기 : " + args.length);

        for (Object arg : args) {
            System.out.println("매개변수 : " + arg);
        }

        System.out.println("MyValidationHandler : hello_______________");
    }

}
