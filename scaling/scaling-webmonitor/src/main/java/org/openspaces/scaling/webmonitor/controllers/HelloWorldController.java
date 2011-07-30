package org.openspaces.scaling.webmonitor.controllers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorldController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {

        String aMessage = "Hello World MVC!";

        ModelAndView modelAndView = new ModelAndView("hello_world");
        modelAndView.addObject("message", aMessage);

        return modelAndView;
    }
}