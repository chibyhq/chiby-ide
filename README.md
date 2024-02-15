

# Chiby IDE
[![IDE Version](https://jitpack.io/v/chibyhq/chiby-ide.svg)](https://jitpack.io/#chibyhq/chiby-ide) 
[![GitHub Actions Status](https://github.com/chibyhq/chiby-ide/workflows/Java%20CI/badge.svg)](https://github.com/chibyhq/chiby-ide/actions)
[![Java 11](https://img.shields.io/badge/Java-11-green "Java 11")](https://java.com)

A Blockly-based Spring Boot-enabled IDE

# Pre-requisites

* You may need to [install PygameZero on your machine](https://pygame-zero.readthedocs.io/en/latest/installation.html)

## Developer setup

* The Chiby server can automatically initialize the folder ``~/chiby-home`` in your user home. By default, it does not attempt to create the folder, but you can set the property upon run :
```
java -jar chiby-ide.jar --chiby.ide.initialize-home=true
```

* The codebase uses Lombok, in Eclipse, you may need to set it up accordingly to avoid compilation errors.