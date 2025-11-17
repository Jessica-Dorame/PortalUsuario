package Control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, 
        maxFileSize = 5 * 1024 * 1024, 
        maxRequestSize = 10 * 1024 * 1024) 
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        Part filePart = req.getPart("profileImage");
        if (filePart == null || filePart.getSize() == 0) {
            req.setAttribute("message", "No se seleccionó archivo");
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
            return;
        }

        String uploadsPath = getServletContext().getRealPath("/uploads");
        File uploadsDir = new File(uploadsPath);
        if (!uploadsDir.exists()) uploadsDir.mkdirs();

        String submitted = getFileName(filePart);
        // sanitizar el nombre básico
        String safeName = submitted.replaceAll("[^a-zA-Z0-9.\\-\\_]", "_");
        String filename = username + "_" + System.currentTimeMillis() + "_" + safeName;

        File file = new File(uploadsDir, filename);

        try {
            Files.copy(filePart.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServletException("Error al guardar archivo", e);
        }

        String relativePath = req.getContextPath() + "/uploads/" + filename;
        req.getSession().setAttribute("profileImage", relativePath);

        req.setAttribute("message", "Archivo subido correctamente: " + relativePath);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    private String getFileName(Part part) {
        String header = part.getHeader("content-disposition");
        if (header == null) return null;
        for (String cd : header.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename;
            }
        }
        return null;
    }
}
