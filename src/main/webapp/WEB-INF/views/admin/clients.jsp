<%@ include file="../includes/header.jsp" %>

<div class="container mt-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.admin.promote_client_to_worker"/></h3>

    <form method="post" action="<c:url value="/app/admin/promote-client"/>">
        <div class="list-group">
            <c:forEach items="${requestScope.page.items}" var="client">
                <input name="client-id" class="d-none" type="radio"
                       id="client-<c:out value="${client.id}"/>"
                       value="<c:out value="${client.id}"/>">

                <label for="client-<c:out value="${client.id}"/>"
                       class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                    <h5 class="mb-1">
                        <c:out value="${client.name}"/>
                    </h5>
                    <p class="mb-1"><c:out value="${client.email}"/></p>
                </label>
            </c:forEach>
            <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit"><fmt:message key="label.promote"/></button>
        </div>
    </form>

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