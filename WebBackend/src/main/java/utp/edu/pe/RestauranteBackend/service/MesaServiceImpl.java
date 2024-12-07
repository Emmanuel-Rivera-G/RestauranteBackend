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
    public boolean saveMesa(Mesa mesa) {
        return mesaDAO.saveMesa(mesa);
    }

    @Override
    public boolean deleteMesa(Mesa mesa) {
        return mesaDAO.deleteMesa(mesa);
    }

    @Override
    public Mesa updateMesa(Mesa mesa) {
        return mesaDAO.updateMesa(mesa);
    }

    @Override
    public List<Mesa> findAllMesas() {
        return mesaDAO.findAllMesas();
    }

    @Override
    public Mesa findMesaById(Long id) {
        return mesaDAO.findMesaById(id);
    }
}
