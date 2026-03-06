<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список пользователей</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-links a {
            text-decoration: none;
            color: #444;
            margin-right: 30px;
            font-weight: 600;
        }

        .btn-add {
            background: linear-gradient(#fff, #ddd);
            border: 1px solid #999;
            padding: 5px 15px;
            text-decoration: none;
            color: #333;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
        }

        main {
            flex: 1;
            margin: 20px 60px;
            background: #fff;
            border: 1px solid #ccc;
            padding: 30px;
            background-image:
                linear-gradient(to top right, transparent 49.9%, #f9f9f9 49.9%, #f9f9f9 50.1%, transparent 50.1%),
                linear-gradient(to top left, transparent 49.9%, #f9f9f9 49.9%, #f9f9f9 50.1%, transparent 50.1%);
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

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 5px 5px 15px #eee;
            position: relative;
            z-index: 1;
        }

        th {
            background: linear-gradient(180deg, #eee 0%, #ccc 100%);
            color: #444;
            text-transform: uppercase;
            font-size: 11px;
            letter-spacing: 1px;
            padding: 12px;
            border: 1px solid #aaa;
            text-align: left;
        }

        td {
            padding: 10px 12px;
            border: 1px solid #ddd;
            font-size: 13px;
        }

        tr:nth-child(even) { background-color: #f8f8f8; }
        tr:hover { background-color: #f0f0f0; }

        .action-btn {
            text-decoration: none;
            padding: 4px 8px;
            font-size: 11px;
            border: 1px solid #bbb;
            margin-right: 5px;
            color: #555;
            background: #fdfdfd;
        }

        .edit-btn:hover { background: #e0e0e0; color: #000; }
        .del-btn { color: #a00; }
        .del-btn:hover { background: #fee; }

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
    <div style="font-size: 13px; color: #666;">
        Авторизован: <strong>${sessionScope.user.login}</strong>
    </div>
</header>

<nav>
    <t:nav />
    <a href="${pageContext.request.contextPath}/useradd.jhtml" class="btn-add">+ Добавить пользователя</a>
</nav>

<main>
    <h2 style="text-transform: uppercase; font-size: 18px; color: #555; margin-bottom: 20px;">
        Реестр учетных записей
    </h2>


    <c:if test="${not empty errorMessage}">
        <div class="error-banner">
            ${errorMessage}
        </div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>Логин</th>
                <th>ФИО</th>
                <th>Email</th>
                <th>Дата рожд.</th>
                <th>Роль</th>
                <th style="width: 150px;">Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${userList}">
                <tr>
                    <td><strong>${u.login}</strong></td>
                    <td>${u.surname} ${u.name} ${u.patronymic}</td>
                    <td>${u.email}</td>
                    <td>${u.birthday}</td>
                    <td><small>${u.role}</small></td>
                    <td>

                        <a href="${pageContext.request.contextPath}/useredit.jhtml?id=${u.login}" class="action-btn edit-btn">Изменить</a>


                        <a href="${pageContext.request.contextPath}/userlist.jhtml?login=${u.login}" class="action-btn del-btn"
                           onclick="return confirm('Вы уверены, что хотите удалить пользователя ${u.login}?')">
                           Удалить
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty userList}">
                <tr>
                    <td colspan="6" style="text-align: center; color: #999; padding: 40px;">
                        Список пользователей пуст
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</main>

<t:footer />

</body>
</html>