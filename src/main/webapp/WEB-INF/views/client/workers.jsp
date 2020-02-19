<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_worker"/></h3>

    <form method="post">
        <div class="list-group">
            <c:forEach items="${requestScope.page.items}" var="item">
                <input name="worker-id" class="d-none" type="radio"
                       id="worker-<c:out value="${item.id}"/>"
                       value="<c:out value="${item.id}"/>">

                <label for="worker-<c:out value="${item.id}"/>"
                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                    <h5 class="mb-1">
                        <c:out value="${item.name}"/>
                    </h5>
                </label>
            </c:forEach>
            <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Next</button>
        </div>
    </form>

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