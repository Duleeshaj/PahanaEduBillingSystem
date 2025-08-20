<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.edu.model.Customer" %>
<%@ include file="/_header.jsp" %>

<div class="container" style="padding-top:20px;">
    <div class="card">
        <h3>Customers</h3>

        <form method="get" action="<%= request.getContextPath() %>/customers" style="display:flex; gap:8px; margin:12px 0; align-items:center;">
            <label for="q" style="min-width:60px;">Search</label>
            <input id="q" name="q"
                   value="<%= request.getAttribute("q") != null ? request.getAttribute("q") : "" %>"
                   placeholder="Search by name..."
                   style="flex:1; padding:8px 10px; border:1px solid #d1d5db; border-radius:8px;">
            <button class="btn" type="submit">Search</button>
            <!-- open Add form via GET /customers/add servlet -->
            <a class="btn primary" href="<%= request.getContextPath() %>/customers/add">Add Customer</a>
        </form>

        <%
            String msg = request.getParameter("msg");
            String err = request.getParameter("err");
            if (msg != null) {
        %>
        <div class="alert" style="background:#ecfdf5; color:#065f46; border-color:#a7f3d0;">Action <%= msg %> successfully.</div>
        <%
        } else if (err != null) {
        %>
        <div class="alert">Action failed: <%= err %></div>
        <%
            }

            Object listObj = request.getAttribute("customers");
            List<?> list = (listObj instanceof java.util.List) ? (java.util.List<?>) listObj : java.util.Collections.emptyList();

            if (list.isEmpty()) {
        %>
        <p class="muted">No customers found.</p>
        <%
        } else {
        %>

        <div style="overflow:auto;">
            <table style="width:100%; border-collapse:collapse;">
                <thead>
                <tr style="text-align:left; border-bottom:1px solid #e5e7eb;">
                    <th style="padding:10px;">Account #</th>
                    <th style="padding:10px;">Name</th>
                    <th style="padding:10px;">Telephone</th>
                    <th style="padding:10px;">Units</th>
                    <th style="padding:10px;">Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Object o : list) {
                        Customer c = (Customer) o;
                %>
                <tr style="border-bottom:1px solid #f1f5f9;">
                    <td style="padding:10px;"><%= c.getAccountNumber() %></td>
                    <td style="padding:10px;"><%= c.getName() %></td>
                    <td style="padding:10px;"><%= c.getTelephone() %></td>
                    <td style="padding:10px;"><%= c.getUnitsConsumed() %></td>
                    <td style="padding:10px;">
                        <div style="display:flex; gap:6px; flex-wrap:wrap;">
                            <a class="btn" href="<%= request.getContextPath() %>/customers/view?accountNumber=<%= c.getAccountNumber() %>">View</a>
                            <a class="btn" href="<%= request.getContextPath() %>/customers/edit?accountNumber=<%= c.getAccountNumber() %>">Edit</a>
                            <form method="post" action="<%= request.getContextPath() %>/customers/delete" onsubmit="return confirm('Delete this customer?');">
                                <input type="hidden" name="accountNumber" value="<%= c.getAccountNumber() %>">
                                <button class="btn secondary" type="submit">Delete</button>
                            </form>
                        </div>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <%
            }
        %>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
