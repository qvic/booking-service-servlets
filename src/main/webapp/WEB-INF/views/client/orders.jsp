<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center">Orders</h3>
    <div class="list-group">
        <c:forEach items="${requestScope.orders}" var="item">
            <div class="list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 mb-2 justify-content-between">
                    <h5>
                        <c:out value="${cf:formatLocalDateTime(item.date ,'dd.MM.yyyy HH:mm')}"/>
                    </h5>
                </div>
                <div class="mb-1">
                    <h5>Service</h5>
                    <p><c:out value="${item.service.name}"/> ($<c:out value="${item.service.price}"/>)</p>
                </div>
                <div class="mb-1">
                    <h5>Worker</h5>
                    <p><c:out value="${item.worker.name}"/> (<c:out value="${item.worker.email}"/>)</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>