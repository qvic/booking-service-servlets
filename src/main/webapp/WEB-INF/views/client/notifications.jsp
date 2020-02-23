<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.notifications"/></h3>
    <div class="list-group">
        <c:forEach items="${requestScope.unreadNotifications}" var="n">
            <div class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 mb-2 justify-content-between">
                    <h5>
                        <c:out value="${cf:formatLocalDateTime(n.order.date ,'dd.MM.yyyy HH:mm')}"/>
                    </h5>
                </div>
                <div class="mb-1">
                    <h5><fmt:message key="label.service"/></h5>
                    <p><c:out value="${n.order.service.name}"/> ($<c:out value="${n.order.service.price}"/>)</p>
                </div>
                <div class="mb-1">
                    <h5><fmt:message key="label.worker"/></h5>
                    <p><c:out value="${n.order.worker.name}"/> (<c:out value="${n.order.worker.email}"/>)</p>
                </div>
                <a class="btn btn-link" href="<c:out value="/app/client/leave-feedback?order-id=${n.order.id}"/>">
                    <fmt:message key="label.client.leave_feedback"/>
                </a>
            </div>
        </c:forEach>
        <hr>
        <h3 class="my-2"><fmt:message key="label.client.old_notifications"/></h3>
        <c:forEach items="${requestScope.readNotifications}" var="n">
            <div class="disabled list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 mb-2 justify-content-between">
                    <h5>
                        <c:out value="${cf:formatLocalDateTime(n.order.date ,'dd.MM.yyyy HH:mm')}"/>
                    </h5>
                </div>
                <div class="mb-1">
                    <h5><fmt:message key="label.service"/></h5>
                    <p><c:out value="${n.order.service.name}"/> ($<c:out value="${n.order.service.price}"/>)</p>
                </div>
                <div class="mb-1">
                    <h5><fmt:message key="label.worker"/></h5>
                    <p><c:out value="${n.order.worker.name}"/> (<c:out value="${n.order.worker.email}"/>)</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>