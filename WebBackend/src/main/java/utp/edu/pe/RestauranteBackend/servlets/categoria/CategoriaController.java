package utp.edu.pe.RestauranteBackend.servlets.categoria;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.CategoriaServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.CategoriaService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

import static utp.edu.pe.server.config.ServletHandler.getterPath;
import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/categoria")
public class CategoriaController extends HttpServletBasic {

    private final CategoriaService categoriaService;

    public CategoriaController(EntityManager entityManager) {
        super(entityManager);
        this.categoriaService = new CategoriaServiceImpl(entityManager);
    }
}
