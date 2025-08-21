<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.edu.model.Customer" %>
<%@ include file="/_header.jsp" %>

<div style="max-width:1080px;margin:16px auto 32px auto;padding:0 16px;">
    <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <h3 style="margin:0 0 12px 0;font-size:1.1rem;">Customers</h3>

        <form method="get" action="<%= request.getContextPath() %>/customers"
              style="display:flex;gap:8px;margin:12px 0;align-items:center;flex-wrap:wrap;">
            <label for="q" style="min-width:60px;">Search</label>
            <input id="q" name="q"
                   value="<%= request.getAttribute("q") != null ? request.getAttribute("q") : "" %>"
                   placeholder="Search by name..."
                   style="flex:1;min-width:220px;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
            <button type="submit"
                    style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#111827;color:#fff;cursor:pointer;">
                Search
            </button>
            <a href="<%= request.getContextPath() %>/customers/add"
               style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#3b82f6;color:#fff;display:inline-block;text-decoration:none;">
                Add Customer
            </a>
        </form>

        <%
            String msg = request.getParameter("msg");
            String err = request.getParameter("err");
            if (msg != null) {
        %>
        <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#ecfdf5;color:#065f46;border:1px solid #a7f3d0;">
            Action <%= msg %> successfully.
        </div>
        <% } else if (err != null) { %>
        <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
            Action failed: <%= err %>
        </div>
        <% } %>

        <%
            Object listObj = request.getAttribute("customers");
            List<?> list = (listObj instanceof java.util.List) ? (java.util.List<?>) listObj : java.util.Collections.emptyList();
            if (list.isEmpty()) {
        %>
        <p style="color:#6b7280;margin:8px 0;">No customers found.</p>
        <%
        } else {
        %>
        <div style="overflow:auto;">
            <table style="width:100%;border-collapse:collapse;">
                <thead>
                <tr style="text-align:left;border-bottom:1px solid #e5e7eb;">
                    <th style="padding:10px;">Account #</th>
                    <th style="padding:10px;">Name</th>
                    <th style="padding:10px;">Telephone</th>
                    <th style="padding:10px;">Email</th>
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
                    <td style="padding:10px;"><%= c.getEmail() %></td>
                    <td style="padding:10px;">
                        <div style="display:flex;gap:6px;flex-wrap:wrap;">
                            <a href="<%= request.getContextPath() %>/customers/view?accountNumber=<%= c.getAccountNumber() %>"
                               style="padding:8px 12px;border-radius:10px;background:#111827;color:#fff;display:inline-block;text-decoration:none;">View</a>
                            <a href="<%= request.getContextPath() %>/customers/edit?accountNumber=<%= c.getAccountNumber() %>"
                               style="padding:8px 12px;border-radius:10px;background:#111827;color:#fff;display:inline-block;text-decoration:none;">Edit</a>
                            <form method="post" action="<%= request.getContextPath() %>/customers/delete"
                                  onsubmit="return confirm('Delete this customer?');" style="display:inline;">
                                <input type="hidden" name="accountNumber" value="<%= c.getAccountNumber() %>">
                                <button type="submit"
                                        style="padding:8px 12px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;cursor:pointer;">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <% } %>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
