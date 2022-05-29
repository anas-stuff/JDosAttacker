# A Simple dos attack program implementation using java 😉

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f1993d62ce2c4e4d815d688c20abaa81)](https://app.codacy.com/gh/Anas-Elgarhy/JDosAttacker?utm_source=github.com&utm_medium=referral&utm_content=Anas-Elgarhy/JDosAttacker&utm_campaign=Badge_Grade_Settings)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Anas-Elgarhy_JDosAttacker&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)


> This is a simple DOS attack program, and it is smart enough not to change your IP during the attack 😆, so it is recommended to use a separate tool for disguise or you will find the FPI at your door 🙂

![Screenshot](./Screenshots/jdosattack.gif)

- Requirements:
    - jdk-18 or higher

## Command Line Arguments (All are optional)

| Argument            | Value                    | Description                                  |
|---------------------|--------------------------|----------------------------------------------|
| `-h` or `--help`    | n/a                      | Show help                                    |
| `-v` or `--version` | n/a                      | Show version                                 |
| `-u` or `--url`     | Target website url       | Set target url (*require)                    |
| `-t` or `--threads` | Number of threads        | Set number of threads (*require)             |
| `-n` or `--number`  | Number of requests       | Set number of requests per thread (*require) |
| `--useragent`       | User agent string        | Set the user agent                           |
| `--requestMethod`   | Request method(GET/POST) | Set the request method                       |
| `--connectTimeout`  | Connection timeout       | Set the connection timeout                   |

> *require: Required argument but if not provided, the program will show TUI and get it.

- Examples:
```bash
java -jar JDosAttacker.jar -u http://example.com -t 10 -n 100
```
```bash
java -jar JDosAttacker.jar -u https://example.com -t 10 -n 100 --useragent "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.0.0 Safari/537.36"
```
```bash
java -jar JDosAttacker.jar
```
- Without arguments, the program will show TUI like this:
![TUI](./Screenshots/TUI.png)

> This program was created for the purpose of study only, and I am not responsible for any use outside this purpose

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/summary/new_code?id=Anas-Elgarhy_JDosAttacker)

[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)