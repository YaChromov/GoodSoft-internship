<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="common.home" /></title>
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

        .lang-link:last-child {
            border-right: none;
        }

        .lang-link:hover {
            background: #ddd;
        }

        .lang-link.active {
            background: #bbb;
            color: #333;
            box-shadow: inset 0 1px 3px rgba(0,0,0,0.2);
        }

        .user-info {
            font-size: 14px;
            color: #666;
            display: flex;
            align-items: center;
        }

        .user-info a {
            color: #000;
            font-weight: bold;
        }

        .logout-btn {
            background: linear-gradient(#eee, #ccc);
            border: 1px solid #aaa;
            padding: 2px 8px;
            border-radius: 3px;
            cursor: pointer;
            margin-left: 10px;
            font-size: 12px;
        }

        .logout-btn:hover {
            background: #ddd;
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

        <div class="user-info">
            <spring:message code="common.welcome" />,
            <a href="${pageContext.request.contextPath}/loginedit.jhtml">
                <strong><sec:authentication property="principal.username" /></strong>
            </a>.
            <form action="${pageContext.request.contextPath}/logout.jhtml" method="post" style="display:inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="logout-btn"><spring:message code="common.logout" /></button>
            </form>
        </div>
    </div>
</header>

<nav>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/welcome.jhtml"><spring:message code="common.home" /></a>

        <sec:authorize access="hasRole('ADMIN')">
            <a href="${pageContext.request.contextPath}/userlist.jhtml"><spring:message code="common.users" /></a>
        </sec:authorize>
    </div>
</nav>

<main>
    <h2>🫠</h2>
</main>

<t:footer />

</body>
</html>