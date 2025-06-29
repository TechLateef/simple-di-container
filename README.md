

# Simple Java Dependency Injection (DI) Container

[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://java.com)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A lightweight dependency injection container inspired by Spring's `@Component` and `@Inject` annotations, implemented using reflection.

## Features

- ✅ Automatic component scanning (`@Component`)
- 🧠 Dependency injection (`@Inject`)
- 🔍 Reflection-based field injection
- 🏗️ Simple programmatic configuration
- 🧪 Basic DI container functionality

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/TechLateef/simple-di-container.git
   ```
2. Import into your Java project (requires Java 17+)

## Usage

### 1. Define Components
```java
package com.example;

@Component
public class ServiceA {
    @Inject
    private ServiceB serviceB;
    
    public void execute() {
        serviceB.doSomething();
    }
}

@Component 
public class ServiceB {
    public void doSomething() {
        System.out.println("ServiceB working!");
    }
}
```

### 2. Initialize the Container
```java
public class Main {
    public static void main(String[] args) {
        MyInjector injector = new MyInjector("com.example");
        ServiceA service = injector.getObject(ServiceA.class);
        service.execute(); // Output: "ServiceB working!"
    }
}
```

## How It Works

1. **Component Scanning**:
    - Scans the specified package for classes annotated with `@Component`
    - Creates instances of all found components

2. **Dependency Injection**:
    - Injects dependencies into fields marked with `@Inject`
    - Handles both singleton management and dependency resolution

## Dependencies

- [Reflections](https://github.com/ronmamo/reflections) - For classpath scanning
- Java 17+ - For reflection APIs

## Limitations

- Only supports field injection
- No constructor/setter injection
- No circular dependency handling
- Basic scope management (singleton only)

## Future Improvements

- [ ] Add constructor injection
- [ ] Support for interfaces
- [ ] Circular dependency detection
- [ ] Custom scopes (prototype, request, etc.)
- [ ] Lifecycle callbacks (`@PostConstruct`)

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.

## License

[MIT](LICENSE)


