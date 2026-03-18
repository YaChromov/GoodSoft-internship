<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Смена пароля</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
            color: #333;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        header {
            padding: 30px 60px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            padding: 15px 45px;
            font-size: 28px;
            font-weight: 900;
            letter-spacing: 3px;
            text-transform: uppercase;
            border: 2px solid #888;
            background: linear-gradient(180deg, #eee 0%, #ccc 50%, #aaa 100%);
            color: #444;
            text-shadow: 1px 1px 0px #fff;
            box-shadow: 2px 2px 5px #bebebe;
        }

        nav {
            margin: 0 60px;
            padding: 12px 20px;
            border-top: 1px solid #999;
            border-bottom: 1px solid #999;
            background: #e8e8e8;
        }

        .nav-links a {
            text-decoration: none;
            color: #444;
            margin-right: 40px;
            font-weight: 600;
        }

        main {
            flex: 1;
            margin: 20px 60px;
            background: #fff;
            border: 1px solid #ccc;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image:
                    linear-gradient(to top right, transparent 49.7%, #f5f5f5 49.7%, #f5f5f5 50.3%, transparent 50.3%),
                    linear-gradient(to top left, transparent 49.7%, #f5f5f5 49.7%, #f5f5f5 50.3%, transparent 50.3%);
        }

        .edit-card {
            background: linear-gradient(145deg, #ffffff, #e6e6e6);
            padding: 40px;
            border: 1px solid #a1a1a1;
            box-shadow: 10px 10px 20px #d9d9d9, -10px -10px 20px #ffffff;
            width: 320px;
        }

        .edit-card h2 {
            margin-top: 0;
            font-size: 1.2rem;
            text-align: center;
            color: #444;
            text-transform: uppercase;
            border-bottom: 1px solid #ccc;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .error-message {
            color: #a94442;
            background-color: #f2dede;
            border: 1px solid #ebccd1;
            padding: 10px;
            margin-bottom: 15px;
            font-size: 13px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-size: 11px;
            margin-bottom: 5px;
            color: #777;
            font-weight: bold;
            letter-spacing: 1px;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #bbb;
            background: #fafafa;
            outline: none;
        }

        .btn-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .submit-btn {
            width: 100%;
            padding: 12px;
            border: 1px solid #999;
            background: linear-gradient(#eee, #ccc);
            cursor: pointer;
            font-weight: bold;
            text-transform: uppercase;
            color: #444;
        }

        .back-link {
            text-align: center;
            display: block;
            font-size: 12px;
            color: #888;
            text-decoration: none;
            margin-top: 10px;
        }

        footer {
            margin: 20px 60px;
            padding: 20px;
            border: 1px solid #bbb;
            background: linear-gradient(90deg, #ddd, #f5f5f5, #ddd);
            text-align: center;
            font-size: 12px;
            color: #777;
        }
    </style>
</head>
<body>

<header>
    <div class="logo">XPOM</div>
    <div style="color: #666; font-size: 14px;">Безопасность профиля</div>
</header>

<nav>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/welcome.jhtml">Главная</a>
    </div>
</nav>

<main>
    <div class="edit-card">
        <h2>Смена пароля</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>


        <form:form action="${pageContext.request.contextPath}/loginedit.jhtml" method="post" modelAttribute="passwordForm">
            <div class="form-group">
                <label>СТАРЫЙ ПАРОЛЬ</label>
                <form:password path="oldPassword" required="required" />
            </div>

            <div class="form-group">
                <label>НОВЫЙ ПАРОЛЬ</label>
                <form:password path="newPassword" required="required" />
            </div>

            <div class="btn-group">
                <button type="submit" class="submit-btn">Изменить</button>
                <a href="${pageContext.request.contextPath}/welcome.jhtml" class="back-link">Отмена / На главную</a>
            </div>
        </form:form>
    </div>
</main>

<t:footer />

</body>
</html>