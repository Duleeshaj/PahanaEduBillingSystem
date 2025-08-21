<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String ctx = request.getContextPath();
    Object u = session.getAttribute("user");
    boolean loggedIn = (u != null);
%>

<!-- Topbar -->
<div style="display:flex;align-items:center;justify-content:space-between;padding:12px 16px;background:#111827;color:#fff;">
    <div style="display:flex;align-items:center;gap:10px;font-weight:700;letter-spacing:.02em;">
        <div style="width:28px;height:28px;border-radius:8px;background:#3b82f6;display:inline-flex;align-items:center;justify-content:center;font-weight:800;">
            P
        </div>
        <div>Pahana Edu</div>
    </div>

    <div>
        <a href="<%= ctx %>/home"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#1f2937;color:#e5e7eb;display:inline-block;">Home</a>

        <a href="<%= ctx %>/help.jsp"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#1f2937;color:#e5e7eb;display:inline-block;">Help</a>

        <% if (!loggedIn) { %>
        <a href="<%= ctx %>/login.jsp"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#3b82f6;color:#fff;display:inline-block;">Login</a>
        <% } else { %>
        <a href="<%= ctx %>/logout"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#3b82f6;color:#fff;display:inline-block;">Logout</a>
        <% } %>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
