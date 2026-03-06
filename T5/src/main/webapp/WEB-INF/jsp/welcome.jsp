<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0; /* Светлый фон */
            color: #333;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .metal-border {
            border: 1px solid #a1a1a1;
            background: linear-gradient(145deg, #ffffff, #dcdcdc);
            box-shadow: 2px 2px 5px #bebebe, -2px -2px 5px #ffffff;
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
        }

        .user-info {
            font-size: 14px;
            color: #666;
        }

        .user-info a { color: #000; font-weight: bold; }

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
            transition: color 0.3s;
        }

        .nav-links a:hover {
            color: #000;
        }

        main {
            flex: 1;
            margin: 30px 60px;
            background: #fff;
            border: 1px solid #ccc;
            position: relative;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
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

        .logout-btn {
            background: none;
            border: 1px solid #aaa;
            padding: 2px 8px;
            border-radius: 3px;
            cursor: pointer;
            margin-left: 10px;
            font-size: 12px;
            background: linear-gradient(#eee, #ccc);
        }

        .logout-btn:hover {
            background: #ddd;
        }
    </style>
</head>
<body>

<header>
    <div class="logo">XPOM</div>
    <div class="user-info">
        Привет, <a href="${pageContext.request.contextPath}/loginedit.jhtml"><strong>${sessionScope.user.login}</strong></a>.
        <form action="${pageContext.request.contextPath}/logout.jhtml" method="post" style="display:inline;">
            <button type="submit" class="logout-btn">Выйти</button>
        </form>
    </div>
</header>

<nav>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/welcome.jhtml">Главная</a>

        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/userlist.jhtml">Пользователи</a>
        </c:if>
    </div>
</nav>

<main>
    <h2>🫠</h2>
</main>

<t:footer />

</body>
</html>