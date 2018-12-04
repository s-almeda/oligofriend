package servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/hello"}
)
public class HelloServlet extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();

        // Set response content type
        resp.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter pw = resp.getWriter();
        pw.println("<h1>" + message + "</h1>");

        out.write("hello heroku\n".getBytes());
        Greeter greeter = new Greeter();
        out.write(greeter.sayHello().getBytes());
        out.flush();
        out.close();


    }

}