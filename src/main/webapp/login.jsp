<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div style="flex:1;display:flex;align-items:center;justify-content:center;">
  <div style="width:min(420px,92%);background:#fff;border:1px solid #e5e7eb;border-radius:16px;
              padding:22px;box-shadow:0 6px 20px rgba(0,0,0,.06);">
    <h2 style="margin:0 0 6px 0;">Sign in</h2>
    <p style="margin:0 0 12px 0;color:#6b7280;">Use your system credentials to continue.</p>

    <%
      Object err = request.getAttribute("error");
      if (err != null) {
    %>
    <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
      <%= err %>
    </div>
    <%
      }
    %>

    <form method="post" action="<%= request.getContextPath() %>/login">
      <div style="display:flex;flex-direction:column;gap:6px;margin-bottom:12px;">
        <label for="username" style="font-size:.92rem;color:#374151;">Username</label>
        <input id="username" name="username" autocomplete="username"
               style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
      </div>
      <div style="display:flex;flex-direction:column;gap:6px;margin-bottom:12px;">
        <label for="password" style="font-size:.92rem;color:#374151;">Password</label>
        <input id="password" type="password" name="password" autocomplete="current-password"
               style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
      </div>

      <div style="display:flex;justify-content:space-between;margin-top:10px;">
        <a href="<%= request.getContextPath() %>/index.jsp"
           style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;
                  text-decoration:none;display:inline-block;">Cancel</a>
        <button type="submit"
                style="padding:10px 14px;border-radius:10px;border:0;background:#3b82f6;color:#fff;cursor:pointer;">
          Login
        </button>
      </div>
    </form>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
