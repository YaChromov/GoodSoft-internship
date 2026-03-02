<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Главная</title>
</head>
<body>
    <h2>Добро пожаловать, ${sessionScope.user.login}!</h2>
    <a href="${pageContext.request.contextPath}/loginedit.jhtml">Сменить пароль</a>
    <form action="${pageContext.request.contextPath}/login.jhtml" method="post">
        <input type="hidden" name="action" value="logout">
        <input type="submit" value="Выйти">
    </form>
</body>
</html>