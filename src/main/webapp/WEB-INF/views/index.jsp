<%@ include file="includes/header.jsp" %>

<div class="d-flex h-100 justify-content-center">
    <div class="text-center mt-5">
        <h1 class="my-5 display-1">
            <c:choose>
                <c:when test="${sessionScope.user ne null}">
                    <fmt:message key="label.hello"/>, <c:out value="${sessionScope.user.name}"/>!
                </c:when>
                <c:otherwise>
                    <fmt:message key="label.hello"/>!
                </c:otherwise>
            </c:choose>
        </h1>
        <h1 class="my-4 display-5 text-secondary"><fmt:message key="label.description"/></h1>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>