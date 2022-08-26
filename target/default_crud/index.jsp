<%@ page import = "java.io.*,java.util.*" %>
<html>
<body>

<%
    // New location to be redirected
    String site = new String("/customer");
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", site);
%>

</body>
</html>
