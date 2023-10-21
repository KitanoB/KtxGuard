![Coverage](https://img.shields.io/badge/Coverage-77-green) ![GraalVM](https://img.shields.io/badge/GraalVM-21-blue) ![Kotlin](https://img.shields.io/badge/Kotlin-100-orange)

# KtxGuard ![Coverage](https://img.shields.io/badge/Coverage-85-green) ![GraalVM](https://img.shields.io/badge/GraalVM-21-blue) ![Kotlin](https://img.shields.io/badge/Kotlin-100-orange)

KTXGuard is a minimal tool built with Ktor (Kotlin 100%). It leverages the power of Kotlin for back-end services and provides robust encryption utilities without external library and without any internet request. (educational purpose only)


## Features    ![Algorithm](https://img.shields.io/badge/Algorithm-AES/DES/RSA-red)

* Robust encryption utilities including AES, DES, and RSA.
* Command-line interface tools for easy management.
* Dependency Injection with Koin for easy management and scalability.
* Unit tests with Kotlin test framework.

## Getting Started 🚀
### Prerequisites
* Java JDK 18 or later
* Kotlin version 1.9.10

### Installation
1. Clone the repository
   ```sh
   git clone https://github.com/YourUsername/KtxGuard.git
   ```

2. Navigate to the repository
   ```sh
	cd KTXGuard
    ```

3. Navigate to the repository
   ```sh
	./gradlew build
	```
### Usage

markdown
Copy code
## USAGE 🛠

To effectively use our tool, you need to familiarize yourself with the available command-line options:

#### 1. Choosing an Algorithm

The algorithm for encryption or decryption is specified with `-a` or `--algorithm`. Currently, the supported algorithms are:
- `rsa`
- `aes`
- `des`

For example:
```shell
-a aes
```

#### 2. Encrypting & Decrypting
To encrypt a string:

```shell
-e "Your String Here" -a ALGORITHM -p PASSWORD
```

To decrypt a string:

```shell
-d "Encrypted String Here" -a ALGORITHM -p PASSWORD
```

To encrypt a file:

```shell
-ef /path/to/your/file.txt -a ALGORITHM -p PASSWORD
```

To decrypt a file:

```shell
-df /path/to/encrypted/file.txt -a ALGORITHM -p PASSWORD
```

#### 3. Specifying an Output
If you'd like to specify an output file for your encrypted or decrypted content, use the -o or --output option:

```shell
-ef /path/to/your/file.txt -a ALGORITHM -p PASSWORD -o /path/to/output/file.txt
```

#### 4. Help & Listing Algorithms
If you need a reminder on the available options:

```shell
-h
```

To list all available algorithms:

```shell
-l
```

#### 5. Generating Passwords & QR Codes
To generate a random password:

```shell
-r
```

To generate a QR code from a public key:

```shell
-qr
```

#### 6. Providing a Password
Specify a password for encryption or decryption using -p or --password:

```shell
-p "YourPasswordHere"
```

Note: If you don't provide a required argument via command line, you'll be prompted to enter it interactively.

## Contributing

Contributions are always welcome! Please see the [Contribution Guide](docs/CONTRIBUTION_GUIDE.md) for more information.

# Disclaimer

This software, "KTX GUARD", was created for learning purposes and is provided "as is", without any warranty. The author holds no responsibility for how this software is used or any consequences arising from its use.

# License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above disclaimer and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


## Contact

[kitanoblog@proton.me](mailto:kitanoblog@proton.me)

<img src="docs/ktxguard.png" alt="logo_ktx_guard" width="100" height="100"/>
