<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahana.edu.model.Customer" %>
<%@ include file="/_header.jsp" %>
<%
    Customer c = (Customer) request.getAttribute("customer");
    if (c == null) { response.sendRedirect(request.getContextPath() + "/customers?err=notfound"); return; }
%>
<div class="form">
    <h2>Edit Customer</h2>
    <%
        String err = (String) request.getAttribute("error");
        if (err != null) { %><div class="alert"><%= err %></div><% }
%>
    <form method="post" action="<%= request.getContextPath() %>/customers/edit">
        <div class="row">
            <label for="accountNumber">Account Number</label>
            <input id="accountNumber" name="accountNumber" value="<%= c.getAccountNumber() %>" readonly>
        </div>
        <div class="row">
            <label for="name">Name</label>
            <input id="name" name="name" value="<%= c.getName() %>" required>
        </div>
        <div class="row">
            <label for="address">Address</label>
            <input id="address" name="address" value="<%= c.getAddress() %>" required>
        </div>
        <div class="row">
            <label for="telephone">Telephone</label>
            <input id="telephone" name="telephone" value="<%= c.getTelephone() %>" required>
        </div>
        <div class="row">
            <label for="unitsConsumed">Units Consumed</label>
            <input id="unitsConsumed" name="unitsConsumed" value="<%= c.getUnitsConsumed() %>" required>
        </div>
        <div class="actions">
            <a class="btn secondary" href="<%= request.getContextPath() %>/customers">Cancel</a>
            <button class="btn primary" type="submit">Update</button>
        </div>
    </form>
</div>
<%@ include file="/_footer.jsp" %>
