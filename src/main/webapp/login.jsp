<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login - Pahana Edu Billing System</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(135deg, #004080, #0066cc);
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }
    header {
      text-align: center;
      padding: 20px;
      color: white;
    }
    header h1 {
      font-size: 28px;
      margin: 0;
      letter-spacing: 1px;
    }
    .container {
      flex: 1;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 30px;
    }
    .card {
      background-color: white;
      padding: 35px 40px;
      border-radius: 12px;
      max-width: 400px;
      width: 100%;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
    }
    .card h2 {
      margin-bottom: 20px;
      color: #004080;
      text-align: center;
    }
    .form-group {
      margin-bottom: 18px;
    }
    .form-group label {
      display: block;
      font-size: 14px;
      margin-bottom: 6px;
      color: #333;
      font-weight: 500;
    }
    .form-group input {
      width: 100%;
      padding: 10px;
      font-size: 14px;
      border-radius: 6px;
      border: 1px solid #ccc;
      box-sizing: border-box;
    }
    .form-group input:focus {
      border-color: #004080;
      outline: none;
    }
    .btn {
      background-color: #004080;
      color: white;
      padding: 10px;
      font-size: 16px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      transition: background 0.3s ease;
      width: 100%;
    }
    .btn:hover {
      background-color: #0066cc;
    }
    .link {
      display: block;
      margin-top: 12px;
      text-align: center;
      font-size: 14px;
    }
    .link a {
      color: #004080;
      text-decoration: none;
    }
    .link a:hover {
      text-decoration: underline;
    }
    footer {
      text-align: center;
      padding: 15px;
      background-color: #003060;
      color: white;
      font-size: 14px;
    }
  </style>
</head>
<body>

<header>
  <h1>Pahana Edu Billing System</h1>
</header>

<div class="container">
  <div class="card">
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Enter username" required>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter password" required>
      </div>

      <button type="submit" class="btn">Login</button>

      <div class="link">
        <a href="index.jsp">← Back to Home</a>
      </div>
    </form>
  </div>
</div>

<footer>
  &copy; <%= java.time.Year.now() %> Pahana Edu Bookshop. All Rights Reserved.
</footer>

</body>
</html>
