package wsm.web;

import wsm.CommonPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        if(!password.equals(passwordConfirm)){
            String errorMessage = "注册错误：两次输入密码不一致！";
            request.setAttribute("errorMessage",errorMessage);
            request.setAttribute("name",name);

            request.getRequestDispatcher("/views/login/register.jsp").forward(request,response);
            return;
        }

        File users = new File(CommonPath.userPath);

//        BufferedReader bf = new BufferedReader(new FileReader(users));
        BufferedWriter bw = new BufferedWriter(new FileWriter(users,true));

        bw.newLine();
        bw.write(name);
        bw.write("=");
        bw.write(password);

        bw.close();

        response.sendRedirect("/views/common/success.jsp");


    }

}
