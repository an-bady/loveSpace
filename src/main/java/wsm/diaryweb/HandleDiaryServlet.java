package wsm.diaryweb;

import net.sf.json.JSONObject;
import wsm.common.CommonPath;
import wsm.module.Diary;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**删除日记
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteDiary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        System.out.println("delete Diary!");

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


    }

}
