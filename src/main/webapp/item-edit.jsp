<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.pahana.edu.model.Item" %>
<%@ include file="/_header.jsp" %>
<%
  final String ctx = request.getContextPath();
  Item it = (Item) request.getAttribute("item");
  if (it == null) { response.sendRedirect(ctx + "/items?err=notfound"); return; }
%>

<div style="max-width:520px;margin:24px auto 40px auto;padding:0 16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:20px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <h2 style="margin:0 0 12px 0;">Edit Item</h2>

    <%
      String err = (String) request.getAttribute("error");
      if (err != null) {
    %>
    <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;"><%= err %></div>
    <% } %>

    <form method="post" action="<%= ctx %>/items/edit">
      <input type="hidden" name="itemId" value="<%= it.getItemId() %>">

      <div style="display:flex;flex-direction:column;margin-bottom:12px;">
        <label for="name" style="margin-bottom:6px;color:#374151;">Name</label>
        <input id="name" name="name" value="<%= it.getName() %>" required
               style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">
      </div>

      <div style="display:flex;flex-direction:column;margin-bottom:12px;">
        <label for="description" style="margin-bottom:6px;color:#374151;">Description</label>
        <input id="description" name="description" value="<%= it.getDescription() == null ? "" : it.getDescription() %>"
               style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">
      </div>

      <div style="display:flex;flex-direction:column;margin-bottom:12px;">
        <label for="price" style="margin-bottom:6px;color:#374151;">Price</label>
        <input id="price" name="price" value="<%= it.getPrice() %>" required
               style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">
      </div>

      <div style="display:flex;flex-direction:column;margin-bottom:12px;">
        <label for="stock" style="margin-bottom:6px;color:#374151;">Stock</label>
        <input id="stock" name="stock" value="<%= it.getStock() %>" required
               style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">
      </div>

      <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:12px;">
        <a href="<%= ctx %>/items"
           style="padding:10px 14px;border-radius:10px;background:#fff;color:#111827;border:1px solid #d1d5db;display:inline-block;">Cancel</a>
        <button type="submit"
                style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#3b82f6;color:#fff;cursor:pointer;">
          Update
        </button>
      </div>
    </form>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
