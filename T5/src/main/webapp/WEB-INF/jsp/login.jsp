<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
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

        main {
            flex: 1;
            margin: 0 60px;
            background: #fff;
            border: 1px solid #ccc;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-card {
            background: linear-gradient(145deg, #ffffff, #e6e6e6);
            padding: 40px;
            border: 1px solid #a1a1a1;
            box-shadow: 10px 10px 20px #d9d9d9, -10px -10px 20px #ffffff;
            width: 300px;
            z-index: 1; /* Чтобы быть над перекрестием */
        }

        .login-card h2 {
            margin-top: 0;
            font-size: 1.5rem;
            text-align: center;
            color: #555;
            text-transform: uppercase;
            border-bottom: 1px solid #ccc;
            padding-bottom: 10px;
        }

        .error-message {
            color: #721c24;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 10px;
            margin-bottom: 15px;
            font-size: 13px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            font-size: 12px;
            margin-bottom: 5px;
            color: #666;
            font-weight: bold;
        }

        .form-group input[type="text"],
        .form-group input[type="password"] {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #bbb;
            background: #fafafa;
            outline: none;
        }

        .form-group input:focus {
            border-color: #888;
            background: #fff;
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
            transition: all 0.2s;
        }

        .submit-btn:hover {
            background: linear-gradient(#ddd, #bbb);
            color: #000;
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
    <div style="color: #999; font-style: italic;">Авторизация</div>
</header>

<main>
    <div class="login-card">
        <h2>Вход</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login.jhtml" method="post">
            <input type="hidden" name="action" value="login">

            <div class="form-group">
                <label>ЛОГИН</label>
                <input type="text" name="login" required>
            </div>

            <div class="form-group">
                <label>ПАРОЛЬ</label>
                <input type="password" name="password" required>
            </div>

            <input type="submit" value="Войти" class="submit-btn">
        </form>
    </div>
</main>

<t:footer />

</body>
</html>