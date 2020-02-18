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
        <a class="navbar-brand" href="<c:url value="/"/>"><fmt:message key="label.app_name"/>
            <c:choose>
                <c:when test="$ {sessionScope.user.isWorker()}">
                    <span class="badge badge-success">Worker</span>
                </c:when>
                <c:when test="${sessionScope.user.isAdmin()}">
                    <span class="badge badge-danger">Admin</span>
                </c:when>
            </c:choose>
        </a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <c:choose>
                    <c:when test="${sessionScope.user ne null}">
                        <li class="nav-item mr-2">
                            <span class="nav-link">
                                <span class="text-secondary">
                                    <fmt:message key="label.logged_in_as"/>
                                </span>
                                <span class="text-white">
                                    <c:out value="${sessionScope.user.name}"/>
                                </span>
                            </span>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/app/logout"/>"><fmt:message
                                    key="label.log_out"/></a>
                        </li>
                        <c:choose>
                            <c:when test="${sessionScope.user.isClient()}">
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/client/order-timeslot"/>"><fmt:message
                                            key="label.client.create_order"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/client/orders"/>"><fmt:message
                                            key="label.client.previous_orders"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/client/feedback"/>"><fmt:message
                                            key="label.client.feedback"/></a>
                                </li>
                            </c:when>
                            <c:when test="${sessionScope.user.isWorker()}">
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/worker/timetable"/>"><fmt:message
                                            key="label.worker.timetable"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/worker/feedback"/>"><fmt:message
                                            key="label.worker.feedback"/></a>
                                </li>
                            </c:when>
                            <c:when test="${sessionScope.user.isAdmin()}">
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/admin/users"/>"><fmt:message
                                            key="label.admin.list_users"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/admin/timetable"/>"><fmt:message
                                            key="label.admin.timetable"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/app/admin/feedback"/>"><fmt:message
                                            key="label.admin.feedback"/></a>
                                </li>
                            </c:when>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="<c:url value="/app/login?from=${requestScope['javax.servlet.forward.request_uri']}"/>">
                                <fmt:message key="label.log_in"/>
                            </a>
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
        <fmt:message key="${requestScope.message}"/>
    </div>
</c:if>