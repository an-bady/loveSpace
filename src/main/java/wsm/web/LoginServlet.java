package wsm.web;

import wsm.common.CommonPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoginServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        String name = request.getParameter("name");
        String password = request.getParameter("password");


        File users = new File(CommonPath.userPath);
        BufferedReader br =
                new BufferedReader(new FileReader(users));

        String read;
        while ((read = br.readLine()) != null) {

            if (read.contains("=")) {
                String rName = read.split("=")[0];
                String rPassword = read.split("=")[1];

                if (rName.equals(name) && rPassword.equals(password)) {
                    response.sendRedirect("/views/show/loading.jsp");
                    return;
                }
                String errorMessage = "登入错误：账号密码不一致或不存在该账号！<br> 请重新输入！";
                request.setAttribute("errorMessage", errorMessage);

                request.getRequestDispatcher("/views/login/login.jsp").forward(request, response);
                return;
            }


        }


    }
}
