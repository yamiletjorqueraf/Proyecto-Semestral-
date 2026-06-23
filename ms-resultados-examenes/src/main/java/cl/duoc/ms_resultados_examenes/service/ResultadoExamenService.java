package cl.duoc.ms_resultados_examenes.service;


import org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi.ecNR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_resultados_examenes.dto.ResultadoExamenDTO;
import cl.duoc.ms_resultados_examenes.exception.ResourceNotFoundException;
import cl.duoc.ms_resultados_examenes.model.ResultadoExamen;
import cl.duoc.ms_resultados_examenes.repository.ResultadoExamenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoExamenService {

    @Autowired
    private ResultadoExamenRepository resultadoexamenRepository;

    public ResultadoExamen guardar(ResultadoExamenDTO dto) {
        ResultadoExamen re = new ResultadoExamen();
        re.setIdMascota(dto.getIdMascota());
        re.setTipoExamen(dto.getTipoExamen());
        re.setFechaExamen(dto.getFechaExamen());
        re.setResultado(dto.getResultado());
        re.setIdPersonal(dto.getIdPersonal());
        re.setPersonalCargo(dto.getPersonalCargo());
        return resultadoexamenRepository.save(re);
    }

    public List<ResultadoExamen> listar() {
        return resultadoexamenRepository.findAll();
    }

    public Optional<ResultadoExamen> findById(Long id) {
        return resultadoexamenRepository.findById(id);
    }

    public List<ResultadoExamen> buscarPorMascota(Long idMascota) {
        return resultadoexamenRepository.findByIdMascota(idMascota);
    }

    public ResultadoExamen actualizar(Long id, ResultadoExamen datosNuevos) {
        return resultadoexamenRepository.findById(id)
                .map(re -> {
                    re.setIdMascota(datosNuevos.getIdMascota());
                    re.setTipoExamen(datosNuevos.getTipoExamen());
                    re.setFechaExamen(datosNuevos.getFechaExamen());
                    re.setResultado(datosNuevos.getResultado());
                    re.setIdPersonal(datosNuevos.getIdPersonal());
                    re.setPersonalCargo(datosNuevos.getPersonalCargo());
                    return resultadoexamenRepository.save(re);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Resultado de examen no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        if (!resultadoexamenRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, ID inexistente: " + id);
        }
        resultadoexamenRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return resultadoexamenRepository.existsById(id);
    }
}