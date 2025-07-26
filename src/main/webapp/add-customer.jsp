<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Customer</title>
</head>
<body>
<h2>Register New Customer</h2>
<form action="addCustomer" method="post">
    <label>Account Number:</label><br/>
    <input type="number" name="accountNumber" required><br/><br/>

    <label>Name:</label><br/>
    <input type="text" name="name" required><br/><br/>

    <label>Address:</label><br/>
    <input type="text" name="address" required><br/><br/>

    <label>Telephone:</label><br/>
    <input type="text" name="telephone" required><br/><br/>

    <label>Units Consumed:</label><br/>
    <input type="number" name="units" required><br/><br/>

    <input type="submit" value="Add Customer">
</form>
</body>
</html>
