package ua.kislov.shop_front.exeptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceException {

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerForAllException(Exception e, HttpServletResponse response){
        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.setViewName("other/error");
        modelAndView.addObject("error", e.getMessage());
        e.fillInStackTrace();
        return modelAndView;
    }
}
