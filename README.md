# Throwable in Java

## Description
This repository is a workshop on `Throwable` in Java, mainly on `Exceptions` subclass. It presents the key points regarding that issue and relevant usage cases.

## Types
Below are listed some of the most common types of `Throwable` in Java. Their usage examples are provided in the `eu.ciechanowiec.exceptions.throwableproducer` package. 

### Exceptions
* ClassNotFoundException
* IOException
* ParseException
* InterruptedException
* RuntimeException:
  * ArithmeticException
  * NullPointerException
  * IndexOutOfBoundsException
  * IllegalArgumentException
  * IllegalStateException
  * ClassCastException

### Errors
* StackOverFlowError

### Mnemonics
Checked exceptions: C **io** P **i**</br>
Unchecked exceptions: **A** n i i **C**

## Throwable tree
![tree](src/main/resources/tree.png)
