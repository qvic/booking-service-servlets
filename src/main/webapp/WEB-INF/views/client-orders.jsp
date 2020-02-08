<%@ include file="includes/header.jsp" %>

<div class="container mt-5">
    <h3 class="mb-5 text-center">Orders</h3>
    <div class="list-group">
        <c:forEach items="${requestScope.orders}" var="item">
            <div class="form-check-label list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 mb-2 justify-content-between">
                    <h5>
                        <c:out value="${item.date}"/>
                    </h5>
                </div>
                <p class="mb-1">
                    <c:out value="${item.service}"/>
                </p>
                <p class="mb-1">
                    Worker <c:out value="${item.worker}"/>
                </p>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>