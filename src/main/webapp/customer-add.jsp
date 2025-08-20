<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div class="form">
    <h2>Add Customer</h2>
    <%
        String err = (String) request.getAttribute("error");
        if (err != null) { %><div class="alert"><%= err %></div><% }
%>
    <form method="post" action="<%= request.getContextPath() %>/customers/add">
        <div class="row">
            <label for="accountNumber">Account Number</label>
            <input id="accountNumber" name="accountNumber" required>
        </div>
        <div class="row">
            <label for="name">Name</label>
            <input id="name" name="name" required>
        </div>
        <div class="row">
            <label for="address">Address</label>
            <input id="address" name="address" required>
        </div>
        <div class="row">
            <label for="telephone">Telephone</label>
            <input id="telephone" name="telephone" required>
        </div>
        <div class="row">
            <label for="unitsConsumed">Units Consumed</label>
            <input id="unitsConsumed" name="unitsConsumed" required>
        </div>
        <div class="actions">
            <a class="btn secondary" href="<%= request.getContextPath() %>/customers">Cancel</a>
            <button class="btn primary" type="submit">Save</button>
        </div>
    </form>
</div>

<%@ include file="/_footer.jsp" %>
