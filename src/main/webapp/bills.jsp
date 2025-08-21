<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.edu.model.Bill" %>
<%@ include file="/_header.jsp" %>
<%
  final String ctx = request.getContextPath();
  Object obj = request.getAttribute("bills");
  List<?> bills = (obj instanceof java.util.List) ? (java.util.List<?>) obj : java.util.Collections.emptyList();
%>

<div style="max-width:1080px;margin:16px auto 24px auto;padding:0 16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <h3 style="margin:0 0 12px 0;font-size:1.1rem;">Bill History</h3>

    <!-- Date filter (optional) -->
    <form method="get" action="<%= ctx %>/bills" style="display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin:10px 0 14px 0;">
      <label for="from" style="min-width:70px;">From</label>
      <input id="from" name="from" type="date"
             value="<%= request.getAttribute("from")!=null?request.getAttribute("from"):"" %>"
             style="padding:8px 10px;border:1px solid #d1d5db;border-radius:8px;">
      <label for="to" style="min-width:40px;">To</label>
      <input id="to" name="to" type="date"
             value="<%= request.getAttribute("to")!=null?request.getAttribute("to"):"" %>"
             style="padding:8px 10px;border:1px solid #d1d5db;border-radius:8px;">
      <button type="submit"
              style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#111827;color:#fff;cursor:pointer;">
        Filter
      </button>
    </form>

    <%
      String err = (String) request.getAttribute("error");
      if (err != null) {
    %>
    <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
      <%= err %>
    </div>
    <% } %>

    <%
      if (bills.isEmpty()) {
    %>
    <p style="color:#6b7280;margin:8px 0;">No bills found.</p>
    <%
    } else {
    %>
    <div style="overflow:auto;">
      <table style="width:100%;border-collapse:collapse;">
        <thead>
        <tr style="text-align:left;border-bottom:1px solid #e5e7eb;">
          <th style="padding:10px;">Bill #</th>
          <th style="padding:10px;">Account #</th>
          <th style="padding:10px;">Total (Rs.)</th>
          <th style="padding:10px;">Date/Time</th>
          <th style="padding:10px;">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
          for (Object o : bills) {
            Bill b = (Bill) o;
        %>
        <tr style="border-bottom:1px solid #f1f5f9;">
          <td style="padding:10px;"><%= b.getBillId() %></td>
          <td style="padding:10px;"><%= b.getAccountNumber() %></td>
          <td style="padding:10px;"><%= String.format("%.2f", b.getTotalAmount()) %></td>
          <td style="padding:10px;"><%= b.getBillDate()!=null ? b.getBillDate().toString() : "-" %></td>
          <td style="padding:10px;">
            <a href="<%= ctx %>/bill-view?billId=<%= b.getBillId() %>"
               style="padding:8px 12px;border-radius:10px;background:#111827;color:#fff;text-decoration:none;display:inline-block;">
              View
            </a>
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
