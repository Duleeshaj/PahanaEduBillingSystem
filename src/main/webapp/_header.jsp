<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css">
<div class="topbar">
    <div class="brand">
        <div class="brand-badge">P</div>
        <div>Pahana Edu</div>
    </div>
    <div class="nav-actions">
        <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
        <a href="<%= request.getContextPath() %>/help.jsp">Help</a>
        <%
            Object u = session.getAttribute("user");
            if (u == null) {
        %>
        <a class="primary" href="<%= request.getContextPath() %>/login.jsp">Login</a>
        <%
        } else {
        %>
        <a class="primary" href="<%= request.getContextPath() %>/logout">Logout</a>
        <%
            }
        %>
    </div>
</div>
