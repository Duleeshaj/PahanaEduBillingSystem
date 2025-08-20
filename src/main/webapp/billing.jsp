<%@ page contentType="text/html;charset=UTF-8" %>
<div style="min-height:100vh;display:flex;flex-direction:column;background:#f6f7fb;color:#1f2937;">
  <%@ include file="/_header.jsp" %>

  <main style="flex:1;display:block;">
    <div style="max-width:640px;margin:24px auto;padding:0 16px;">
      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:18px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <h2 style="margin:0 0 12px 0;">Create Bill</h2>

        <%
          String err = (String) request.getAttribute("error");
          if (err != null) {
        %>
        <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
          <%= err %>
        </div>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/billing/create">
          <div style="display:flex;flex-direction:column;gap:12px;">
            <div>
              <label for="accountNumber" style="display:block;margin-bottom:6px;color:#374151;">Account Number</label>
              <input id="accountNumber" name="accountNumber" required
                     style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
            </div>
            <div>
              <label for="unitsConsumed" style="display:block;margin-bottom:6px;color:#374151;">Units Consumed</label>
              <input id="unitsConsumed" name="unitsConsumed" required
                     style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
            </div>
            <div style="display:flex;gap:10px;justify-content:flex-end;">
              <a href="<%= request.getContextPath() %>/home"
                 style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;display:inline-block;text-decoration:none;">
                Cancel
              </a>
              <button type="submit"
                      style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#3b82f6;color:#fff;cursor:pointer;">
                Generate Bill
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </main>

  <%@ include file="/_footer.jsp" %>
</div>
