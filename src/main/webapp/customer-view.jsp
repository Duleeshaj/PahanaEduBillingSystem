<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahana.edu.model.Customer" %>
<%@ include file="/_header.jsp" %>
<%
  final String ctx = request.getContextPath();
  Customer c = (Customer) request.getAttribute("customer");
  if (c == null) { response.sendRedirect(ctx + "/customers?err=notfound"); return; }
%>

<div style="max-width:1080px;margin:16px auto 32px auto;padding:0 16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <h3 style="margin:0 0 12px 0;font-size:1.1rem;">Customer Details</h3>

    <p style="margin:8px 0;"><strong>Account #:</strong> <%= c.getAccountNumber() %></p>
    <p style="margin:8px 0;"><strong>Name:</strong> <%= c.getName() %></p>
    <p style="margin:8px 0;"><strong>Address:</strong> <%= c.getAddress() %></p>
    <p style="margin:8px 0;"><strong>Telephone:</strong> <%= c.getTelephone() %></p>
    <p style="margin:8px 0;"><strong>Units Consumed:</strong> <%= c.getUnitsConsumed() %></p>

    <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:12px;">
      <a href="<%= ctx %>/customers/edit?accountNumber=<%= c.getAccountNumber() %>"
         style="padding:10px 14px;border-radius:10px;background:#111827;color:#fff;display:inline-block;">Edit</a>
      <a href="<%= ctx %>/customers"
         style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;display:inline-block;">Back</a>
    </div>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
