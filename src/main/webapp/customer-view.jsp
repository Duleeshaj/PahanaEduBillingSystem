<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahana.edu.model.Customer" %>
<%@ include file="/_header.jsp" %>
<%
  Customer c = (Customer) request.getAttribute("customer");
  if (c == null) { response.sendRedirect(request.getContextPath() + "/customers?err=notfound"); return; }
%>
<div class="container" style="padding-top:20px;">
  <div class="card">
    <h3>Customer Details</h3>
    <p><strong>Account #:</strong> <%= c.getAccountNumber() %></p>
    <p><strong>Name:</strong> <%= c.getName() %></p>
    <p><strong>Address:</strong> <%= c.getAddress() %></p>
    <p><strong>Telephone:</strong> <%= c.getTelephone() %></p>
    <p><strong>Units Consumed:</strong> <%= c.getUnitsConsumed() %></p>
    <div class="actions">
      <a class="btn" href="<%= request.getContextPath() %>/customers/edit?accountNumber=<%= c.getAccountNumber() %>">Edit</a>
      <a class="btn secondary" href="<%= request.getContextPath() %>/customers">Back</a>
    </div>
  </div>
</div>
<%@ include file="/_footer.jsp" %>
