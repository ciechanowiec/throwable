# Throwable in Java

## Description
This repository is a workshop on `Throwable` in Java, mainly on its `Exception` subclass. It presents the key points regarding that issue and relevant usage cases.

Run `main` methods in appropriate classes to see how `Throwable` behaves.

## Types
Below are listed some of the most common types of `Throwable` in Java. Their usage examples are provided in the `eu.ciechanowiec.throwable.throwableproducer` package.

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

## Key words 
* `throwing`
* `try`
* `catch`
* `finally`
* `throw new`

## Key points
* `System.exit(...)`</br>
  * If system `System.exit(...)` command inside the `try` block is executed, the program immediately stops. Even the `finally` block is not executed
  * See `eu.ciechanowiec.throwable.systemexit` package
* `finally`
  * The `finally` block always executes when the `try` block exits. This ensures that the `finally` block is executed even if an unexpected exception occurs
  * See `eu.ciechanowiec.throwable.finallyshowcase` package
* Multicatching
  * With `catch (Exception1 | Exception2 exception)` (`exception` variable is final then)
  * With chained `catch` blocks
  * From the narrower to the broader exception
  * See `eu.ciechanowiec.throwable.multicatch` package
* try-with-resources
  * one or more resources
  * resource - any object that implements `java.lang.AutoCloseable` or `java.lang.Closeable`
  * If any, even unexpected, exception occurs, `close()` methods of all specified resources are called and close those resources
  * See `eu.ciechanowiec.throwable.withresources` package

## Handling exceptions
