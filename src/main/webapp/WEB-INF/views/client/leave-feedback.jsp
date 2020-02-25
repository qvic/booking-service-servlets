<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_order_to_leave_feedback"/></h3>

    <form method="post">
        <div class="list-group">
            <c:forEach items="${requestScope.orders}" var="order">
                <c:choose>
                    <c:when test="${requestScope.selectedOrderId ne null and order.id == requestScope.selectedOrderId}">
                        <input name="order-id" class="d-none" type="radio"
                               required
                               checked
                               id="order-${order.id}"
                               value="${order.id}">
                    </c:when>
                    <c:otherwise>
                        <input name="order-id" class="d-none" type="radio"
                               required
                               id="order-${order.id}"
                               value="${order.id}">
                    </c:otherwise>
                </c:choose>

                <label for="order-${order.id}"
                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
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
                </label>
            </c:forEach>
        </div>
        <hr>
        <label for="feedbackText"><fmt:message key="label.client.feedback_text"/></label>
        <textarea name="text" class="form-control" id="feedbackText" rows="3"></textarea>

        <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit"><fmt:message
                key="label.submit"/></button>
    </form>
</div>

<%@ include file="../includes/footer.jsp" %>