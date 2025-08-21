<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<!-- Page container -->
<div style="max-width:1080px;margin:16px auto 32px auto;padding:0 16px;">
    <!-- Title -->
    <div style="display:flex;align-items:center;justify-content:space-between;gap:12px;margin-bottom:12px;">
        <h2 style="margin:0;font-size:1.35rem;">Admin • Overview</h2>
    </div>

    <!-- KPI row -->
    <div style="display:flex;gap:12px;flex-wrap:wrap;margin-bottom:16px;">
        <div style="background:#f8fafc;border:1px solid #e2e8f0;border-radius:12px;padding:10px 14px;">Customers: <strong>-</strong></div>
        <div style="background:#f8fafc;border:1px solid #e2e8f0;border-radius:12px;padding:10px 14px;">Items: <strong>-</strong></div>
        <div style="background:#f8fafc;border:1px solid #e2e8f0;border-radius:12px;padding:10px 14px;">Bills (Today): <strong>-</strong></div>
    </div>

    <!-- Card grid -->
    <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:16px;">
        <a href="customers.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;
              text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Customer Management</h3>
            <div style="color:#6b7280;">Add, edit, search, and view customers.</div>
        </a>

        <a href="items.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;
              text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Items Management</h3>
            <div style="color:#6b7280;">Manage items and stock levels.</div>
        </a>

        <a href="staff-management.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Staff Management</h3>
            <div style="color:#6b7280;">Add staff, enable/disable, reset passwords.</div>
        </a>


        <a href="billing.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;
              text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Create Bill</h3>
            <div style="color:#6b7280;">Enter units and print bills.</div>
        </a>

        <a href="bills.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;
              text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Bill History</h3>
            <div style="color:#6b7280;">View, filter, and reprint bills.</div>
        </a>

        <a href="audit.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;
              text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Audit</h3>
            <div style="color:#6b7280;">Review recent actions and events.</div>
        </a>

        <a href="help.jsp"
           style="display:block;background:#fff;border:1px solid #e5e7eb;border-radius:14px;padding:16px;
              text-decoration:none;color:#1f2937;box-shadow:0 1px 2px rgba(0,0,0,.04);">
            <h3 style="margin:0 0 8px 0;font-size:1.05rem;">Help</h3>
            <div style="color:#6b7280;">Guides and tips for using the system.</div>
        </a>
    </div>
</div>

<%@ include file="/_footer.jsp" %>
