package com.example.springtest1.homework.task2.servlets;


import com.example.springtest1.homework.task2.RequestSender;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet
{
    private String message;
    private RequestSender requestSender;

    public void init() {
        requestSender = new RequestSender();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Hello
        PrintWriter out = response.getWriter();
        String location = request.getParameter("location");

        try {
            out.write(requestSender.getInfoByCity(location));

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        out.close();
    }

    public void destroy() {
    }
}
