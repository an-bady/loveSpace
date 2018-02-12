<%--
  Created by IntelliJ IDEA.
  User: wangsiming
  Date: 18/2/11
  Time: 下午5:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../common/baseui.jsp"%>
<html>
<head>
    <title>${sessionScope.user}的日记列表</title>
</head>
<body background="/resources/img/diaryback1.jpeg">

    <div id="div" style="text-align:center;display: block; margin-left:10%;margin-top:0%;width:80%; height:100%; border:0px solid #000;">
        <div data-options="region: 'north', split: true, title: ''" style="height: 80px;">
            <form class="formTable" id="formSearch" action="">
                <div style="background-color:#F5F5F5" >
                    <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" id="btnSearch" plain="true" onclick="cx()">
                        查询
                    </a>
                    <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="export_list()">
                        下载日记
                    </a>
                    <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="export_list()">
                        在线编辑日记
                    </a>
                    <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="export_list()">
                        查看日记
                    </a>
                    <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-lock" plain="true" onclick="lock()">
                        更改日记权限
                    </a>
                    <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="lock()">
                        删除日记
                    </a>
                    <a style="float:right" href="javascript(0)" class="easyui-linkbutton" iconCls="icon-filter" id="btnSetFilter" plain="true" onclick="filter()">
                        过滤
                    </a>
                </div>
                查询条件：
                <br>
                <table border="0" cellspacing="0" class="table-form" cellpadding="2" style="width:100%">
                    <tr>
                        <th style="width: 8%">
                            开始时间
                        </th>
                        <td style="width:12%">
                            <input type="text" name="createTimeFrom" id="txtSearchCreateTimeFrom" class="Wdate easyui-validatebox textbox"
                                   onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
                        </td>
                        <th style="width: 8%">
                            结束时间
                        </th>
                        <td style="width:12%">
                            <input type="text" name="createTimeTo" id="txtSearchCreateTimeTo" class="Wdate easyui-validatebox textbox"
                                   onFocus="WdatePicker({startDate:'%y-%M-01 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
                        </td>
                        <th style="width: 8%">日记人</th>
                        <td style="width:12%">
                            <input type="text" name="author" class="text"/>
                        </td>
                        <th style="width: 8%">日记权限</th>
                        <td style="width:12%">
                            <select name="perimission">
                                <option value="public">公开的</option>
                                <option value="private">隐私的</option>
                            </select>
                        </td>

                    </tr>
                </table>
            </form>
        </div>
        <span style="text-align:center;display: block; color:brown;font-weight:bolder;font-size:25px">${sessionScope.user}的日记列表</span>

        <br>
        <table id="dg" class="easyui-datagrid"
        data-options="fitColumns:true,rownumbers:true,url:'/handleDiaryServlet?method=showDiaryList',method:'get'">
            <thead>
                <tr>
                    <th data-options="checkbox:true, field:'name',width:160,align:'center'">日记名称</th>
                    <th data-options="field:'dateTime',width:160,align:'center'">日记时间</th>
                    <th data-options="field:'author',width:160,align:'center'">日记作者</th>
                    <th data-options="field:'permission',width:160,align:'center'">日记权限</th>
                </tr>
            </thead>
        </table>



        <a href="/views/show/diary/diary.jsp">
            <span style="color:rebeccapurple;font-weight:bolder;font-size:15px">返回继续提交日记</span></a>
        <br>
        <a href="/views/show/loading.jsp">
            <span style="color:rebeccapurple;font-weight:bolder;font-size:15px">返回主页</span></a>
        <br>
    </div>
</body>
</html>
