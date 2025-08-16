<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pahana Edu Billing System</title>
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
            font-size: 32px;
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
            padding: 40px;
            border-radius: 12px;
            text-align: center;
            max-width: 500px;
            width: 100%;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
        }
        .card h2 {
            margin-bottom: 15px;
            color: #004080;
        }
        .card p {
            color: #555;
            font-size: 16px;
            margin-bottom: 25px;
            line-height: 1.5;
        }
        .btn {
            background-color: #004080;
            color: white;
            padding: 12px 25px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }
        .btn:hover {
            background-color: #0066cc;
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
        <h2>Welcome!</h2>
        <p>
            Manage customer accounts, items, and billing seamlessly in one place.
            Secure, efficient, and tailored for Pahana Edu Bookshop's needs.
        </p>
        <a href="login.jsp" class="btn">Login to Continue</a>
    </div>
</div>

<footer>
    &copy; <%= java.time.Year.now() %> Pahana Edu Bookshop. All Rights Reserved.
</footer>

</body>
</html>
