package com.appspot.jzrest;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple rest service.
 * 
 * @author jchionh
 */
@SuppressWarnings("serial")
public class Jz_restServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        //String pathinfo = req.getPathInfo();

        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world. ");
    }
}
