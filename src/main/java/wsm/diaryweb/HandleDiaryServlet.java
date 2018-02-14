package wsm.diaryweb;

import net.sf.json.JSONObject;
import wsm.common.CommonPath;
import wsm.module.Diary;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class HandleDiaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");

        if(method == null || method.equals("")){
            throw new RuntimeException("please give the method name");
        }


        Class c = this.getClass();

        Method m = null;

        try {
            m = c.getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
        }catch (Exception e){

            throw new RuntimeException("没有找到该方法！");
        }

        try{
            m.invoke(this,request,response);

        }catch (Exception e){

            throw new RuntimeException(e);
        }
    }

    /**
     * 反射方法 扩展dopost
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String method = request.getParameter("method");

        if(method == null || method.equals("")){
            throw new RuntimeException("please give the method name");
        }


        Class c = this.getClass();

        Method m = null;

        try {
            m = c.getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
        }catch (Exception e){

            throw new RuntimeException("没有找到该方法！");
        }

        try{
            m.invoke(this,request,response);

        }catch (Exception e){

            throw new RuntimeException(e);
        }

    }

    /**显示该账户下的所有日记以及public的日记
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void diaryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.getRequestDispatcher("/views/show/diary/diaryList.jsp").forward(request,response);

    }
    public void showDiaryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        Map<String,Object> retMap = new HashMap<>();

        Long total = Long.valueOf(0);

        List<Map<String,Object>> rows = new ArrayList<>();


        String userName = (String)request.getSession().getAttribute("user");

        File diaryDir = new File(CommonPath.DiaryPath);

        File[] files = diaryDir.listFiles();
        for(File f : files){
            if(f.getName().contains(userName) || f.getName().contains("public")){
                Diary d = new Diary();
                String name = f.getName();
                d.setOriginName(name);
                d.setAuthor(userName);
                String title = name.substring(0,name.indexOf("_"));
                d.setName(title);
                if(name.contains("public")){
                    d.setPermission("公开");
                    name = name.replace("_public","");
                }else{
                    d.setPermission("秘密");
                    name = name.replace("_private","");
                }
                name = name.replace(userName+"_","");
                name = name.replace(".txt","");
                name = name.replace(title+"_","");
                name = name.substring(0,name.indexOf("_"));
                d.setDateTime(name);
                Map<String,Object> map = new HashMap<>();
                map.put("name",d.getName());
                map.put("dateTime",d.getDateTime());
                map.put("author",d.getAuthor());
                map.put("permission",d.getPermission());
                map.put("originName",d.getOriginName());
                total += 1;
                rows.add(map);

            }
        }


        retMap.put("rows", rows);
        retMap.put("total", total);
        JSONObject j =  JSONObject.fromObject(retMap);

        responseOutWithJson(response,j);

    }



    /**删除日记
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteDiary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{


        String fileName = request.getParameter("fileName");

        File f = new File(CommonPath.DiaryPath+"/"+fileName);

        f.delete();

        String s = successMessage("删除成功");

        responseOutWithJson(response,s);



    }
    /**保存日记
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void saveDiary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String fileName = request.getParameter("fileName");

        File f = new File(CommonPath.DiaryPath+"/"+fileName);

        String diary = request.getParameter("diary");


        String datetime=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
                .format(Calendar.getInstance()
                        .getTime());

        f.delete();

        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("## 最后更新时间：");
        bw.write(datetime);
        bw.newLine();
        bw.write(diary);
        bw.close();

        responseOutWithJson(response,successMessage("更改保存成功！"));

    }


        /**编辑日记
         *
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
    public void editDiary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String fileName = request.getParameter("fileName");

        File f = new File(CommonPath.DiaryPath+"/"+fileName);

        BufferedReader br = new BufferedReader(new FileReader(f));
        String s;
        StringBuffer sb = new StringBuffer();

        while((s=br.readLine())!=null){
            for(char c : s.toCharArray()){
                if(c == ' '){
                    sb.append("&nbsp");
                }else{
                    sb.append(c);
                }
            }
            sb.append("\r\n");

        }

        request.setAttribute("diary",sb.toString());
        request.setAttribute("fileName",fileName);

        request.getRequestDispatcher("/views/show/diary/diaryForm.jsp").forward(request,response);



    }
    /**修改日记权限
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pubDiary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String fileName = request.getParameter("fileName");

        File f = new File(CommonPath.DiaryPath+"/"+fileName);

        String permission = fileName.replace(".txt","").substring(fileName.lastIndexOf("_")+1);
        String newFile = fileName.replace(".txt","").replace("_"+permission,"");
        File f1 = null;
        if(permission.equals("public")){
             f1 = new File(CommonPath.DiaryPath+"/"+newFile+"_private.txt");
        }else {
             f1 = new File(CommonPath.DiaryPath+"/"+newFile+"_public.txt");
        }

        f.renameTo(f1);

        String s = successMessage("修改权限成功");

        responseOutWithJson(response,s);



    }
    /*查看日记
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showDiary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String fileName = request.getParameter("fileName");

        File f = new File(CommonPath.DiaryPath+"/"+fileName);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        BufferedReader br = new BufferedReader(new FileReader(f));
        String s;
        while ((s=br.readLine())!=null){
            for(char c : s.toCharArray()){
                if(c == ' '){
                    out.append("&nbsp");
                }else{
                    out.append(c);
                }
            }
            out.append("<br>");
        }


    }


    /**
     * 成功消息
     *
     * @return
     */
    public String successMessage(String message)
    {
        return "{\"result\":\"success\", \"message\":\"" + message + "\", \"level\":\"" + "info"+ "\"}";
    }


    /**
     * 以JSON格式输出
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
