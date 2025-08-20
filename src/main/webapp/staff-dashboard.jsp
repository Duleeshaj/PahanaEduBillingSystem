<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp"); return;
    }
%>
<div class="page">
    <div class="sidebar">
        <h4>Staff</h4>
        <a class="active" href="staff-dashboard.jsp">Overview</a>
        <a href="customers.jsp">Customer Management</a>
        <a href="billing.jsp">Create Bill</a>
        <a href="bills.jsp">Bill History</a>
        <a href="help.jsp">Help</a>
    </div>
    <div class="main">
        <div class="kpi">
            <div class="chip">Customers: <strong><%= request.getAttribute("kpiCustomersCount") != null ? request.getAttribute("kpiCustomersCount") : "-" %></strong></div>
            <div class="chip">Bills (Today): <strong><%= request.getAttribute("kpiBillsToday") != null ? request.getAttribute("kpiBillsToday") : "-" %></strong></div>
        </div>
        <div style="margin-top:16px" class="grid">
            <div class="card">
                <h3>Quick Actions</h3>
                <div class="actions"><a class="btn primary" href="billing.jsp">Create Bill</a></div>
            </div>
            <div class="card">
                <h3>Tips</h3>
                <p class="muted">Use search on Customers to find accounts quickly.</p>
            </div>
        </div>
    </div>
</div>
<%@ include file="/_footer.jsp" %>
