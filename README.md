# Spring-Boot Cheat sheet

## Spring Boot JPA
### Tools
#### H2
  - It provides an in-memory relational database.

#### Lombok
  **Important:** In VSCODE install lombok annotation extension
  - Minimizes boilerplate code
    
_Example (This generates automatically getters/setters/constructors):_
```
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {}
```

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
#### Creating JPA CrudRepository
JPA provides a **CrudRepository** with an interface
   ```
   public interface NameRepository extends CrudRepository<Entity, Long> {}
   ```
  You have to implement this interface in your ServiceImplementation class 
   ```
   public class NameServiceImpl implements NameService {
       @Autowired
       NameRepository nameRepository
   }
   ```
#### Custom queries
Naming convention:
```
Name (Action)findBy(Field value)column_name(Long id);
```
#### Unidirectional Relationships: Many to One
1. **Many** table are associated with **One** table
2. The child manages the foreign key
```
@ManyToOne(optional = false)
@JoinColumn(name = "column_name", refferencedColumnName = "foreign_key")
    private Name name;
```
#### Bidirectional: One to Many
Provides navigational access from both sides
mappedBy goes on the non-owning side of the relationship
```


