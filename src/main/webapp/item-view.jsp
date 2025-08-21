<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahana.edu.model.Item" %>
<%@ include file="/_header.jsp" %>
<%
  final String ctx = request.getContextPath();
  Item it = (Item) request.getAttribute("item");
  if (it == null) { response.sendRedirect(ctx + "/items?err=notfound"); return; }
%>

<div style="max-width:1080px;margin:16px auto 32px auto;padding:0 16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <h3 style="margin:0 0 12px 0;font-size:1.1rem;">Item Details</h3>

    <p style="margin:8px 0;"><strong>ID:</strong> <%= it.getItemId() %></p>
    <p style="margin:8px 0;"><strong>Name:</strong> <%= it.getName() %></p>
    <p style="margin:8px 0;"><strong>Description:</strong> <%= it.getDescription() == null ? "-" : it.getDescription() %></p>
    <p style="margin:8px 0;"><strong>Price:</strong> Rs. <%= it.getPrice() %></p>
    <p style="margin:8px 0;"><strong>Stock:</strong> <%= it.getStock() %></p>

    <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:12px;">
      <a href="<%= ctx %>/items/edit?itemId=<%= it.getItemId() %>"
         style="padding:10px 14px;border-radius:10px;background:#111827;color:#fff;display:inline-block;">Edit</a>
      <a href="<%= ctx %>/items"
         style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;display:inline-block;">Back</a>
    </div>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
