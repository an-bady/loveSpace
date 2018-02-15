package wsm.diaryweb;

import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String fileName = request.getParameter("fileName");
        String user = (String)httpRequest.getSession().getAttribute("user");


        if(fileName!=null){
            if(fileName.contains(user)){
                chain.doFilter(request, resp);
            }else{
                HttpServletResponse httpResp = (HttpServletResponse) resp;
                responseOutWithJson(httpResp,failMessage("不能修改他人日记"));
            }

        }else {
            chain.doFilter(request, resp);
        }






    }

    /**
     * 失败消息
     *
     * @return
     */
    public String failMessage(String message)
    {
        return "{\"result\":\"fail\", \"message\":\"" + message + "\", \"level\":\"" + "info"+ "\"}";
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

    public void init(FilterConfig config) throws ServletException {

    }

}
