<center><h2>Player and database</h2></center>

<center><h4>Author: Ang Li</h4></center> 

<h3>Clean Architecture</h3>

- Controller (Gateway) 
  - Location: player/controller/PlayerController
  - Forwarding and responding to external requests using a service (PlayerService)

- Service (Use Cases):
  - Location: player/service/PlayerService
  - Integrate several database operation(add, delete, update, select) into a specific service, and the rules of the service are defined here

- Dao (Data Access Object):
  - Location: player/dao/PlayerDao
  - Do basic persistence operations, by connecting to MySQL databases(PlayerDaoMysqlImpl) or using serialization and deserialization to do file IO(PlayerDaoFileIoImpl)

- Entity (User -> Player)
  - Location: player/entity/Player
  - The player's entity class, which defines the properties that the player has

<h3>SOLID Principles</h3>

- Single responsibility principle (SRP)
  - Example: Player, PlayerRole, PlayerFactory
  - In my implementation, each class is responsible for a single thing, for example PlayerRole is responsible for enumeration, Player is responsible for defining entities and PlayerFactory is responsible for instantiating entities

- Open/closed principle (OCP)
  - Example: Player(Abstract), CommonPlayer(Subclass) GustPlayer(Subclass)
  - For the definition of the player using the abstract class, the publicly owned properties are not allowed to be modified by subclasses, however subclasses can add their own characteristics as methods if needed

- Liskov substitution principle (LSP)
  - Example: Player(Abstract), CommonPlayer(Subclass) GustPlayer(Subclass)
  - Both subclasses can replace the parent Player, but the parent cannot replace the child

- Interface segregation principle (ISP)
  - Example: PlayerDao(Interface), PlayerService(Interface)
  - Each interface is responsible for providing different functionality, and there is no redundant functionality to be implemented

- Dependency inversion principle (DIP)
  - Example: PlayerServiceImpl depend on PlayerDao(Interface) and Player(Abstract class)
  - High-level modules do not depend on lower-level modules, and dependencies are on abstract classes or interfaces rather than specific implementation classes

<h3>Design Pattern</h3>

- Usage: Factory Pattern

- Location： player/entity/PlayerFactory

- Explanation: This factory is used to instantiate two different player subclasses


<h3>Unit Test</h3>

- Framework: JUnit

- Location

  - test/player/controller/PlayerControllerImplTest 
    - Test Methods: signIn, signUp, update, getAllPlayers, changePassword, changeName 
    - Test Passed: 6 of 6 tests
  
  - test/player/service/PlayerServiceImplTest
    - Test Method: getAllPlayers, changePassword, changeName
    - Test Passed: 3 of 3 tests
  
  - test/player/dao/PlayerDaoImplTest
    - Test Methods: add, getByName, delete, update
    - Test Passed: 4 of 4 tests

- Note: In my tests, there will be persistent files in `./chess/player/` file directory, but JUnit has a convenient callback function @After for post-unit test processing, in which I delete the player persistence files generated by each test, so the tests don't affect each other and don't generate unnecessary extra files for the computer

<h3>To Do</h3>

 - For persistence of data (PlayerDao) there are two ways, file IO based on serialization and deserialization or database operations based on jdbc connection to MySQL. File IO to do persistence(PlayerDaoFileIoImpl) has been implemented, MySQL-based(PlayerDaoMysqlImpl.java) remains to be improved.