# Curso RestAssured

This project demonstrates basic API testing in Java using RestAssured and JUnit. It is intended for learning and practicing REST API automation.

## Project Structure
- `src/main/java` - Main application code (if any)
- `src/test/java` - Test classes using RestAssured and JUnit
- `pom.xml` - Maven configuration file

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/vivianmagda/cursorestassured.git
   ```
2. Navigate to the project directory:
   ```bash
   cd cursorestassured
   ```
3. Install dependencies:
   ```bash
   mvn clean install
   ```

### Running Tests
To run the tests, use:
```bash
mvn test
```

## Example Test
The project includes a sample test (`OlaMundoTest.java`) that sends a GET request to `https://restapi.wcaquino.me/ola` and validates the response.

## Dependencies
- [RestAssured](https://rest-assured.io/)
- [JUnit](https://junit.org/)

## Author
Vivian Magda

## License
This project is licensed under the MIT License.


*Aquietem-se e saibam que eu sou Deus;
sou exaltado entre as nações,
sou exaltado na terra* 
Sl 46.10
