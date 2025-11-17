<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Login - Portal</title>
    <link rel="stylesheet" href="Estilos/estiloLogin.css">
</head>
<body>
<jsp:include page="header.jspf" />
<div class="login-container">
    <h2>Iniciar sesión</h2>

    <c:if test="${not empty error}">
        <div style="color:red">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label>Usuario</label><br/>
            <input type="text" name="usuario" required/>
        </div>
        <div style="margin-top:8px;">
            <label>Clave</label><br/>
            <input type="password" name="contrasena" required/>
        </div>
        <div style="margin-top:12px;">
            <button type="submit">Entrar</button>
        </div>
    </form>
</div>
<jsp:include page="footer.jspf" />
</body>
</html>
