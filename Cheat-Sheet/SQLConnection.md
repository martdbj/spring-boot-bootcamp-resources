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
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
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
Visualizes the content of our database

Access the console with the path

Connection name
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
#### Custom queries for Crud Repository
Naming convention:
```
Name (Action)findBy(Field value)column_name(Long id);
```
#### Unidirectional Relationships: Many to One
1. **Many** table are associated with **One** table
2. The child manages the foreign key
```
@ManyToOne(optional = false)
@JoinColumn(name = "column_name", referencedColumnName = "foreign_key")
    private Name name;
```
#### Bidirectional: One to Many (Preferred over unidirectional)
Provides navigational access from both sides
mappedBy goes on the non-owning side of the relationship
We use JsonIgnore for avoiding creating an infinite loop when doing a request
```
@JsonIgnore
@OneToMany(mappedBy = "property_match", cascade = CascadeType.ALL)
```
It's recommended to put the foreign key in the table that can't live without the other
We want to have only the nonNull fields with:
```
@RequiredArgsConstructor 
@Entity

@NonNull
@Column
```
#### Autowired vs AllArgsConstructor
It takes care of dependency injection by itself, you don't need to use autowired or write the constructor by hand again
```
@AllArgsConstructor
@Service
```


