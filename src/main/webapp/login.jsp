<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/_header.jsp" %>

<div class="form">
  <h2>Sign in</h2>
  <p class="muted">Use your system credentials to continue.</p>

  <%
    String err = (String) request.getAttribute("error");
    if (err != null && !err.isEmpty()) {
  %>
  <div class="alert"><%= err %></div>
  <%
    }
  %>

  <form method="post" action="<%= request.getContextPath() %>/login">
    <div class="row">
      <label for="username">Username</label>
      <input id="username" name="username" required autocomplete="username">
    </div>
    <div class="row">
      <label for="password">Password</label>
      <input id="password" type="password" name="password" required autocomplete="current-password">
    </div>
    <div class="actions">
      <a class="btn secondary" href="index.jsp">Cancel</a>
      <button class="btn primary" type="submit">Login</button>
    </div>
  </form>
</div>

<%@ include file="/_footer.jsp" %>
