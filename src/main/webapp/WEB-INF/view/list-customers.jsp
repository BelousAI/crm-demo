<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>List Customers</title>
</head>

<body>

    <table>
        <tr>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
        </tr>

        <c:forEach var="tempCustomer" items="${customers}" >

            <tr>
                <td>${tempCustomer.firstName}</td>
                <td>${tempCustomer.lastName}</td>
                <td>${tempCustomer.email}</td>
            </tr>

        </c:forEach>

    </table>

</body>


</html>