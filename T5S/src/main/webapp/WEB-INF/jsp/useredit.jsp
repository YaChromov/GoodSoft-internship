<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

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
            margin-right: 40px;
            font-weight: 600;
            transition: color 0.3s;
        }

        .nav-links a:hover {
            color: #000;
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

        .error-text {
            color: #d00;
            font-size: 11px;
            display: block;
            margin-top: 5px;
            font-weight: 600;
        }


        .error-field {
            border: 1px solid #f44 !important;
            background-color: #fff5f5 !important;
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

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #bbb;
            background: #fff;
            box-sizing: border-box;
            font-size: 14px;
        }

        .roles-container {
            background: #fff;
            border: 1px solid #bbb;
            padding: 10px;
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }

        .role-option {
            display: flex;
            align-items: center;
            font-size: 13px;
            cursor: pointer;
            user-select: none;
        }

        .role-option input[type="checkbox"] {
            width: auto;
            margin-right: 8px;
            cursor: pointer;
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
        }

        .back-link {
            display: block;
            margin-top: 15px;
            font-size: 12px;
            color: #888;
            text-align: center;
            text-decoration: none;
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

    <t:nav />
</nav>

<main>
    <div class="form-card">
        <h2>Редактирование данных</h2>


        <c:if test="${not empty error}">
            <div class="error-banner">${error}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/useredit.jhtml" method="post" modelAttribute="user">

            <div class="form-row">
                <div class="form-group">
                    <label>Логин</label>
                    <form:input path="login" readonly="true" />
                    <form:errors path="login" cssClass="error-text" />
                </div>
            </div>

            <div class="form-group">
                <label>Доступные права доступа (роли)</label>
                <div class="roles-container">
                    <c:forEach var="roleName" items="${allRoles}">
                        <label class="role-option">
                            <c:set var="isDisabled" value="${sessionScope.user.login eq user.login and roleName eq 'ADMIN'}" />

                            <c:choose>
                                <c:when test="${isDisabled}">
                                    <input type="checkbox" name="roles" value="${roleName}" checked disabled>
                                    <input type="hidden" name="roles" value="${roleName}">
                                </c:when>
                                <c:otherwise>
                                    <form:checkbox path="roles" value="${roleName}" />
                                </c:otherwise>
                            </c:choose>
                                ${roleName}
                        </label>
                    </c:forEach>
                </div>
                <form:errors path="roles" cssClass="error-text" />
            </div>

            <div class="form-group">
                <label>Фамилия</label>
                <form:input path="surname" cssErrorClass="error-field" />
                <form:errors path="surname" cssClass="error-text" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Имя</label>
                    <form:input path="name" cssErrorClass="error-field" />
                    <form:errors path="name" cssClass="error-text" />
                </div>
                <div class="form-group">
                    <label>Отчество</label>
                    <form:input path="patronymic" cssErrorClass="error-field" />
                    <form:errors path="patronymic" cssClass="error-text" />
                </div>
            </div>

            <div class="form-group">
                <label>Email</label>
                <form:input path="email" type="email" cssErrorClass="error-field" />
                <form:errors path="email" cssClass="error-text" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Дата рождения</label>
                    <spring:bind path="user.birthday">
                        <input type="date"
                               name="birthday"
                               id="birthday"
                               value="${status.value}"
                               class="form-control ${status.error ? 'error-field' : ''}" />
                    </spring:bind>
                    <form:errors path="birthday" cssClass="error-text" />
                </div>
            </div>

            <button type="submit" class="btn-save">Сохранить изменения</button>
            <a href="${pageContext.request.contextPath}/userlist.jhtml" class="back-link">Отмена</a>
        </form:form>
    </div>
</main>

<t:footer />

</body>
</html>