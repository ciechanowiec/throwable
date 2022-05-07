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
* operations on an exception:
  * regarding exception message
  * regarding exception cause
  * regarding suppressed exceptions
  * regarding exception stack stace
  * See `eu.ciechanowiec.throwable.operations` package
* Miscellaneous
  * an exception thrown from the `catch` block is propagated as and exception thrown from the outside of the `try-catch` block
  * `try-finally` block, without `catch` block is possible, but the exception will be propagated to the caller
  * `return` statement inside a `finally` block will cause any exception that might be thrown in the `try` or `catch` block to be discarded
  * See `eu.ciechanowiec.throwable.misc` package

## Handling caught exceptions
Here are possibilities of how to handle caught exceptions, although not all of those possibilities can be treated as good practice. Some of listed actions can be mixed:  
* modify the caught exception (r.g. change the stack trace)
* log the exception
* repack the caught exception into another exception
* perform some logic after catching the exception (e.g. remove the file created inside the `try` block)
* swallow the caught exception (do nothing)
* change the flow of the program
* rethrow the caught exception

## Checked/Unchecked - Controversy
There is a controversy regarding the division of exceptions in Java on the *checked* and *unchecked* ones.
Arguments in favour of the division:
* Checked exceptions thrown by *critical methods* force to handle them, which improves the program stability
* Checked exceptions *describe some behaviour of the method* 
Arguments against the division:
* It is possible to develop robust software without checked exceptions (e.g. C++ and C# don't have such a concept)
* Checked exceptions *violate the open/closed principle*. If a checked exception is thrown from a method and the catch is three levels above, that exception must be declared in the signature of each method in the chain. It means that a change at a low level of the software can force signature changes on many higher levels
* Checked exceptions *break encapsulation* because all functions in the path of a throw must know about details of that low-level exception.
