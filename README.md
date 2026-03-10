# Aprendendo o Light Weight Java Game library - LWJGL

Foi criado um projetinho simples desenhando algumas formas geométricas na tela.

As Branches estão separadas com cada forma geométrica.

## Rotating Triangle

Esta branch implementa um triângulo colorido que rotaciona na tela usando LWJGL 3 e OpenGL.

### Características

- Triângulo com cores RGB nos vértices (vermelho, verde, azul)
- Rotação contínua usando transformação de matriz
- Janela de 800x600 pixels
- Usa OpenGL 3.3 Core Profile com shaders modernos

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+

### Como executar

```bash
mvn clean compile
mvn exec:java
```

### Controles

- **ESC**: Fechar a janela
