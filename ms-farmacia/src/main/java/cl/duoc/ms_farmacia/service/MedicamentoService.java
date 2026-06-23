package cl.duoc.ms_farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.repository.MedicamentoRepository;

@Service
public class MedicamentoService {
 @Autowired
    private MedicamentoRepository medicamentoRepository;

    // Guarda un nuevo medicamento mapeando desde el DTO hacia la Entidad validada
    public Medicamento guardar(MedicamentoDTO dto) {
        Medicamento m = new Medicamento();
        m.setNombre(dto.getNombre());
        m.setDescripcion(dto.getDescription()); // Se usa getDescription() según tu DTO
        m.setPrecio(dto.getPrecio());
        m.setStock(dto.getStock());
        return medicamentoRepository.save(m);
    }

    // Lista todas las entidades de la base de datos
    public List<Medicamento> listar() {
        return medicamentoRepository.findAll();
    }

    // Busca opcionalmente por ID
    public Optional<Medicamento> findById(Long id) {
        return medicamentoRepository.findById(id);
    }

    // Actualiza los datos controlando si el ID no existe con una excepción limpia
    public Medicamento actualizar(Long id, Medicamento datosNuevos) {
        return medicamentoRepository.findById(id)
                .map(m -> {
                    m.setNombre(datosNuevos.getNombre());
                    m.setDescripcion(datosNuevos.getDescripcion());
                    m.setPrecio(datosNuevos.getPrecio());
                    m.setStock(datosNuevos.getStock());
                    return repository.save(m);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con ID: " + id));
    }

    // Elimina verificando existencia previa
    public void eliminar(Long id) {
        if (medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, ID inexistente: " + id);
        }
        medicamentoRepository.deleteById(id);
    }

    // Verifica si existe (Usado por el endpoint /exists)
    public boolean existePorId(Long id) {
        return medicamentoRepository.existsById(id);
    }
}
