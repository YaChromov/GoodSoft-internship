<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="password.title" /></title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0; padding: 0; background-color: #f0f0f0; color: #333;
            display: flex; flex-direction: column; min-height: 100vh;
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

        .form-group { margin-bottom: 20px; }
        .form-group label {
            display: block;
            font-size: 11px;
            margin-bottom: 5px;
            color: #777;
            font-weight: bold;
            letter-spacing: 1px;
        }

        .form-group input {
            width: 100%; padding: 10px; box-sizing: border-box;
            border: 1px solid #bbb; background: #fafafa; outline: none;
        }

        .btn-group { display: flex; flex-direction: column; gap: 10px; }

        .submit-btn {
            width: 100%; padding: 12px; border: 1px solid #999;
            background: linear-gradient(#eee, #ccc);
            cursor: pointer; font-weight: bold; text-transform: uppercase;
            color: #444;
        }

        .back-link {
            text-align: center; display: block; font-size: 12px;
            color: #888; text-decoration: none; margin-top: 10px;
        }

        footer {
            margin: 20px 60px; padding: 20px; border: 1px solid #bbb;
            background: linear-gradient(90deg, #ddd, #f5f5f5, #ddd);
            text-align: center; font-size: 12px; color: #777;
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
        <div style="color: #666; font-size: 14px;"><spring:message code="password.header" /></div>
    </div>
</header>

<nav>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/welcome.jhtml"><spring:message code="common.home" /></a>
    </div>
</nav>

<main>
    <div class="edit-card">
        <h2><spring:message code="password.title" /></h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/loginedit.jhtml" method="post" modelAttribute="passwordForm">
            <div class="form-group">
                <label><spring:message code="password.label.old" /></label>
                <form:password path="oldPassword" required="required" />
            </div>

            <div class="form-group">
                <label><spring:message code="password.label.new" /></label>
                <form:password path="newPassword" required="required" />
            </div>

            <div class="btn-group">
                <button type="submit" class="submit-btn"><spring:message code="password.btn.submit" /></button>
                <a href="${pageContext.request.contextPath}/welcome.jhtml" class="back-link">
                    <spring:message code="password.btn.cancel" />
                </a>
            </div>
        </form:form>
    </div>
</main>

<t:footer />

</body>
</html>