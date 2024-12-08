# ScramblerEncryption

**ScramblerEncryption** is a lightweight Java library for encrypting and decrypting text using custom scrambling algorithms. It’s implemented entirely in plain Java without any external dependencies, making it simple to integrate into any Java project.

---

## Features

- **Custom Scrambling Algorithm**: A straightforward encryption technique.
- **Lightweight**: Pure Java implementation with no external libraries.
- **Symmetric Encryption**: The same key is used for both encryption and decryption.
- **Easy Integration**: Suitable for educational purposes, small projects, or simple use cases.

---

## Use Cases

- **Basic Obfuscation**: Hide sensitive data from plain view.
- **Learning Tool**: Understand encryption principles through a simple implementation.
- **Lightweight Security**: Add basic security to data in transit or at rest.

---

## Installation

Copy the `ScramblerEncryption` class into your Java project. No additional setup or dependencies are required.

---

## Usage

### Basic Example
```java
public class Main {
    public static void main(String[] args) {
        String originalText = "Hello, World!";
        String key = "mySecretKey";

        String encryptedText = ScramblerEncryption.encrypt(originalText, key);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = ScramblerEncryption.decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
