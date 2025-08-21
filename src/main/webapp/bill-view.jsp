<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahana.edu.model.Bill, com.pahana.edu.model.BillItem, java.util.*" %>
<%@ include file="/_header.jsp" %>

<%
  Bill bill = (Bill) request.getAttribute("bill");
%>

<div style="max-width:960px;margin:24px auto;padding:0 16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:20px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <% if (bill == null) { %>
    <div style="padding:14px;border-radius:10px;background:#fee2e2;border:1px solid #fecaca;color:#991b1b;">
      Bill not found.
    </div>
    <div style="margin-top:12px;">
      <a href="<%= request.getContextPath() %>/home"
         style="padding:8px 12px;border-radius:10px;background:#111827;color:#fff;display:inline-block;">Back</a>
    </div>
    <% } else { %>

    <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:10px;">
      <div style="font-size:1.2rem;font-weight:700;">Bill #<%= bill.getBillId() %></div>
      <div style="color:#475569;">
        Account #: <%= bill.getAccountNumber() %>
        &nbsp;•&nbsp; Date: <%= bill.getBillDate() != null ? bill.getBillDate().toString().replace('T',' ') : "-" %>
      </div>
    </div>

    <div style="overflow:auto;">
      <table style="width:100%;border-collapse:collapse;">
        <thead>
        <tr style="text-align:left;border-bottom:1px solid #e5e7eb;">
          <th style="padding:10px;">Item</th>
          <th style="padding:10px;">Qty</th>
          <th style="padding:10px;">Unit</th>
          <th style="padding:10px;">Line</th>
        </tr>
        </thead>
        <tbody>
        <%
          List<BillItem> items = bill.getItems();
          if (items != null) {
            for (BillItem bi : items) {
        %>
        <tr style="border-bottom:1px solid #f1f5f9;">
          <td style="padding:10px;"><%= bi.getItemName() != null ? bi.getItemName() : String.valueOf(bi.getItemId()) %></td>
          <td style="padding:10px;"><%= bi.getQty() %></td>
          <td style="padding:10px;">Rs. <%= bi.getUnitPrice() %></td>
          <td style="padding:10px;">Rs. <%= bi.getLineTotal() %></td>
        </tr>
        <%
            }
          }
        %>
        <tr>
          <td colspan="3" style="padding:10px;text-align:right;font-weight:700;">Total:</td>
          <td style="padding:10px;font-weight:700;">Rs. <%= bill.getTotalAmount() %></td>
        </tr>
        </tbody>
      </table>
    </div>

    <div style="margin-top:16px;display:flex;gap:10px;">
      <a href="<%= request.getContextPath() %>/home"
         style="padding:8px 12px;border-radius:10px;background:#111827;color:#fff;display:inline-block;">Back to Dashboard</a>
      <a href="<%= request.getContextPath() %>/bills"
         style="padding:8px 12px;border-radius:10px;background:#1f2937;color:#e5e7eb;display:inline-block;">Bill History</a>
    </div>

    <% } %>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
