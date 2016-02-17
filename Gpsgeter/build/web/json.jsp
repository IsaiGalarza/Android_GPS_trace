<%-- 
    Document   : json
    Created on : 2016/2/12, 下午 03:53:09
    Author     : Supi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="org.json.simple.JSONArray" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="org.json.simple.parser.ParseException" %>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Connection con;
            Statement sta;            
            ResultSet userRes;
            ArrayList<String> allUserName = new ArrayList();
            JSONObject  allData = new JSONObject();
            
            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gps", "root", "hk4g4m/4ru fu4");
                    sta = con.createStatement();
                    
                     userRes= sta.executeQuery("SELECT DISTINCT userName FROM locationtrace");
                    
                    
                    while(userRes.next()){
                        allUserName.add(userRes.getString("userName"));
                    }
                    for(int i=0; i<allUserName.size();i++){
                        ResultSet detailRes = sta.executeQuery("SELECT * FROM locationtrace WHERE userName='"+allUserName.get(i)+"'");
                        JSONArray locationData = new JSONArray();
                        while(detailRes.next()){
                            JSONObject element = new JSONObject();
                            element.put("latitude", detailRes.getString("latitude"));
                            element.put("longitude", detailRes.getString("longitude"));
                            element.put("time", detailRes.getString("datetime"));
                            locationData.add(element);
                        }
                        allData.put(allUserName.get(i), locationData);
                    }
                   
                    out.print(allData);
     
                    con.close();
                }catch(java.lang.ClassNotFoundException e){
                    out.println("<p>"+e.getMessage()+"</p>");
                }catch(java.sql.SQLException e){
                    out.println("<p>"+e.getMessage()+"</p>");
               }
        %>
    </body>
</html>
