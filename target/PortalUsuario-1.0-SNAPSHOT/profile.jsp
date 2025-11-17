<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>


<html>
<head>
    <title>Perfil - Portal</title>
    <link rel="stylesheet" href="Estilos/estiloPortal.css">">
</head>
<body>
<jsp:include page="header.jspf" />
<div class="main-container" style="max-width:640px;margin:20px auto;">
    <h2>Profile</h2>

    <p>Usuario autenticado: <b>${sessionScope.user}</b></p>

    <c:if test="${not empty sessionScope.profileImage}">
        <div>
            <p>Imagen de perfil actual:</p>
            <img src="${sessionScope.profileImage}" class="profile-img" alt="Profile"/>
        </div>
    </c:if>

    <hr/>

    <h3>Subir imagen de perfil</h3>
    <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="profileImage" accept="image/*" required />
        <div style="margin-top:8px;">
            <button type="submit">Subir</button>
        </div>
    </form>

    <c:if test="${not empty message}">
        <div style="color:green;margin-top:10px;">${message}</div>
    </c:if>

</div>
<jsp:include page="footer.jspf" />
</body>
</html>
