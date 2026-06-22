package cl.duoc.ms_hospitalizacion.model;

@Entity
@Table(name = "hospitalizacion")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hospitalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHospitalizacion")
    private Long idHospitalizacion;

    private Long idMascota;
    private Long idDueno;
    private LocalDate fecha_inicio;
    private LocalDate fecha_alta;
    private String diagnostico;
}