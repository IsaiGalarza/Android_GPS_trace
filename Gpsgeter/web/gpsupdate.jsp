<%-- 
    Document   : gpsupdate
    Created on : 2016/1/28, 下午 02:51:50
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
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
            
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gps", "root", "hk4g4m/4ru fu4");
                    sta = con.createStatement();
                    
                    String userName = request.getParameter("username");
                    String longitude = request.getParameter("longitude");
                    String latitude = request.getParameter("latitude");
                    
                    String sql = "INSERT INTO locationtrace(userName,latitude,longitude) VALUES('"+userName +"','"
                                            +latitude+"','"+longitude +"')";                                        
                    sta.execute(sql); 
                    con.close();
                }catch(java.lang.ClassNotFoundException e){
                    out.println("<p>"+e.getMessage()+"</p>");
                }catch(java.sql.SQLException e){
                    out.println("<p>"+e.getMessage()+"</p>");
               }
                
            %>
    </body>
</html>
