# Clínica Dental

Aplicación web para gestionar pacientes y citas de una clínica dental. Está desarrollada
con **Java Servlets, JSP/JSTL, JDBC y MySQL**, siguiendo una separación MVC: los servlets
controlan las peticiones, los DAO concentran el acceso a datos, los modelos representan
las entidades y las JSP se limitan a mostrar la información.

## Estructura

```text
dwes_scriplet_dentista/
├── pom.xml
└── src/main/
    ├── java/es/dwes/dentista/
    │   ├── dao/
    │   │   ├── Conexion.java
    │   │   ├── PacienteDAO.java
    │   │   ├── CitaDAO.java
    │   │   └── OdontologoDAO.java
    │   ├── model/
    │   │   ├── Paciente.java
    │   │   ├── Cita.java
    │   │   └── Odontologo.java
    │   └── servlet/
    │       ├── PacienteServlet.java
    │       └── CitaServlet.java
    ├── resources/dentista.sql
    └── webapp/
        ├── index.jsp
        ├── estilos.css
        └── WEB-INF/vistas/
            ├── listadoPacientes.jsp
            ├── formularioPaciente.jsp
            ├── listadoCitas.jsp
            └── formularioCita.jsp
```

Las vistas están dentro de `WEB-INF`, por lo que no se pueden invocar directamente. Las
peticiones se procesan a través de los servlets y estos envían los datos a la vista usando
atributos de petición. Las conexiones JDBC se crean desde `dao.Conexion`; las consultas y
operaciones SQL están en sus DAO correspondientes.

## Funcionalidad y rutas

| Método | Ruta | Acción |
|---|---|---|
| GET | `/` | Menú principal |
| GET | `/pacientes` | Listado de pacientes y número de citas |
| GET | `/pacientes/nuevo` | Formulario de alta de paciente |
| POST | `/pacientes/guardar` | Alta de paciente |
| POST | `/pacientes/eliminar` | Baja de paciente y sus citas asociadas |
| GET | `/citas` | Próximas citas |
| GET | `/citas?todas=1` | Histórico completo de citas |
| GET | `/citas/nueva` | Formulario de alta de cita |
| GET | `/citas/nueva?paciente=ID` | Formulario con el paciente preseleccionado |
| POST | `/citas/guardar` | Alta de cita |
| POST | `/citas/eliminar` | Anulación de cita |

## Base de datos

El script `src/main/resources/dentista.sql` crea la base de datos `dentista`, las tablas
`paciente`, `odontologos` y `cita`, y carga datos de ejemplo. Las citas de ejemplo se
calculan respecto al día en que se importa el script. Impórtalo con un usuario que tenga
permisos para crear bases de datos:

```bash
mysql -u root -p < src/main/resources/dentista.sql
```

La conexión predeterminada espera MySQL en `localhost:3306`, base de datos `dentista`,
usuario `user` y contraseña `user`. Si ese usuario no existe, créalo y concédele acceso:

```sql
CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';
GRANT ALL PRIVILEGES ON dentista.* TO 'user'@'localhost';
```

La configuración puede sobrescribirse con variables de entorno:

| Variable | Valor predeterminado |
|---|---|
| `DENTISTA_DB_URL` | `jdbc:mysql://localhost:3306/dentista?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8` |
| `DENTISTA_DB_USER` | `user` |
| `DENTISTA_DB_PASSWORD` | `user` |

## Requisitos y despliegue

- Java 21.
- Maven 3.8 o superior.
- MySQL Server.
- Apache Tomcat 9.0.x.

El proyecto usa Servlet 4.0 (`javax.servlet`) y JSTL 1.2, por lo que debe desplegarse en
Tomcat 9, no Tomcat 10 o posterior (que utiliza el espacio de nombres `jakarta.*`).

Genera el WAR con:

```bash
mvn clean package
```

El artefacto queda en `target/scriplet_dentista.war`. Despliégalo en Tomcat 9; el contexto
de la aplicación será `/scriplet_dentista`.
