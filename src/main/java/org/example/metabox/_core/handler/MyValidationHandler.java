package org.example.metabox._core.handler;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.metabox._core.errors.exception.Exception400;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Aspect
@Component
public class MyValidationHandler {
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    // PointCut
    public void hello(JoinPoint jp) {
        Object[] args = jp.getArgs(); // Args: 파라미터 -> object를 리턴
        System.out.println("크기 : " + args.length);

        for (Object arg : args) {

            if (arg instanceof Errors) {
                Errors errors = (Errors) arg; // 에러스타입의 arg를 다운캐스팅

                if (errors.hasErrors()) {
                    for (FieldError error : errors.getFieldErrors()) {
                        System.out.println(error.getField());
                        System.out.println(error.getDefaultMessage());
                        throw new Exception400(error.getDefaultMessage() + " : " + error.getField());
                    }
                }
            }
        }
        System.out.println("MyValidationHandler: hello____________________");
    }
}
