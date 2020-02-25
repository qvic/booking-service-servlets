<%@ include file="includes/header.jsp" %>

<div class="d-flex h-100 justify-content-center align-items-center">
    <form class="w-25" method="post">
        <h3 class="pb-3 text-center"><fmt:message key="label.sign_up"/></h3>

        <label for="inputEmail" class="sr-only"></label>
        <input name="email" type="text" id="inputEmail" class="form-control"
               placeholder="<fmt:message key="label.email"/>"
               required autofocus>

        <label for="inputName" class="sr-only"></label>
        <input name="name" type="text" id="inputName" class="mt-1 form-control"
               placeholder="<fmt:message key="label.name"/>"
               required minlength="5" maxlength="200">

        <label for="input-password" class="sr-only"></label>
        <input name="password" type="password" id="input-password" class="mt-1 form-control"
               placeholder="<fmt:message key="label.password"/>"
               required pattern="(?=.*\w)(?=.*\d)[\w\d]{5,200}">

        <label for="repeat-password" class="sr-only"></label>
        <input type="password" id="repeat-password" class="mt-1 form-control"
               placeholder="<fmt:message key="label.repeat_password"/>"
               required>

        <button id="submit-button" class="mt-3 btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                key="label.sign_up"/></button>
    </form>
</div>

<script>
    const password = document.getElementById("input-password"),
        repeatPassword = document.getElementById("repeat-password"),
        submit = document.getElementById("submit-button");

    const checkEquality = () => {
        submit.disabled = password.value !== repeatPassword.value;
    };

    password.onchange = checkEquality;
    repeatPassword.onkeyup = checkEquality;
</script>

<%@ include file="includes/footer.jsp" %>
