<%@page import="com.strive.labs.extjs.practicum.Utils"%>
<%@page import="com.strive.labs.extjs.practicum.security.LoginResponse"%>
<%
// Authenticate
String username = request.getParameter("username");
String password = request.getParameter("password");
boolean success = ("prideafrica".equals(username) && "Sench@123".equals(password));

// Response
LoginResponse loginResponse = new LoginResponse(success);

// JSON
String jsonResponse = Utils.toJson(loginResponse);

// Response header
response.setContentType("application/json");
%>
<%=jsonResponse%>