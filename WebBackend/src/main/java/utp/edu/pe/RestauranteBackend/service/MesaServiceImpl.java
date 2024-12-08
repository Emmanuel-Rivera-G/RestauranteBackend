package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.MesaDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MesaDAO;
import utp.edu.pe.RestauranteBackend.model.Mesa;
import utp.edu.pe.RestauranteBackend.service.interfaz.MesaService;

import java.util.List;

public class MesaServiceImpl implements MesaService {
    private final MesaDAO mesaDAO;

    public MesaServiceImpl(EntityManager entityManager) {
        this.mesaDAO = new MesaDAOImpl(entityManager);
    }

    @Override
    public boolean saveMesa(Mesa mesa) throws Exception {
        try {
            return mesaDAO.saveMesa(mesa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMesa(Mesa mesa) throws Exception {
        try {
            return mesaDAO.deleteMesa(mesa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mesa updateMesa(Mesa mesa) throws Exception {
        try {
            return mesaDAO.updateMesa(mesa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Mesa> findAllMesas() throws Exception {
        try {
            return mesaDAO.findAllMesas();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mesa findMesaById(Long id) throws Exception {
        try {
            return mesaDAO.findMesaById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
