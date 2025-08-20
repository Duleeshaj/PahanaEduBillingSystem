<%@ page contentType="text/html;charset=UTF-8" %>
<div style="min-height:100vh;display:flex;flex-direction:column;background:#f6f7fb;color:#1f2937;">
  <%@ include file="/_header.jsp" %>

  <main style="flex:1;display:block;">
    <div style="max-width:1080px;margin:16px auto 32px auto;padding:0 16px;">
      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <h2 style="margin:0 0 12px 0;">Bill History</h2>

        <form method="get" action="<%= request.getContextPath() %>/bills"
              style="display:flex;gap:8px;margin:12px 0;align-items:center;flex-wrap:wrap;">
          <label for="q" style="min-width:110px;">Account #</label>
          <input id="q" name="q"
                 value="<%= request.getAttribute("q")!=null?request.getAttribute("q"):"" %>"
                 placeholder="e.g. 1001"
                 style="flex:1;min-width:220px;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
          <button type="submit"
                  style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#111827;color:#fff;cursor:pointer;">
            Search
          </button>
          <a href="<%= request.getContextPath() %>/billing"
             style="padding:10px 14px;border-radius:10px;background:#3b82f6;color:#fff;text-decoration:none;display:inline-block;">Create Bill</a>
        </form>

        <%
          String msg = request.getParameter("msg");
          String err = (String) request.getAttribute("error");
          String hint = (String) request.getAttribute("hint");
          if (msg != null) {
        %>
        <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#ecfdf5;color:#065f46;border:1px solid #a7f3d0;">
          Action <%= msg %> successfully.
        </div>
        <% } %>
        <% if (err != null) { %>
        <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
          <%= err %>
        </div>
        <% } %>
        <% if (hint != null && err == null && (request.getAttribute("bills")==null ||
                ((java.util.List)request.getAttribute("bills")).isEmpty())) { %>
        <div style="margin:12px 0;color:#6b7280;"><%= hint %></div>
        <% } %>

        <%
          Object obj = request.getAttribute("bills");
          java.util.List<?> list = (obj instanceof java.util.List) ? (java.util.List<?>) obj : java.util.Collections.emptyList();
          if (!list.isEmpty()) {
        %>
        <div style="overflow:auto;">
          <table style="width:100%;border-collapse:collapse;">
            <thead>
            <tr style="text-align:left;border-bottom:1px solid #e5e7eb;">
              <th style="padding:10px;">Bill #</th>
              <th style="padding:10px;">Account #</th>
              <th style="padding:10px;">Units</th>
              <th style="padding:10px;">Unit Rate</th>
              <th style="padding:10px;">Total</th>
              <th style="padding:10px;">Created</th>
              <th style="padding:10px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
              for (Object o : list) {
                com.pahana.edu.model.Bill b = (com.pahana.edu.model.Bill) o;
            %>
            <tr style="border-bottom:1px solid #f1f5f9;">
              <td style="padding:10px;"><%= b.getBillId() %></td>
              <td style="padding:10px;"><%= b.getAccountNumber() %></td>
              <td style="padding:10px;"><%= b.getUnitsConsumed() %></td>
              <td style="padding:10px;">Rs. <%= b.getUnitRate() %></td>
              <td style="padding:10px;font-weight:700;">Rs. <%= b.getTotalAmount() %></td>
              <td style="padding:10px;"><%= b.getCreatedAt() %></td>
              <td style="padding:10px;">
                <a href="<%= request.getContextPath() %>/bill/print?billId=<%= b.getBillId() %>"
                   style="padding:6px 10px;border-radius:8px;background:#111827;color:#fff;text-decoration:none;display:inline-block;">
                  Print
                </a>
              </td>
            </tr>
            <%
              }
            %>
            </tbody>
          </table>
        </div>
        <% } %>
      </div>
    </div>
  </main>

  <%@ include file="/_footer.jsp" %>
</div>
