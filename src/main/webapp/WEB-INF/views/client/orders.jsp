<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center">Orders</h3>
    <div class="list-group">
        <c:forEach items="${requestScope.orders}" var="order">
            <div class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 mb-2 justify-content-between">
                    <h5>
                        <c:out value="${cf:formatLocalDateTime(order.date ,'dd.MM.yyyy HH:mm')}"/>
                    </h5>
                </div>
                <div class="mb-1">
                    <h5><fmt:message key="label.service"/></h5>
                    <p><c:out value="${order.service.name}"/> ($<c:out value="${order.service.price}"/>)</p>
                </div>
                <div class="mb-1">
                    <h5><fmt:message key="label.worker"/></h5>
                    <p><c:out value="${order.worker.name}"/> (<c:out value="${order.worker.email}"/>)</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>