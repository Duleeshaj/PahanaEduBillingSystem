<%@ page contentType="text/html;charset=UTF-8" %>
<%
  com.pahana.edu.model.Bill b = (com.pahana.edu.model.Bill) request.getAttribute("bill");
  if (b == null) { response.sendRedirect(request.getContextPath()+"/bills?err=notfound"); return; }
%>
<div style="max-width:720px;margin:24px auto;padding:16px;border:1px solid #e5e7eb;border-radius:12px;background:#fff;">
  <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;">
    <div style="font-weight:800;font-size:1.1rem;">Pahana Edu — Tax Invoice</div>
    <div>#<%= b.getBillId() %></div>
  </div>
  <div style="border-top:1px solid #e5e7eb;margin:8px 0;"></div>

  <div style="display:grid;grid-template-columns:1fr 1fr;gap:12px;">
    <div>
      <div><strong>Account #:</strong> <%= b.getAccountNumber() %></div>
      <div><strong>Units:</strong> <%= b.getUnitsConsumed() %></div>
    </div>
    <div>
      <div><strong>Unit Rate:</strong> Rs. <%= b.getUnitRate() %></div>
      <div><strong>Total:</strong> <span style="font-weight:700;">Rs. <%= b.getTotalAmount() %></span></div>
      <div><strong>Date:</strong> <%= b.getCreatedAt() %></div>
    </div>
  </div>

  <div style="margin-top:16px;text-align:right;">
    <button onclick="window.print()"
            style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#111827;color:#fff;cursor:pointer;">
      Print
    </button>
    <a href="<%= request.getContextPath() %>/bills?q=<%= b.getAccountNumber() %>"
       style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;text-decoration:none;display:inline-block;margin-left:8px;">
      Back
    </a>
  </div>
</div>
