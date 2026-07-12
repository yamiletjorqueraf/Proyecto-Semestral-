package cl.duoc.ms_farmacia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_farmacia.dto.MedicamentoDTO;
import cl.duoc.ms_farmacia.model.Medicamento;
import cl.duoc.ms_farmacia.repository.MedicamentoRepository;
import cl.duoc.ms_farmacia.exception.BadRequestException;
import cl.duoc.ms_farmacia.exception.ResourceNotFoundException; 
@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

  
     public Medicamento guardar(MedicamentoDTO dto) {
        // Regla de Negocio / Integridad: Validaciones manuales críticas en el servicio
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del medicamento no debe estar en blanco o vacío");
        }
        if (dto.getPrecio() == null || dto.getPrecio() < 0) {
            throw new BadRequestException("El precio debe ser un valor positivo");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new BadRequestException("El stock no puede ser un número negativo");
        }

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
        // Regla de Negocio / Integridad en actualizaciones
        if (datosNuevos.getNombre() == null || datosNuevos.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre a actualizar no puede estar vacío");
        }
        if (datosNuevos.getPrecio() == null || datosNuevos.getPrecio() < 0) {
            throw new BadRequestException("El precio a actualizar debe ser positivo");
        }
        if (datosNuevos.getStock() == null || datosNuevos.getStock() < 0) {
            throw new BadRequestException("El stock a actualizar no puede ser negativo");
        }

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

