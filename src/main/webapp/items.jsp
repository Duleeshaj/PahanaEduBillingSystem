<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pahana.edu.model.Item" %>
<%@ include file="/_header.jsp" %>

<%
  final String ctx = request.getContextPath();
%>

<div style="max-width:1080px;margin:0 auto;padding:16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Items</h3>

    <form method="get" action="<%= ctx %>/items"
          style="display:flex;gap:8px;margin:12px 0;align-items:center;">
      <label for="q" style="min-width:60px;font-size:.92rem;color:#374151;">Search</label>
      <input id="q" name="q"
             value="<%= request.getAttribute("q")!=null?request.getAttribute("q"):"" %>"
             placeholder="Search by name..."
             style="flex:1;padding:8px 10px;border:1px solid #d1d5db;border-radius:8px;">
      <button type="submit"
              style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#111827;color:#fff;cursor:pointer;">
        Search
      </button>
      <a href="<%= ctx %>/items/add"
         style="padding:10px 14px;border-radius:10px;background:#3b82f6;color:#fff;text-decoration:none;display:inline-block;">
        Add Item
      </a>
    </form>

    <%
      String msg = request.getParameter("msg");
      String err = request.getParameter("err");
      if (msg != null || err != null) {
        String text = (msg != null) ? ("Action " + msg + " successfully.") : ("Action failed: " + err);
        String style = (msg != null) ? "background:#ecfdf5;color:#065f46;border-color:#a7f3d0;"
                : "background:#fee2e2;color:#991b1b;border-color:#fecaca;";
    %>
    <div style="margin:12px 0;padding:10px 12px;border-radius:10px;border:1px solid; <%= style %>"><%= text %></div>
    <%
      }

      Object obj = request.getAttribute("items");
      List<?> list = (obj instanceof java.util.List) ? (java.util.List<?>) obj : java.util.Collections.emptyList();

      if (list.isEmpty()) {
    %>
    <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:16px;">
      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:14px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <img src="<%=ctx%>/assets/items/a4-exercise-book.jpeg" alt="A4 Exercise Book 80p"
             style="width:100%;height:180px;object-fit:cover;border-radius:12px;display:block;">
        <div style="margin:10px 0 4px 0;font-weight:700;color:#111827;">A4 Exercise Book 80p</div>
        <div style="color:#6b7280;font-size:.95rem;margin:0 0 8px 0;">Single ruled exercise book</div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin-top:6px;">
          <div style="font-weight:700;">Rs. 180.00</div><div style="color:#475569;font-size:.92rem;">In stock</div>
        </div>
      </div>

      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:14px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <img src="<%=ctx%>/assets/items/hb-pencil.jpeg" alt="HB Pencil"
             style="width:100%;height:180px;object-fit:cover;border-radius:12px;display:block;">
        <div style="margin:10px 0 4px 0;font-weight:700;color:#111827;">HB Pencil</div>
        <div style="color:#6b7280;font-size:.95rem;margin:0 0 8px 0;">Graphite HB pencil</div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin-top:6px;">
          <div style="font-weight:700;">Rs. 60.00</div><div style="color:#475569;font-size:.92rem;">In stock</div>
        </div>
      </div>

      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:14px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <img src="<%=ctx%>/assets/items/blue-pen.jpeg" alt="Blue Ball Pen"
             style="width:100%;height:180px;object-fit:cover;border-radius:12px;display:block;">
        <div style="margin:10px 0 4px 0;font-weight:700;color:#111827;">Blue Ball Pen</div>
        <div style="color:#6b7280;font-size:.95rem;margin:0 0 8px 0;">0.7mm blue ink ballpoint</div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin-top:6px;">
          <div style="font-weight:700;">Rs. 50.00</div><div style="color:#475569;font-size:.92rem;">In stock</div>
        </div>
      </div>

      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:14px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <img src="<%=ctx%>/assets/items/eraser.jpg" alt="Eraser"
             style="width:100%;height:180px;object-fit:cover;border-radius:12px;display:block;">
        <div style="margin:10px 0 4px 0;font-weight:700;color:#111827;">Eraser</div>
        <div style="color:#6b7280;font-size:.95rem;margin:0 0 8px 0;">PVC‑free eraser</div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin-top:6px;">
          <div style="font-weight:700;">Rs. 40.00</div><div style="color:#475569;font-size:.92rem;">In stock</div>
        </div>
      </div>

      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:14px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <img src="<%=ctx%>/assets/items/maths-box.jpg" alt="Maths Box Set"
             style="width:100%;height:180px;object-fit:cover;border-radius:12px;display:block;">
        <div style="margin:10px 0 4px 0;font-weight:700;color:#111827;">Maths Box Set</div>
        <div style="color:#6b7280;font-size:.95rem;margin:0 0 8px 0;">Compass, divider, protractor, set squares</div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin-top:6px;">
          <div style="font-weight:700;">Rs. 950.00</div><div style="color:#475569;font-size:.92rem;">In stock</div>
        </div>
      </div>
    </div>
    <%
    } else {
    %>
    <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:16px;">
      <%
        for (Object o : list) {
          Item it = (Item) o;
      %>
      <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:14px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
        <img src="<%=ctx%>/assets/items/placeholder.png" alt="Item"
             style="width:100%;height:180px;object-fit:cover;border-radius:12px;display:block;">
        <div style="margin:10px 0 4px 0;font-weight:700;color:#111827;"><%= it.getName() %></div>
        <div style="color:#6b7280;font-size:.95rem;margin:0 0 8px 0;"><%= it.getDescription()==null?"":it.getDescription() %></div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin-top:6px;">
          <div style="font-weight:700;">Rs. <%= it.getPrice() %></div>
          <div style="color:#475569;font-size:.92rem;">Stock: <%= it.getStock() %></div>
        </div>
        <div style="display:flex;gap:6px;margin-top:10px;flex-wrap:wrap;">
          <a href="<%= ctx %>/items/view?itemId=<%= it.getItemId() %>"
             style="padding:6px 10px;border-radius:8px;background:#111827;color:#fff;text-decoration:none;display:inline-block;">View</a>
          <a href="<%= ctx %>/items/edit?itemId=<%= it.getItemId() %>"
             style="padding:6px 10px;border-radius:8px;background:#111827;color:#fff;text-decoration:none;display:inline-block;">Edit</a>
          <form method="post" action="<%= ctx %>/items/delete" onsubmit="return confirm('Delete this item?');" style="display:inline;">
            <input type="hidden" name="itemId" value="<%= it.getItemId() %>">
            <button type="submit"
                    style="padding:6px 10px;border-radius:8px;background:#fff;color:#111827;border:1px solid #d1d5db;cursor:pointer;">
              Delete
            </button>
          </form>
        </div>
      </div>
      <%
        }
      %>
    </div>
    <%
      } // end else
    %>
  </div>
</div>

<%@ include file="/_footer.jsp" %>
