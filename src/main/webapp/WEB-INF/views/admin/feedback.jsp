<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.admin.approve_feedback"/></h3>

    <form method="post" action="<c:url value="/app/admin/approve-feedback"/>">
        <div class="list-group">
            <c:forEach items="${requestScope.page.items}" var="feedback">
                <input name="feedback-id" class="d-none" type="radio"
                       id="feedback-<c:out value="${feedback.id}"/>"
                       value="<c:out value="${feedback.id}"/>">

                <label for="feedback-<c:out value="${feedback.id}"/>"
                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                    <h5 class="mb-1">
                        <span class="text-muted"><fmt:message key="label.order"/></span> #${feedback.order.id}
                        <span class="text-muted"><fmt:message key="label.to_worker"/></span> <c:out
                            value="${feedback.order.worker.name}"/> (<c:out value="${feedback.order.worker.email}"/>)
                    </h5>
                    <p class="mb-1"><c:out value="${feedback.text}"/></p>
                </label>
            </c:forEach>
            <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Next</button>
        </div>

        <div class="my-3 text-center">
            <c:forEach begin="0" end="${requestScope.page.totalPages - 1}" varStatus="loop">
                <c:choose>
                    <c:when test="${requestScope.page.properties.pageNumber == loop.index}">
                        <span class="h3 mx-2"><c:out value="${loop.index + 1}"/></span>
                    </c:when>
                    <c:otherwise>
                        <a class="h3 mx-2" href="<c:url value="?page=${loop.index}"/>">
                            <c:out value="${loop.index + 1}"/></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </form>
</div>

<%@ include file="../includes/footer.jsp" %>