package hospital;
import java.util.List;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

// Definimos una entidad (tabla orientada a objetos)
@Entity
class Procedimiento{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String fecha;
    private String tipoProcedimiento;
    private String codigo;
    private String descripcionCodigo;
    private String especialidad;
    
    //@ManyToOne
    private Area area;
    private List<Medico> medicos;

    public Procedimiento() {} // Constructor requerido por JPA
    public Procedimiento(String fecha, String tipoProcedimiento, String codigo, String descripcionCodigo, String especialidad, Area area, List<Medico> medicos ) {
        this.fecha = fecha;
        this.tipoProcedimiento = tipoProcedimiento;
        this.codigo = codigo;
        this.descripcionCodigo = descripcionCodigo;
        this.especialidad = especialidad;
        this.area = area;
        this.medicos = medicos;
    }
    @Override
    public String toString(){
        return "Procedimiento: "+tipoProcedimiento;
    }

    public String get_desCodigo(){
        return descripcionCodigo;
    } 
    public String get_tipoProcedimiento(){
        return tipoProcedimiento;
    }
    public String get_medicos(){
        String nombres = "";
        for (Medico m: medicos) {
            System.out.println(m.get_nomComp());
            nombres = nombres+m.get_nomComp()+"";
        }
        return nombres;
    }
    public String get_especialidad(){
        return especialidad;
    }
}

@Entity
class Paciente{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nombre;
    private String apellidos;
    private String sexo;
    private int edad;
    private String procedencia;
    private String consideracionesMedicas; 

    public Paciente() {} // Constructor requerido por JPA
    public Paciente(String nombre, String apellidos, String sexo, int edad, String procedencia, String consideracionesMedicas) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.edad = edad;
        this.procedencia = procedencia;
        this.consideracionesMedicas = consideracionesMedicas;
    }
    
    public String get_nomComp() {
        return nombre + " " + apellidos;
    }
    
    public String get_conMed(){
        return consideracionesMedicas;
    }   

    public String get_proedencia(){
        return procedencia;
    }
}

@Entity
class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nombre;
    private String apellido;
    private int edad;
    private String sexo;
    private String especialidad;
    private int cedula;
    
    public Medico() {} // Constructor requerido por JPA
    public Medico(String nombre, String apellido, int edad, String sexo, String especialidad, int cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.especialidad = especialidad;
        this.cedula = cedula;
    }
    @Override
    public String toString(){
        return "Medico: "+nombre+" "+apellido+" Especialiad: "+ especialidad;
    }


    public String get_nomComp() {
        return nombre + " " + apellido;
    }
    public String get_especialidad(){
        return especialidad;
    }
    public int get_cedula(){
        return cedula;
    }
}

@Entity
class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //@ManyToOne
    private Paciente paciente;
    
    //@ManyToOne
    private Procedimiento procedimiento;
    
    private String fecha;
    private String unidad;

    public Cita() {} // Constructor requerido por JPA
    public Cita (Paciente paciente, Procedimiento procedimiento, String fecha, String unidad) {
        this.paciente = paciente;
        this.procedimiento = procedimiento;
        this.fecha = fecha;
        this.unidad = unidad;
    }


    public String get_paciente(){
        return paciente.get_nomComp();
    }

    public String get_procedimiento(){//Regresa el nombre del procedimiento
        return procedimiento.get_tipoProcedimiento(); 
    }

    public String get_unidad(){
        return unidad;
    }
}

@Entity
class ProcedimientoQuirurgico extends Procedimiento {    
    private Boolean esAmbulatorio;
    private Boolean requiereHospitalizacion;

    public ProcedimientoQuirurgico() {}

    public ProcedimientoQuirurgico(
        String fecha,
        String tipoProcedimiento,
        String codigo,
        String descripcionCodigo,
        String especialidad,
        Area area,
        Boolean esAmbulatorio,
        Boolean requiereHospitalizacion,
        List<Medico> medicos
    ) {
        super(fecha, tipoProcedimiento, codigo, descripcionCodigo, especialidad, area, medicos);
        this.esAmbulatorio = esAmbulatorio;
        this.requiereHospitalizacion = requiereHospitalizacion;
    }
    @Override
    public String toString(){
        return "Procedimiento: "+this.get_especialidad()+ this.get_tipoProcedimiento();
    }
    public Boolean getEsAmbulatorio() {
        return esAmbulatorio;
    }

    public Boolean getRequiereHospitalizacion() {
        return requiereHospitalizacion;
    }
}

@Entity
class ProcedimientoNoQuirurgico extends Procedimiento {
    
    private Boolean esAmbulatorio;
    private Boolean requiereHospitalizacion;
    private int dias_de_recuperación;
    public ProcedimientoNoQuirurgico() {}

    public ProcedimientoNoQuirurgico(
        String fecha,
        String tipoProcedimiento,
        String codigo,
        String descripcionCodigo,
        String especialidad,
        Area area,
        Boolean esAmbulatorio,
        Boolean requiereHospitalizacion,
        List<Medico> medicos
    ) {
        super(fecha, tipoProcedimiento, codigo, descripcionCodigo, especialidad, area, medicos);
        this.esAmbulatorio = esAmbulatorio;
        this.requiereHospitalizacion = requiereHospitalizacion;
    }

    public Boolean getEsAmbulatorio() {
        return esAmbulatorio;
    }

    public Boolean getRequiereHospitalizacion() { 
        return requiereHospitalizacion;
    }
}

@Entity
class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombreArea;
    private List<String> especialidades;


    public Area() {} // Constructor requerido por JPA
    public Area(String nombreArea, List<String> especialidades) {
        this.nombreArea = nombreArea;
        this.especialidades = especialidades;

    }
    @Override
    public String toString() {
        return "Area[nombre del area = " + nombreArea + ", Especialidad = " + especialidades+"]";
    }
    
    public String get_nomA(){
        return nombreArea;
    }

    public String get_esp(){
        String especialeString = "";
        for(String esp : especialidades) {
            System.out.println(esp);
            especialeString = especialeString+esp+" ";
        }
        return especialeString;
    }   
}


public class Hospital {
    public static void main(String[] args) {
        // Crear conexión a la base de datos ObjectDB (se crea si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Base_de_datos_hospital_orientada_a_objetos.odb");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // Insertar objetos en la base de datos
        tx.begin();
        /*
        em.persist(new Medico("Gabriel","Sanchez Cruz", 39, "Masculino", "Neurologia", 312359)); 
        em.persist(new Medico("Valeria","Martinez Bravo", 34, "Femenino", "Oftalmologia", 981404));
        em.persist(new Medico("Diego","Perez Sanchez", 36, "Masculino", "Pediatria", 139531)); 
        Medico medico1 = new Medico("Gabriel","Sanchez Cruz", 39, "Masculino", "Neurologia", 312359);
        Medico medico2 = new Medico("Valeria","Martinez Bravo", 34, "Femenino", "Oftalmologia", 981404);
        Medico medico3 = new Medico("Diego","Perez Sanchez", 36, "Masculino", "Pediatria", 139531);
        em.persist(medico1);
        em.persist(medico2);
        em.persist(medico3);

        em.persist(new Paciente("Jose","Perez Lopez","Masculino",24,"San Luis","Ninguna"));
        em.persist(new Paciente("Alberto","Lopez Ramirez","Masculino",34,"Puebla","Hipertension"));
        em.persist(new Paciente("Marco","Nutrialez Zapopan","Masculino",19,"Oaxaca","Enanismo"));
        em.persist(new Paciente("Guadalupe","Reyez Perez","Femenino",24,"San Luis","Ninguna"));
        em.persist(new Paciente("Jocelyn","Rosales Meltrozo","Femenino",80,"Tamaulipaz","Elefantismo"));

        Area area1 = new Area("Cirugia general", Arrays.asList("Cirugía de abdomen", "Traumatología", "Cirugía de emergencia"));
        Area area2 = new Area("Patologia", Arrays.asList("Anatomía patológica", "Citopatología", "Patología molecular", "Patología forense"));
        Area area3 = new Area("Radiologia", Arrays.asList("Radiología general", "Radiología intervencionista", "Tomografía computarizada", "Resonancia magnética"));
        Area area4 = new Area("Oncología", Arrays.asList("Oncología médica", "Oncología radioterápica", "Oncología quirúrgica", "Hemato-oncología"));
        Area area5 = new Area("Nefrología", Arrays.asList("Nefrología clínica", "Diálisis y trasplante renal", "Hipertensión secundaria a enfermedad renal"));
        Area area6 = new Area("Colposcopia", Arrays.asList("Ginecología y Obstetricia", "Oncología ginecológica", "Enfermedades de transmisión sexual"));
        
        em.persist(area1);
        em.persist(area2);
        em.persist(area3);
        em.persist(area4);
        em.persist(area5);
        em.persist(area6);
        
        em.persist(new ProcedimientoQuirurgico("17/03/2025", "Extraccion de vesicula biliar", "92w45", "Calculos biliares", "Cirugia de emergencia", area1, false, true, Arrays.asList(medico2)));
        em.persist(new ProcedimientoQuirurgico("25/06/2025", "Biopsia excisional", "B123", "Extraccion quirurgica de tejido para analisis", "Patologia forense", area2, false, true, Arrays.asList(medico2)));
        em.persist(new ProcedimientoQuirurgico("03/02/2025", "Vertebroplastia", "V456", "Inyeccion de cemento oseo en vertebra fracturada", "Radiologia intervencionista", area3, true, false, Arrays.asList(medico3)));
        em.persist(new ProcedimientoQuirurgico("19/10/2025", "Reseccion de tumor óseo", "R789", "Extirpacion quirúrgica de tumor en hueso", "Oncologia quirurgica", area4, false, true, Arrays.asList(medico1)));
        em.persist(new ProcedimientoQuirurgico("21/09/2025", "Nefrectomia parcial", "N321", "Extraccion parcial de riñón", "Nefrología clinica", area5, false, true, Arrays.asList(medico2)));
        em.persist(new ProcedimientoQuirurgico("31/11/2025", "Conizacion cervical", "C654", "Extraccion de tejido cervical", "Oncologia ginecologica", area6, true, false, Arrays.asList(medico3)));

        em.persist(new ProcedimientoQuirurgico("11/08/2025", "Operacion de fimosis","0042","Exceso de prepucio","Cirugia plastica",area1,true,false,Arrays.asList(medico1)));
        em.persist(new ProcedimientoQuirurgico("15/08/2025", "Extraccion de tumor maligno","0x22","Aparicion de tumores cancerigenos","Cirugia oncologica",area4,true,false,Arrays.asList(medico1)));
        em.persist(new ProcedimientoQuirurgico("02/02/2025", "Operacion ocular","0x47","Correccion de globos oculares","Cirugia general",area1,true,false, Arrays.asList(medico1, medico2)));
        em.persist(new ProcedimientoQuirurgico("10/12/2025", "Remocion de cuerpo extraño","0141","Aparicion de un cuerpo extraño en el cuerpo","Ciruigia general",area2,true,false, Arrays.asList(medico2,medico3)));
       

        em.persist(new ProcedimientoNoQuirurgico("5/07/2025","General","009x","Biopsia con aguja fina","Patologia",area1,true,false, Arrays.asList(medico1)));
        em.persist(new ProcedimientoNoQuirurgico("12/07/2025","General","011x","Espirometría","Neumología",area3,true,false, Arrays.asList(medico3)));
        
        
        */
        em.persist(new Medico("Jose Adahir","Illescas Hernandez", 22, "Masculino", "Neurologia", 420)); 
        tx.commit();        
// Consultar objetos
        /*
        System.out.println("Procedimientos almacenados:");
        for (Procedimiento p : em.createQuery("SELECT p FROM Procedimiento p", Procedimiento.class).getResultList()) {
            System.out.println(p);
        }
        */
        System.out.println("Medicos almacenadas:");
        for (Medico m : em.createQuery("SELECT m FROM Medico m", Medico.class).getResultList()) {
            System.out.println(m);
        }
        /*
        System.out.println("Areas almacenadas:");
        for (Area a : em.createQuery("SELECT a FROM Area a", Area.class).getResultList()) {
            System.out.println(a);
        }
        System.out.println("Citas almacenadas:");
        for (Cita c : em.createQuery("SELECT c FROM Cita c", Cita.class).getResultList()) {
            System.out.println(c);
        }
        System.out.println("Procedimiento no quirurgico almacenadas:");
        for (ProcedimientoNoQuirurgico p : em.createQuery("SELECT p FROM ProcedimientoNoQuirurgico p", ProcedimientoNoQuirurgico.class).getResultList()) {
            System.out.println(p);
        }
        System.out.println("Procedimiento quirurgico almacenadas:");
        for (ProcedimientoQuirurgico p : em.createQuery("SELECT p FROM ProcedimientoQuirurgico p", ProcedimientoQuirurgico.class).getResultList()) {
            System.out.println(p);
        }
        */
        // Cerrar conexión
        em.close();
        emf.close();
    }
}