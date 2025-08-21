<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div style="flex:1;display:flex;align-items:center;justify-content:center;padding:20px;">
    <div style="background:#ecfdf5;color:#065f46;border:1px solid #a7f3d0;padding:20px;border-radius:12px;text-align:center;max-width:500px;">
        <h2 style="margin:0 0 8px 0;">Success</h2>
        <p><%= request.getAttribute("message") %></p>
        <a href="<%= request.getContextPath() %>/home" style="padding:10px 14px;background:#3b82f6;color:#fff;border-radius:8px;text-decoration:none;">Go Home</a>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
