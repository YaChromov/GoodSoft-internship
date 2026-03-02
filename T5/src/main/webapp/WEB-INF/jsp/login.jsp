<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
    <h2>Страница входа</h2>
    <c:if test="${not empty errorMessage}">
        <div style="color:red;">${errorMessage}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/login.jhtml" method="post">
        <input type="hidden" name="action" value="login">
        Логин: <input type="text" name="login"><br>
        Пароль: <input type="password" name="password"><br>
        <input type="submit" value="Войти">
    </form>
</body>
</html>