<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация пользователя</title>
    <style>
        /* --- Базовые стили --- */
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f0f0;
            color: #333;
        }

        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 25px 60px;
        }

        .logo {
            padding: 12px 40px;
            font-size: 26px;
            font-weight: 900;
            letter-spacing: 2px;
            text-shadow: 1px 1px 0 #fff;
            background: linear-gradient(180deg, #eee 0%, #ccc 50%, #aaa 100%);
            border: 2px solid #888;
            box-shadow: 2px 2px 5px #bbb;
        }

        /* --- Навигация --- */
        nav {
            margin: 0 60px;
            padding: 12px 20px;
            background: #e8e8e8;
            border-top: 1px solid #999;
            border-bottom: 1px solid #999;
        }

        .nav-links a {
            margin-right: 30px;
            font-weight: 600;
            color: #444;
            text-decoration: none;
        }

        .nav-links a:hover {
            color: #000;
        }


        main {
            display: flex;
            flex: 1;
            justify-content: center;
            align-items: center;
            margin: 20px 60px;
            padding: 40px 0;
            background-color: #fff;
            background-image:
                linear-gradient(to top right, transparent 49.8%, #f8f8f8 49.8%, #f8f8f8 50.2%, transparent 50.2%),
                linear-gradient(to top left, transparent 49.8%, #f8f8f8 49.8%, #f8f8f8 50.2%, transparent 50.2%);
            border: 1px solid #ccc;
        }


        .form-card {
            z-index: 1;
            width: 550px;
            padding: 35px;
            background: linear-gradient(145deg, #ffffff, #ececec);
            border: 1px solid #a1a1a1;
            box-shadow: 10px 10px 20px #d1d1d1, -5px -5px 15px #ffffff;
        }

        .form-card h2 {
            margin: 0 0 25px 0;
            padding-bottom: 10px;
            font-size: 1.1rem;
            color: #444;
            text-align: center;
            text-transform: uppercase;
            letter-spacing: 1px;
            border-bottom: 1px solid #bbb;
        }


        .error-banner {
            margin-bottom: 20px;
            padding: 12px;
            font-size: 13px;
            font-weight: bold;
            color: #b00;
            text-align: center;
            background-color: #fee;
            border: 1px solid #f88;
            border-radius: 4px;
        }


        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-size: 10px;
            font-weight: bold;
            color: #666;
            text-transform: uppercase;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            box-sizing: border-box;
            background: #fff;
            border: 1px solid #bbb;
            outline: none;
        }

        .form-group input:focus {
            border-color: #555;
            background: #fdfdfd;
        }


        .btn-submit {
            width: 100%;
            margin-top: 10px;
            padding: 14px;
            font-weight: bold;
            color: #333;
            text-transform: uppercase;
            cursor: pointer;
            background: linear-gradient(#eee, #ccc);
            border: 1px solid #888;
            transition: 0.2s ease;
        }

        .btn-submit:hover {
            color: #000;
            background: linear-gradient(#ddd, #bbb);
        }

        .back-link {
            display: block;
            margin-top: 15px;
            font-size: 12px;
            color: #888;
            text-align: center;
            text-decoration: none;
        }

        /* --- Футер --- */
        footer {
            margin: 0 60px 20px 60px;
            padding: 15px;
            font-size: 11px;
            color: #777;
            text-align: center;
            background: linear-gradient(90deg, #ddd, #f5f5f5, #ddd);
            border: 1px solid #bbb;
        }
    </style>
</head>
<body>

<header>
    <div class="logo">XPOM</div>
    <div style="font-size: 13px; color: #888;">Администрирование / Новый профиль</div>
</header>

<nav>
    <div class="nav-links">
        <a href="welcome.jhtml">Главная</a>
        <a href="userlist.jhtml">Список пользователей</a>
    </div>
</nav>

<main>
    <div class="form-card">
        <h2>Регистрация пользователя</h2>

        <%-- Вывод сообщения об ошибке из сервлета --%>
        <c:if test="${not empty errorMessage}">
            <div class="error-banner">
                ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/useradd.jhtml" method="post">
            <input type="hidden" name="action" value="create">

            <div class="form-row">
                <div class="form-group">
                    <label>Логин</label>
                    <input type="text" name="login" value="${param.login}" required placeholder="user123">
                </div>
                <div class="form-group">
                    <label>Пароль</label>
                    <input type="password" name="password" required placeholder="••••••••">
                </div>
            </div>

            <div class="form-group">
                <label>Фамилия</label>
                <input type="text" name="surname" value="${param.surname}" placeholder="Иванов">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Имя</label>
                    <input type="text" name="name" value="${param.name}" placeholder="Иван">
                </div>
                <div class="form-group">
                    <label>Отчество</label>
                    <input type="text" name="patronymic" value="${param.patronymic}" placeholder="Иванович">
                </div>
            </div>

            <div class="form-group">
                <label>Электронная почта (Email)</label>
                <input type="email" name="email" value="${param.email}" placeholder="example@mail.com">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Дата рождения</label>
                    <input type="date" name="birthday" value="${param.birthday}">
                </div>
                <div class="form-group">
                    <label>Роль в системе</label>
                    <select name="role">
                        <option value="USER" ${param.role == 'USER' ? 'selected' : ''}>User (Пользователь)</option>
                        <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>Admin (Администратор)</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn-submit">Создать учетную запись</button>
            <a href="userlist.jhtml" class="back-link">Отмена</a>
        </form>
    </div>
</main>

<footer>
    by Chromov 2026
</footer>

</body>
</html>