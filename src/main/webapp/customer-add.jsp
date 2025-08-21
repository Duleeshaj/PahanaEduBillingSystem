<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div style="max-width:640px;margin:24px auto;padding:0 16px;">
    <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:18px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <h2 style="margin:0 0 12px 0;">Add Customer</h2>
        <%
            String err = (String) request.getAttribute("error");
            if (err != null) {
        %>
        <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
            <%= err %>
        </div>
        <% } %>
        <form method="post" action="<%= request.getContextPath() %>/customers/add">
            <div style="display:flex;flex-direction:column;gap:12px;">
                <div>
                    <label for="accountNumber" style="display:block;margin-bottom:6px;color:#374151;">Account Number</label>
                    <input id="accountNumber" name="accountNumber" required
                           style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
                </div>
                <div>
                    <label for="name" style="display:block;margin-bottom:6px;color:#374151;">Name</label>
                    <input id="name" name="name" required
                           style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
                </div>
                <div>
                    <label for="address" style="display:block;margin-bottom:6px;color:#374151;">Address</label>
                    <input id="address" name="address" required
                           style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
                </div>
                <div>
                    <label for="telephone" style="display:block;margin-bottom:6px;color:#374151;">Telephone</label>
                    <input id="telephone" name="telephone" required
                           style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
                </div>
                <div>
                    <label for="email" style="display:block;margin-bottom:6px;color:#374151;">Email</label>
                    <input id="email" name="email" required
                           style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
                </div>
                <div style="display:flex;gap:10px;justify-content:flex-end;">
                    <a href="<%= request.getContextPath() %>/customers"
                       style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;text-decoration:none;display:inline-block;">
                        Cancel
                    </a>
                    <button type="submit"
                            style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#3b82f6;color:#fff;cursor:pointer;">
                        Save
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
