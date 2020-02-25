<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.feedback"/></h3>
    <div class="list-group">
        <c:forEach items="${requestScope.page.items}" var="feedback">
            <div class="list-group-item list-group-item-action flex-column align-items-start">
                <h5 class="mb-1">
                    <span class="text-muted"><fmt:message key="label.order"/></span> #${feedback.order.id}
                </h5>
                <h5 class="mb-1">
                    <span class="text-muted"><fmt:message key="label.to_worker"/></span> <c:out
                        value="${feedback.order.worker.name}"/> (<c:out value="${feedback.order.worker.email}"/>)
                </h5>
                <p class="text-muted mb-3">${feedback.status}</p>
                <p class="mb-1"><c:out value="${feedback.text}"/></p>
            </div>
        </c:forEach>
    </div>

    <c:choose>
        <c:when test="${requestScope.page.totalPages == 0}">
            <h5 class="text-center"><fmt:message key="label.nothing_to_show"/></h5>
        </c:when>
        <c:otherwise>
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
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../includes/footer.jsp" %>