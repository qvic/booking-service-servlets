<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center">Feedback</h3>
    <div class="list-group">
        <c:forEach items="${requestScope.page.items}" var="item">
            <div class="list-group-item list-group-item-action flex-column align-items-start">
                <h5 class="mb-1">
                    <span class="text-muted">To worker</span> <c:out value="${item.worker.name}"/>
                </h5>
                <p class="mb-1">
                    <c:out value="${item.text}"/>
                </p>
            </div>
        </c:forEach>
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
</div>

<%@ include file="../includes/footer.jsp" %>