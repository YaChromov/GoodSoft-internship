<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Смена пароля</title>
</head>
<body>
    <h2>Смена пароля</h2>
    <c:if test="${not empty errorMessage}">
        <div style="color:red;">${errorMessage}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/loginedit.jhtml" method="post">
        <input type="hidden" name="action" value="changePassword">
        Старый пароль: <input type="password" name="oldPassword"><br>
        Новый пароль: <input type="password" name="newPassword"><br>
        <input type="submit" value="Изменить">
    </form>
    <a href="${pageContext.request.contextPath}/welcome.jhtml">На главную</a>
</body>
</html>