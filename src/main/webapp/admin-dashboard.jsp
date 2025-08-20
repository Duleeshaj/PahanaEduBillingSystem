<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<div class="page">
    <div class="sidebar">
        <h4>Admin</h4>
        <a class="active" href="admin-dashboard.jsp">Overview</a>
        <a href="customers.jsp">Customers</a>
        <a href="items.jsp">Items</a>
        <a href="billing.jsp">Billing</a>
        <a href="bills.jsp">Bills</a>
        <a href="audit.jsp">Audit</a>
        <a href="help.jsp">Help</a>
    </div>
    <div class="main">
        <div class="kpi">
            <div class="chip">Customers: <strong><%= request.getAttribute("kpiCustomersCount") != null ? request.getAttribute("kpiCustomersCount") : "-" %></strong></div>
            <div class="chip">Items: <strong><%= request.getAttribute("kpiItemsCount") != null ? request.getAttribute("kpiItemsCount") : "-" %></strong></div>
            <div class="chip">Bills (Today): <strong><%= request.getAttribute("kpiBillsToday") != null ? request.getAttribute("kpiBillsToday") : "-" %></strong></div>
        </div>
        <div style="margin-top:16px" class="grid">
            <div class="card">
                <h3>Quick Actions</h3>
                <div class="actions"><a class="btn primary" href="customer-add.jsp">Add Customer</a></div>
                <div class="actions" style="margin-top:8px;"><a class="btn" href="item-add.jsp">Add Item</a></div>
            </div>
            <div class="card">
                <h3>Recent Activity</h3>
                <p class="muted">Latest bills and user actions appear here.</p>
            </div>
        </div>
    </div>
</div>
<%@ include file="/_footer.jsp" %>
