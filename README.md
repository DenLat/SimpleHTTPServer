# Dynamic Content Server

A simple HTTP server implemented in Java that serves dynamic content and static files.

## Features

- Serves HTML pages with dynamic content.
- Handles static files (CSS, JavaScript).
- Supports basic routing for different endpoints.
- Multi-threaded to handle multiple requests concurrently.

## Requirements

- Java 11 or higher
- Maven (for building and running the project)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/DenLat/SimpleHTTPServer.git
cd SimpleHTTPServer
```

### 2. Build the Project

If you are using Maven, you can build the project using the following command:

```bash
mvn clean install
```

### 3. Run the Server

You can run the server using the following command:

```bash
mvn exec:java -Dexec.mainClass="org.app.Main"
```

Alternatively, you can run the `Main` class directly from your IDE.

### 4. Access the Server

Once the server is running, you can access it in your web browser:

- **Home Page**: [http://localhost:8080/](http://localhost:8080/)

### 5. Static Files

The server also serves static files. You can access the CSS file at:

- [http://localhost:8080/static/styles.css](http://localhost:8080/static/styles.css)

### 6. Stopping the Server

To stop the server, you can simply terminate the process in your terminal or IDE.

## Testing

To run the tests, use the following command:

```bash
mvn test
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by various tutorials on building HTTP servers in Java.