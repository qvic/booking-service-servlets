<%@ include file="includes/header.jsp" %>

<div class="container mt-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.users"/></h3>
    <div class="list-group">
        <c:forEach items="${requestScope.page.items}" var="item">
            <div class="form-check-label list-group-item list-group-item-action flex-column align-items-start">
                <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">
                        <c:out value="${item.name}"/>
                    </h5>
                    <p>
                        id <c:out value="${item.id}"/>
                    </p>
                </div>
                <p class="mb-1"><c:out value="${item.email}"/></p>
                <p class="mb-1"><c:out value="${item.role}"/></p>
            </div>
        </c:forEach>
    </div>
    <div class="my-2 text-center">
        <p><c:out value="${requestScope.page.properties}"/></p>
        <p><c:out value="${fn:length(requestScope.page.items)}"/> of total
            <c:out value="${requestScope.page.totalItems}"/> users
            (<c:out value="${requestScope.page.totalPages}"/> pages)</p>

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

<%@ include file="includes/footer.jsp" %>