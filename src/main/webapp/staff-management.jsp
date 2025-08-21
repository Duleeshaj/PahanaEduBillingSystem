<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.pahana.edu.model.User" %>
<%@ include file="/_header.jsp" %>

<%
  // Guard: only ADMIN can view this page (since it's not under /WEB-INF)
  String role = (String) session.getAttribute("role");
  if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
  }
%>

<div style="max-width:1080px;margin:16px auto;padding:0 16px;">
  <h2 style="margin:0 0 12px;">Admin • Staff Management</h2>

  <% if (request.getAttribute("message") != null) { %>
  <div style="background:#ecfdf5;border:1px solid #10b98133;padding:8px 12px;border-radius:8px;margin-bottom:10px;">
    <%= request.getAttribute("message") %>
  </div>
  <% } %>
  <% if (request.getAttribute("error") != null) { %>
  <div style="background:#fef2f2;border:1px solid #ef444433;padding:8px 12px;border-radius:8px;margin-bottom:10px;">
    <%= request.getAttribute("error") %>
  </div>
  <% } %>

  <!-- Add staff -->
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:14px;margin-bottom:16px;">
    <h3 style="margin:0 0 8px;">Add Staff Member</h3>
    <form method="post" action="<%= request.getContextPath() %>/admin/staff/add"
          style="display:grid;grid-template-columns:1fr 1fr auto;gap:8px;align-items:end;">
      <div>
        <label>Username</label>
        <input name="username" required style="width:100%;padding:8px;border:1px solid #e5e7eb;border-radius:8px;">
      </div>
      <div>
        <label>Password</label>
        <input name="password" type="password" required style="width:100%;padding:8px;border:1px solid #e5e7eb;border-radius:8px;">
      </div>
      <div>
        <button type="submit" style="padding:9px 14px;border:1px solid #1f2937;border-radius:10px;background:#111827;color:#fff;">
          Add Staff
        </button>
      </div>
    </form>
  </div>

  <!-- Staff table -->
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:14px;">
    <h3 style="margin:0 0 8px;">Staff List</h3>
    <table style="width:100%;border-collapse:collapse;">
      <thead>
      <tr>
        <th style="text-align:left;border-bottom:1px solid #e5e7eb;padding:8px;">ID</th>
        <th style="text-align:left;border-bottom:1px solid #e5e7eb;padding:8px;">Username</th>
        <th style="text-align:left;border-bottom:1px solid #e5e7eb;padding:8px;">Active</th>
        <th style="text-align:left;border-bottom:1px solid #e5e7eb;padding:8px;">Actions</th>
      </tr>
      </thead>
      <tbody>
      <%
        List<User> staff = (List<User>) request.getAttribute("staff");
        if (staff != null) {
          for (int i = 0; i < staff.size(); i++) {
            User user = staff.get(i); // << no "u" reuse — avoids the duplicate var error
      %>
      <tr>
        <td style="padding:8px;border-bottom:1px solid #f3f4f6;"><%= user.getUserId() %></td>
        <td style="padding:8px;border-bottom:1px solid #f3f4f6;"><%= user.getUsername() %></td>
        <td style="padding:8px;border-bottom:1px solid #f3f4f6;"><%= user.isActive() ? "Yes" : "No" %></td>
        <td style="padding:8px;border-bottom:1px solid #f3f4f6;display:flex;gap:8px;flex-wrap:wrap;">
          <!-- Enable/Disable -->
          <form method="post" action="<%= request.getContextPath() %>/admin/staff/toggle">
            <input type="hidden" name="userId" value="<%= user.getUserId() %>">
            <input type="hidden" name="active" value="<%= user.isActive() ? "0" : "1" %>">
            <button type="submit" style="padding:6px 10px;border:1px solid #6b7280;border-radius:8px;background:#fff;">
              <%= user.isActive() ? "Disable" : "Enable" %>
            </button>
          </form>

          <!-- Reset Password -->
          <form method="post" action="<%= request.getContextPath() %>/resetPassword" style="display:flex;gap:6px;align-items:center;">
            <input type="hidden" name="userId" value="<%= user.getUserId() %>">
            <input type="password" name="newPassword" placeholder="New password" required
                   style="padding:6px 8px;border:1px solid #e5e7eb;border-radius:8px;">
            <button type="submit" style="padding:6px 10px;border:1px solid #374151;border-radius:8px;background:#111827;color:#fff;">
              Reset
            </button>
          </form>
        </td>
      </tr>
      <%
          }
        }
      %>
      </tbody>
    </table>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
