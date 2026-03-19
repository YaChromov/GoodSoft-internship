<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="edit.title" /></title>
    <style>

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
            text-shadow: 1px 1px 0 #fff; /* Белая окантовка букв */
            background: linear-gradient(180deg, #eee 0%, #ccc 50%, #aaa 100%);
            border: 2px solid #888;
            box-shadow: 2px 2px 5px #bbb;
        }

        .header-right {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .lang-switcher {
            display: flex;
            border: 1px solid #aaa;
            border-radius: 3px;
            overflow: hidden;
        }

        .lang-link {
            padding: 2px 10px;
            text-decoration: none;
            font-size: 12px;
            font-weight: bold;
            color: #666;
            background: linear-gradient(#eee, #ccc);
            border-right: 1px solid #aaa;
        }

        .lang-link:last-child { border-right: none; }
        .lang-link:hover { background: #ddd; }
        .lang-link.active {
            background: #bbb;
            color: #333;
            box-shadow: inset 0 1px 3px rgba(0,0,0,0.2);
        }

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
            gap: 20px;
        }

        .form-group { margin-bottom: 18px; }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-size: 10px;
            font-weight: bold;
            color: #666;
            text-transform: uppercase;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            box-sizing: border-box;
            background: #fff;
            border: 1px solid #bbb;
            outline: none;
        }

        .roles-container {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            padding: 10px;
            background: #fff;
            border: 1px solid #bbb;
        }

        .role-option {
            display: flex;
            align-items: center;
            font-size: 13px;
            cursor: pointer;
        }

        .role-option input {
            width: auto !important;
            margin-right: 8px;
        }

        .btn-save {
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

        .btn-save:hover { background: linear-gradient(#ddd, #bbb); }

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
    <div class="header-right">
        <div class="lang-switcher">
            <a href="?lang=ru" class="lang-link ${pageContext.response.locale.language == 'ru' ? 'active' : ''}">RU</a>
            <a href="?lang=en" class="lang-link ${pageContext.response.locale.language == 'en' ? 'active' : ''}">EN</a>
        </div>
        <div style="font-size: 13px; color: #888;">
            <spring:message code="edit.card.header" />: <strong>${user.login}</strong>
        </div>
    </div>
</header>

<nav>
    <t:nav />
</nav>

<main>
    <div class="form-card">
        <h2><spring:message code="edit.form.title" /></h2>

        <c:if test="${not empty error}">
            <div class="error-banner">${error}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/useredit.jhtml" method="post" modelAttribute="user">
            <div class="form-row">
                <div class="form-group">
                    <label><spring:message code="register.label.login" /></label>
                    <form:input path="login" readonly="true" />
                    <form:errors path="login" cssClass="error-text" />
                </div>
            </div>

            <div class="form-group">
                <label><spring:message code="edit.label.roles" /></label>
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
                <label><spring:message code="register.label.surname" /></label>
                <form:input path="surname" cssErrorClass="error-field" />
                <form:errors path="surname" cssClass="error-text" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label><spring:message code="register.label.name" /></label>
                    <form:input path="name" cssErrorClass="error-field" />
                    <form:errors path="name" cssClass="error-text" />
                </div>
                <div class="form-group">
                    <label><spring:message code="register.label.patronymic" /></label>
                    <form:input path="patronymic" cssErrorClass="error-field" />
                    <form:errors path="patronymic" cssClass="error-text" />
                </div>
            </div>

            <div class="form-group">
                <label><spring:message code="register.label.email" /></label>
                <form:input path="email" type="email" cssErrorClass="error-field" />
                <form:errors path="email" cssClass="error-text" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label><spring:message code="register.label.birthday" /></label>
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

            <button type="submit" class="btn-save"><spring:message code="edit.btn.save" /></button>
            <a href="${pageContext.request.contextPath}/userlist.jhtml" class="back-link"><spring:message code="register.btn.cancel" /></a>
        </form:form>
    </div>
</main>

<t:footer />

</body>
</html>