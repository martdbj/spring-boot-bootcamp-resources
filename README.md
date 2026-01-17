# Spring-Boot Cheat sheet

## Spring Boot JPA
### Prototypes
#### H2
  - It provides an in-memory relational database.

### Dependencies
```
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### Properties
Visualize content of our database

Access the console with the path

Conection name
```
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.datasource.url=jdbc:h2:mem:project-name
```

### How to use it
#### Creating a table
1. Set up a **table-name** entity
   ```
   @Entity
   @Table(name = example)
   public class Example {}
   ```
2. Map a field to a column with @Column
   ```
   @Column(name = "name_column", nullable = boolean)
   private String example;
   ```
3. If the column is an id use @Id and @GeneratedValue

_Example:_
```
@Entity
@Table(name = "student") 
public class Student {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
}
```
#### Saving data to a table
1. JPA provides a **CrudRepository** with an interface
   ```
   public interface NameRepository extends CrudRepository<Entity, Long> {}
   ```
You have to implement this interface in your ServiceImplementation class
   ```
   public class NameService implements StudentService {}
   ```
3. Make a **post request** with **json**
4. **Save** the entity from the CrudRepository

#### Retrieving data from a table
