package cl.duoc.ms_farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.repository.MedicamentoRepository;
import cl.duoc.ms_farmacia.exception.ResourceNotFoundException; 
@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

  
    public Medicamento guardar(MedicamentoDTO dto) {
        Medicamento m = new Medicamento();
        m.setNombre(dto.getNombre());
        m.setDescripcion(dto.getDescripcion()); 
        m.setPrecio(dto.getPrecio());
        m.setStock(dto.getStock());
        return medicamentoRepository.save(m);
    }

    
    public List<Medicamento> listar() {
        return medicamentoRepository.findAll();
    }

    // Busca opcionalmente por ID
    public Optional<Medicamento> findById(Long id) {
        return medicamentoRepository.findById(id);
    }

    
    public Medicamento actualizar(Long id, Medicamento datosNuevos) {
        return medicamentoRepository.findById(id)
                .map(m -> {
                    m.setNombre(datosNuevos.getNombre());
                    m.setDescripcion(datosNuevos.getDescripcion());
                    m.setPrecio(datosNuevos.getPrecio());
                    m.setStock(datosNuevos.getStock());
                    return medicamentoRepository.save(m); 
                })
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con ID: " + id));
    }

    
    public void eliminar(Long id) {
        
        if (!medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, ID inexistente: " + id);
        }
        medicamentoRepository.deleteById(id);
    }

  
    public boolean existePorId(Long id) {
        return medicamentoRepository.existsById(id);
    }
}