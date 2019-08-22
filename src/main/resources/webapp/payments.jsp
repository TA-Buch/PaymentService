<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Payment</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Payments</h2>
    <form method="get">
        <b>Choose Sender Id&nbsp;</b>
        <select name="idSender">
            <c:forEach items="${senders}" var="sender">
                <jsp:useBean id="sender" type="java.lang.Integer"/>
                <tr>
                    <option>${sender}</option>
                </tr>
            </c:forEach>
        </select>
        <button type="submit">Show Total Sum</button>
    </form>
    <a href="payments?action=add">Add Payments</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Id Sender</th>
            <th>Id Recipient</th>
            <th>Amount</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${payments}" var="payment">
            <jsp:useBean id="payment" type="model.Payment"/>
            <tr>
            <td>${payment.idSender}</td>
            <td>${payment.idRecipient}</td>
            <td>${payment.amount}</td>
        </tr>
        </c:forEach>

        </tbody>
    </table>
</section>
</body>
</html>