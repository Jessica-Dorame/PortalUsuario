
package Control;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Map<String, String> USERS = new HashMap<>();
    static {
        USERS.put("alice", "alice123");
        USERS.put("bob", "secret");
        USERS.put("jessica", "clave");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/profile.jsp");
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("usuario");
        String password = req.getParameter("contrasena");

        boolean ok = username != null && password != null && password.equals(USERS.get(username));

        if (ok) {
 
            HttpSession session = req.getSession(true);
            session.setAttribute("user", username);
            session.removeAttribute("loginError");
            resp.sendRedirect(req.getContextPath() + "/profile.jsp");
        } else {
            req.setAttribute("error", "Usuario o contraseña incorrectos");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
