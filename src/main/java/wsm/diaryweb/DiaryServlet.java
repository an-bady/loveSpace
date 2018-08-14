package wsm.diaryweb;

import wsm.common.CommonPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiaryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String user = (String) request.getSession().getAttribute("user");
        String weather = request.getParameter("weather");
        String mood = request.getParameter("mood");
        String diary = request.getParameter("diary");
        String isPublic = request.getParameter("isPublic");
        String title = request.getParameter("title");

        if(isPublic == null){
            isPublic = "private";
        }

        String datetime=new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss")
                .format(Calendar.getInstance()
                        .getTime());

        File file = new File(CommonPath.DiaryPath+"/"+title+"_"+user+"_"+datetime+"_"+isPublic+".txt");
        BufferedWriter bw =
                new BufferedWriter(new FileWriter(file));

        bw.write("## 日记人 ： "+user);
        bw.newLine();
        bw.write("## 日记时间 ： "+datetime);
        bw.newLine();
        bw.write("## 天气 ： "+weather);
        bw.newLine();
        bw.write("## 心情 ： "+mood);
        bw.newLine();

        bw.write(diary);

        bw.close();

        response.sendRedirect("/views/common/diarySuccess.jsp");



    }


}
