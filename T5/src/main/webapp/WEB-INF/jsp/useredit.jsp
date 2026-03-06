<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактирование профиля</title>
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
            padding: 25px 60px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            padding: 12px 40px;
            font-size: 26px;
            font-weight: 900;
            letter-spacing: 2px;
            border: 2px solid #888;
            background: linear-gradient(180deg, #eee, #ccc, #aaa);
            box-shadow: 2px 2px 5px #bbb;
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
            margin-right: 30px;
            font-weight: 600;
        }

        main {
            flex: 1;
            margin: 20px 60px;
            background: #fff;
            border: 1px solid #ccc;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 40px 0;
            background-image:
                linear-gradient(to top right, transparent 49.8%, #f8f8f8 49.8%, #f8f8f8 50.2%, transparent 50.2%),
                linear-gradient(to top left, transparent 49.8%, #f8f8f8 49.8%, #f8f8f8 50.2%, transparent 50.2%);
        }

        .form-card {
            background: linear-gradient(145deg, #ffffff, #ececec);
            padding: 35px;
            border: 1px solid #a1a1a1;
            box-shadow: 8px 8px 16px #d1d1d1;
            width: 500px;
            z-index: 1;
        }

        .form-card h2 {
            margin: 0 0 25px 0;
            text-align: center;
            text-transform: uppercase;
            font-size: 1.1rem;
            letter-spacing: 1px;
            border-bottom: 1px solid #bbb;
            padding-bottom: 10px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            font-size: 10px;
            font-weight: bold;
            color: #666;
            margin-bottom: 5px;
            text-transform: uppercase;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #bbb;
            background: #fff;
            box-sizing: border-box;
            font-size: 14px;
        }

        .form-group input:focus {
            border-color: #444;
            outline: none;
            background: #fdfdfd;
        }

        .btn-save {
            width: 100%;
            padding: 14px;
            border: 1px solid #888;
            background: linear-gradient(#eee, #ccc);
            cursor: pointer;
            font-weight: bold;
            text-transform: uppercase;
            margin-top: 10px;
            transition: 0.2s;
        }

        .btn-save:hover {
            background: linear-gradient(#ddd, #bbb);
            box-shadow: inset 0 0 5px rgba(0,0,0,0.1);
        }


        .back-link {
            display: block;
            margin-top: 15px;
            font-size: 12px;
            color: #888;
            text-align: center;
            text-decoration: none;
        }

        .back-link:hover {
            color: #444;
            text-decoration: underline;
        }

        footer {
            margin: 0 60px 20px 60px;
            padding: 15px;
            border: 1px solid #bbb;
            background: linear-gradient(90deg, #ddd, #f5f5f5, #ddd);
            text-align: center;
            font-size: 11px;
            color: #777;
        }
    </style>
</head>
<body>

<header>
    <div class="logo">XPOM</div>
    <div style="font-size: 13px; color: #666;">Карточка пользователя: <strong>${user.login}</strong></div>
</header>

<nav>
    <div class="nav-links">
        <a href="welcome.jhtml">Главная</a>
        <a href="userlist.jhtml">Пользователи</a>
    </div>
</nav>

<main>
    <div class="form-card">
        <h2>Редактирование данных</h2>

        <form action="${pageContext.request.contextPath}/useredit.jhtml" method="post">

            <div class="form-row">
                <div class="form-group">
                    <label>Логин</label>
                    <input type="text" name="login" value="${user.login}" required>
                </div>
                <div class="form-group">
                    <label>Роль</label>
                    <select name="role">
                        <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>User</option>
                        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>Фамилия</label>
                <input type="text" name="surname" value="${user.surname}">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Имя</label>
                    <input type="text" name="name" value="${user.name}">
                </div>
                <div class="form-group">
                    <label>Отчество</label>
                    <input type="text" name="patronymic" value="${user.patronymic}">
                </div>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" value="${user.email}">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Дата рождения</label>
                    <input type="date" name="birthday" value="${user.birthday}">
                </div>
            </div>

            <button type="submit" class="btn-save">Сохранить изменения</button>

            <a href="userlist.jhtml" class="back-link">Отмена</a>
        </form>
    </div>
</main>

<footer>
    by Chromov 2026
</footer>

</body>
</html>