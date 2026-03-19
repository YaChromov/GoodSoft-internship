<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="list.title" /></title>
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
            text-shadow: 1px 1px 0 #fff;
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-links a {
            margin-right: 30px;
            font-weight: 600;
            color: #444;
            text-decoration: none;
        }

        .btn-add {
            background: linear-gradient(#fff, #ddd);
            border: 1px solid #999;
            padding: 5px 15px;
            text-decoration: none;
            color: #333;
            font-size: 11px;
            font-weight: bold;
            text-transform: uppercase;
            box-shadow: 1px 1px 2px #ccc;
        }

        main {
            flex: 1;
            margin: 20px 60px;
            padding: 30px;
            background-color: #fff;
            background-image:
                    linear-gradient(to top right, transparent 49.8%, #f8f8f8 49.8%, #f8f8f8 50.2%, transparent 50.2%),
                    linear-gradient(to top left, transparent 49.8%, #f8f8f8 49.8%, #f8f8f8 50.2%, transparent 50.2%);
            border: 1px solid #ccc;
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
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 5px 5px 15px #eee;
        }

        th {
            background: linear-gradient(180deg, #eee 0%, #ccc 100%);
            color: #444;
            text-transform: uppercase;
            font-size: 10px;
            font-weight: bold;
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

        .role-badge {
            display: inline-block;
            background: #f4f4f4;
            border: 1px solid #ccc;
            padding: 1px 6px;
            border-radius: 3px;
            font-size: 10px;
            font-weight: bold;
            text-transform: uppercase;
        }

        .role-badge-admin {
            border-color: #d9534f;
            color: #d9534f;
            background: #fff1f0;
        }

        .action-btn {
            text-decoration: none;
            padding: 4px 8px;
            font-size: 11px;
            border: 1px solid #bbb;
            margin-right: 5px;
            color: #555;
            background: linear-gradient(#fff, #f2f2f2);
            cursor: pointer;
        }

        .del-btn { color: #a00; }

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
            <spring:message code="list.auth.as" />: <strong>${sessionScope.user.login}</strong>
        </div>
    </div>
</header>

<nav>
    <t:nav />
    <a href="${pageContext.request.contextPath}/useradd.jhtml" class="btn-add">
        <spring:message code="list.btn.add" />
    </a>
</nav>

<main>
    <h2 style="text-transform: uppercase; font-size: 1.1rem; color: #444; margin-bottom: 25px; border-bottom: 1px solid #bbb; padding-bottom: 10px;">
        <spring:message code="list.header.registry" />
    </h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-banner">${errorMessage}</div>
    </c:if>

    <table>
        <thead>
        <tr>
            <th><spring:message code="list.table.login" /></th>
            <th><spring:message code="list.table.fio" /></th>
            <th><spring:message code="list.table.email" /></th>
            <th><spring:message code="list.table.birthday" /></th>
            <th><spring:message code="list.table.roles" /></th>
            <th style="width: 160px;"><spring:message code="list.table.actions" /></th>
        </tr>
        </thead>
        <tbody>
        <spring:message code="list.confirm.delete" var="confirmMsg" />

        <c:forEach var="u" items="${userList}">
            <tr>
                <td><strong>${u.login}</strong></td>
                <td>${u.surname} ${u.name} ${u.patronymic}</td>
                <td>${u.email}</td>
                <td>${u.birthday}</td>
                <td>
                    <c:forEach var="r" items="${u.roles}">
                        <span class="role-badge ${r eq 'ADMIN' ? 'role-badge-admin' : ''}">${r}</span>
                    </c:forEach>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/useredit.jhtml?id=${u.login}" class="action-btn">
                        <spring:message code="list.action.edit" />
                    </a>

                    <form:form action="${pageContext.request.contextPath}/userlist.jhtml/delete" method="post" style="display:inline;"
                               onsubmit="return confirm('${confirmMsg}')">
                        <input type="hidden" name="login" value="${u.login}" />
                        <button type="submit" class="action-btn del-btn">
                            <spring:message code="list.action.delete" />
                        </button>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

<t:footer />

</body>
</html>