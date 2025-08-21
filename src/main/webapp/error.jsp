<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div style="flex:1;display:flex;align-items:center;justify-content:center;padding:20px;">
    <div style="background:#fee2e2;color:#991b1b;border:1px solid #fecaca;padding:20px;border-radius:12px;text-align:center;max-width:500px;">
        <h2 style="margin:0 0 8px 0;">Error</h2>
        <p><%= request.getAttribute("error") %></p>
        <a href="<%= request.getContextPath() %>/home" style="padding:10px 14px;background:#111827;color:#fff;border-radius:8px;text-decoration:none;">Back</a>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
