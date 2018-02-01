package wsm.web;

import java.io.IOException;

public class LoveLinServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        System.out.println("Do Post");


        request.getRequestDispatcher("https://ai.bbai520.com/Lin&Si.html").forward(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        System.out.println("Do Get!");

        String s = request.getContextPath();


        response.sendRedirect("/views/loading.jsp");

    }
}
