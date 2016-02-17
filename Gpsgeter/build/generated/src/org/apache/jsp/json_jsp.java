package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

public final class json_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");

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
        
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
