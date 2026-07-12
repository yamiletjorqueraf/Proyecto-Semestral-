package cl.duoc.ms_resultados_examenes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_resultados_examenes.dto.ResultadoExamenDTO;
import cl.duoc.ms_resultados_examenes.exception.BadRequestException;
import cl.duoc.ms_resultados_examenes.exception.ResourceNotFoundException;
import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;
import cl.duoc.ms_resultados_examenes.repository.ResultadoExamenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoExamenService {

    private static final Logger logger = LoggerFactory.getLogger(ResultadoExamenService.class);

    @Autowired
    private ResultadoExamenRepository resultadoexamenRepository;

    public ResultadoExamen guardar(ResultadoExamenDTO dto) {
        logger.info("Iniciando registro de nuevo resultado de examen para Mascota ID: {}", dto.getIdMascota());
        
      
        if (dto.getIdMascota() == null || dto.getIdMascota() <= 0) {
            throw new BadRequestException("El ID de la mascota asociado al examen es inválido o no fue proporcionado.");
        }
        if (dto.getIdPersonal() == null || dto.getIdPersonal() <= 0) {
            throw new BadRequestException("El ID del personal médico a cargo es obligatorio.");
        }

        ResultadoExamen re = new ResultadoExamen();
        re.setIdMascota(dto.getIdMascota());
        re.setTipoExamen(dto.getTipoExamen());
        re.setFechaExamen(dto.getFechaExamen());
        re.setResultado(dto.getResultado());
        re.setIdPersonal(dto.getIdPersonal());
        re.setPersonalCargo(dto.getPersonalCargo());
        
        ResultadoExamen guardado = resultadoexamenRepository.save(re);
        logger.info("Resultado de examen guardado exitosamente con ID: {}", guardado.getIdResultado());
        return guardado;
    }

    public List<ResultadoExamen> listar() {
        logger.info("Obteniendo listado global de exámenes");
        return resultadoexamenRepository.findAll();
    }

    public Optional<ResultadoExamen> findById(Long id) {
        logger.info("Buscando examen por ID: {}", id);
        return resultadoexamenRepository.findById(id);
    }

    public List<ResultadoExamen> buscarPorMascota(Long idMascota) {
        logger.info("Buscando historial de exámenes para la mascota ID: {}", idMascota);
        return resultadoexamenRepository.findByIdMascota(idMascota);
    }

    public ResultadoExamen actualizar(Long id, ResultadoExamen datosNuevos) {
        logger.info("Iniciando actualización del examen ID: {}", id);
        
        return resultadoexamenRepository.findById(id)
                .map(re -> {
                    if (!re.getIdMascota().equals(datosNuevos.getIdMascota())) {
                        logger.error("Intento fallido de alterar idMascota en examen ID: {}", id);
                        throw new BadRequestException("Regla de Negocio: No está permitido cambiar la mascota asignada a un examen clínico ya registrado.");
                    }
                    
            
                    if (!re.getTipoExamen().equalsIgnoreCase(datosNuevos.getTipoExamen())) {
                        throw new BadRequestException("Regla de Negocio: El tipo de examen no puede modificarse. Registre un nuevo examen si corresponde.");
                    }

                    re.setFechaExamen(datosNuevos.getFechaExamen());
                    re.setResultado(datosNuevos.getResultado());
                    re.setIdPersonal(datosNuevos.getIdPersonal());
                    re.setPersonalCargo(datosNuevos.getPersonalCargo());
                    
                    logger.info("Examen ID: {} modificado exitosamente.", id);
                    return resultadoexamenRepository.save(re);
                })
                .orElseThrow(() -> {
                    logger.warn("Examen ID: {} no encontrado para actualizar", id);
                    return new ResourceNotFoundException("Resultado de examen no encontrado con ID: " + id);
                });
    }

    public void eliminar(Long id) {
        logger.info("Petición de eliminación para examen ID: {}", id);
        if (!resultadoexamenRepository.existsById(id)) {
            logger.warn("Cancelando eliminación: ID {} inexistente", id);
            throw new ResourceNotFoundException("No se puede eliminar, el examen clínico con ID " + id + " no existe.");
        }
        resultadoexamenRepository.deleteById(id);
        logger.info("Examen ID: {} eliminado del sistema", id);
    }

    public boolean existePorId(Long id) {
        return resultadoexamenRepository.existsById(id);
    }
}