<%@ include file="../includes/header.jsp" %>

<div class="container my-5">
    <h3 class="mb-5 text-center"><fmt:message key="label.client.select_service"/></h3>

    <form method="post">
        <div class="list-group">
            <c:forEach items="${requestScope.services}" var="item">
                <c:choose>
                    <c:when test="${item.available}">
                        <input name="service-id" class="d-none" type="radio"
                               id="service-<c:out value="${item.id}"/>"
                               value="<c:out value="${item.id}"/>">

                        <label for="service-<c:out value="${item.id}"/>"
                               class="radio-label form-check-label list-group-item list-group-item-action flex-column align-items-start">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">
                                    <c:out value="${item.name}"/>
                                </h5>
                                <p>
                                    $<c:out value="${item.price}"/>
                                </p>
                            </div>
                        </label>
                    </c:when>
                    <c:otherwise>
                        <div class="disabled list-group-item flex-column align-items-start">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">
                                    <c:out value="${item.name}"/>
                                </h5>
                                <p>
                                    $<c:out value="${item.price}"/>
                                </p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <button class="w-25 mx-auto my-5 btn btn-primary btn-lg btn-block" type="submit">Next</button>
        </div>
    </form>
</div>

<%@ include file="../includes/footer.jsp" %>