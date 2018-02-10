package wsm.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String firstPassword = request.getParameter("firstPassword");

        if("思思爱淋淋".equals(firstPassword)){
            response.sendRedirect("/views/login/login.jsp");
            return;
        }

        response.sendRedirect("/views/common/error.jsp");

    }


}
