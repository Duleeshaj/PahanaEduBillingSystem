<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String ctx = request.getContextPath();
    Object u = session.getAttribute("user");
    boolean loggedIn = (u != null);
%>

<!-- Topbar -->
<div style="display:flex;align-items:center;justify-content:space-between;padding:12px 16px;background:#111827;color:#fff;">
    <div style="display:flex;align-items:center;gap:10px;font-weight:700;letter-spacing:.02em;">
        <div style="width:28px;height:28px;border-radius:8px;background:#3b82f6;display:inline-flex;align-items:center;justify-content:center;font-weight:800;">
            P
        </div>
        <div>Pahana Edu</div>
    </div>

    <div>
        <a href="<%= ctx %>/home"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#1f2937;color:#e5e7eb;display:inline-block;">Home</a>

        <a href="<%= ctx %>/help.jsp"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#1f2937;color:#e5e7eb;display:inline-block;">Help</a>

        <% if (!loggedIn) { %>
        <a href="<%= ctx %>/login.jsp"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#3b82f6;color:#fff;display:inline-block;">Login</a>
        <% } else { %>
        <a href="<%= ctx %>/logout"
           style="margin-left:12px;padding:8px 12px;border-radius:8px;background:#3b82f6;color:#fff;display:inline-block;">Logout</a>
        <% } %>
    </div>
</div>

<!-- Welcome Box (reduced height) -->
<div style="display:flex;align-items:center;justify-content:center;
            padding:48px 16px;          /* ↓ was 96px */
            min-height:70vh;            /* ↓ was ~full viewport */
            background:linear-gradient(180deg,#0d47a1 0%, #0b2e91 100%);">
    <div style="width:min(640px,92vw);
              background:#ffffff;
              border:none;              /* no white outline */
              border-radius:18px;
              padding:24px 22px;        /* ↓ a bit tighter */
              box-shadow:0 16px 36px rgba(2,20,60,.18);
              text-align:center;">
        <h2 style="margin:0 0 10px;font-size:1.8rem;color:#0b2970;letter-spacing:.2px;">Welcome!</h2>
        <p style="margin:0 0 18px;color:#4b5563;line-height:1.55;">
            Manage customer accounts, items, and billing seamlessly in one place.
            Secure, efficient, and tailored for Pahana Edu bookshop’s needs.
        </p>

        <% if (!loggedIn) { %>
        <a href="<%= ctx %>/login.jsp"
           style="display:inline-block;padding:10px 16px;border-radius:10px;background:#1e40af;color:#fff;text-decoration:none;border:1px solid #1c3a9c;box-shadow:0 6px 14px rgba(30,64,175,.25);">
            Continue
        </a>
        <% } else { %>
        <a href="<%= ctx %>/admin/staff"
           style="display:inline-block;padding:10px 16px;border-radius:2px;background:#1e40af;color:#fff;text-decoration:none;border:1px solid #1c3a9c;box-shadow:0 6px 14px rgba(30,64,175,.25);">
            Go to Dashboard
        </a>
        <% } %>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
