<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/styles.css"/>">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#"><fmt:message key="label.app_name"/></a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <c:choose>
                    <c:when test="${sessionScope.user ne null}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/app/users"/>"><fmt:message
                                    key="label.list_users"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/app/logout"/>"><fmt:message
                                    key="label.log_out"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/app/login"/>"><fmt:message key="label.log_in"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/app/signup"/>"><fmt:message
                                    key="label.sign_up"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="?locale=en"><fmt:message key="label.locale.en"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="?locale=uk"><fmt:message key="label.locale.uk"/></a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<c:if test="${requestScope.message ne null}">
    <div class="alert alert-warning text-center fixed-alert" role="alert">
            ${requestScope.message}
    </div>
</c:if>