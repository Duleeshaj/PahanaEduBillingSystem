<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div style="max-width:1080px;margin:24px auto;padding:0 16px;">
  <div style="background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:18px;box-shadow:0 1px 2px rgba(0,0,0,.04);">
    <h3 style="margin:0 0 12px 0;font-size:1.15rem;">Create Bill</h3>

    <% String err = (String) request.getAttribute("error");
      if (err != null) { %>
    <div style="margin:12px 0;padding:10px 12px;border-radius:10px;background:#fee2e2;color:#991b1b;border:1px solid #fecaca;">
      <%= err %>
    </div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/billing/create" id="billForm">
      <!-- Email -->
      <div style="display:flex;gap:16px;align-items:center;flex-wrap:wrap;margin-bottom:12px;">
        <div style="flex:1;min-width:280px;">
          <label for="email" style="display:block;font-size:.92rem;color:#374151;margin-bottom:6px;">Email (existing)</label>
          <input id="email" name="email" placeholder="e.g. alice@example.com"
                 style="width:100%;padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;outline:none;">
        </div>
      </div>

      <!-- Items table -->
      <div>
        <div style="display:flex;justify-content:space-between;align-items:center;margin:8px 0;">
          <label style="font-weight:600;">Items</label>
          <div>
            <button type="button" id="addRowBtn"
                    style="padding:8px 12px;border-radius:8px;border:1px solid #d1d5db;background:#fff;cursor:pointer;">
              + Add Row
            </button>
          </div>
        </div>

        <div id="rows">
          <!-- one starter row -->
          <div class="row" style="display:grid;grid-template-columns:1fr 120px;gap:12px;margin-bottom:10px;">
            <input name="itemId" placeholder="Item ID"
                   style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">
            <input name="qty" placeholder="Qty"
                   style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">
          </div>
        </div>

        <div style="display:flex;gap:10px;justify-content:flex-end;margin-top:12px;">
          <a href="<%= request.getContextPath() %>/home"
             style="padding:10px 14px;border-radius:10px;border:1px solid #d1d5db;background:#fff;color:#111827;display:inline-block;text-decoration:none;">
            Cancel
          </a>
          <button type="submit"
                  style="padding:10px 14px;border-radius:10px;border:1px solid transparent;background:#3b82f6;color:#fff;cursor:pointer;">
            Generate
          </button>
        </div>
      </div>

      <div style="margin-top:10px;color:#6b7280;font-size:.92rem;">
        New customer? <a href="<%= request.getContextPath() %>/customers/add" style="color:#3b82f6;">Add here</a> and come back.
      </div>
    </form>
  </div>
</div>

<script>
  (function () {
    var rows = document.getElementById('rows');
    document.getElementById('addRowBtn').addEventListener('click', function () {
      var div = document.createElement('div');
      div.className = 'row';
      div.style = 'display:grid;grid-template-columns:1fr 120px;gap:12px;margin-bottom:10px;';
      div.innerHTML =
              '<input name="itemId" placeholder="Item ID" ' +
              'style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">' +
              '<input name="qty" placeholder="Qty" ' +
              'style="padding:10px 12px;border:1px solid #d1d5db;border-radius:10px;">';
      rows.appendChild(div);
    });
  })();
</script>

<%@ include file="/_footer.jsp" %>
