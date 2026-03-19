<%@tag description="навигация" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="nav-links">
        <a href="welcome.jhtml">
                <spring:message code="common.home" />
        </a>
        <a href="userlist.jhtml">
                <spring:message code="common.users" />
        </a>
</div>