# Throwable in Java

## Description
This repository is a workshop on `Throwable` in Java, mainly on its `Exception` subclass. It presents key points regarding that issue and relevant usage cases.

Run `main` methods in appropriate classes to see how `Throwable` behaves.

## Throwable Types
Below some of the most common types of `Throwable` in Java are listed. Their usage examples are provided in the `eu.ciechanowiec.throwable.throwableproducer` package.

### Exceptions
* ClassNotFoundException
* IOException
* ParseException
* InterruptedException
* NumberFormatException
* RuntimeException:
  * ArithmeticException
  * NullPointerException
  * IndexOutOfBoundsException
  * IllegalArgumentException
  * IllegalStateException
  * ClassCastException

### Errors
* StackOverflowError
* ExceptionInInitializerError

### Mnemonics
Checked exceptions: C **io** P **i** N</br>
Unchecked exceptions: **A** n i i **C**

## Throwable Tree
![tree](src/main/resources/tree.png)

## Key Words 
* `throws`
* `try`
* `catch`
* `finally`
* `throw`

## Key Points
* `System.exit(...)`</br>
  * if the `System.exit(...)` command inside the `try` block is executed, the program immediately stops. Even the `finally` block is not executed
  * see `eu.ciechanowiec.throwable.systemexit` package
* `finally`
  * the `finally` block always executes when the `try` block exits. This ensures that the `finally` block is executed even if an unexpected exception occurs (the unexpected exception is propagated to the caller)
  * an exception thrown from the `finally` block is propagated to the caller as an exception thrown from the outside of the `try-catch` block; in such a case any unexpected exception thrown from the 'try' block and propagated from there to the caller is suppressed by the exception thrown from the `finally` block
  * see `eu.ciechanowiec.throwable.finallyshowcase` package
* multicatching
  * with `catch (Exception1 | Exception2 exception)` (`exception` variable is final then)
  * with chained `catch` blocks
  * from the narrower to the broader exception
  * see `eu.ciechanowiec.throwable.multicatch` package
* try-with-resources
  * one or more resources
  * any object that implements `java.lang.AutoCloseable` or `java.lang.Closeable` can be used as a resource (both interfaces act the same way)
  * if any, even unexpected, exception occurs, `close()` methods of all specified resources are called and close those resources
  * see `eu.ciechanowiec.throwable.withresources` package
* operations on an exception:
  * regarding an exception message
  * regarding an exception cause
  * regarding suppressed exceptions
  * regarding an exception stack stace
  * see `eu.ciechanowiec.throwable.operations` package
* miscellaneous
  * an exception thrown from the `catch` block is propagated to the caller as an exception thrown from the outside of the `try-catch` block; however, the `finally` block is executed anyway 
  * the `return` statement inside the `finally` block will cause any unexpected exception, that might be thrown in the `try` or `catch` block to be discarded and not propagated to the caller
  * `try-finally` block, without the `catch` block is possible, but the exception from the `try` block will be propagated to the caller
  * see `eu.ciechanowiec.throwable.misc` package

## Handling Caught Exceptions
Here are some possibilities of how to handle caught exceptions, although not all of those possibilities can be treated as good practice. Some of listed actions can be mixed:  
* modify the caught exception (e.g. change the stack trace)
* log the exception
* repack (wrap, chain) the caught exception into another exception, i.e. set the caught exception as the cause af a new one 
* perform some logic after catching the exception (e.g. remove the file created inside the `try` block)
* swallow the caught exception (do nothing)
* change the flow of the program
* rethrow the caught exception

Mnemonics: Mo _**lo**_ re || Per _**swa**_ ch || Reth

## Checked vs Unchecked - Controversy
There is a controversy regarding the division of exceptions in Java on the *checked* and *unchecked* ones.
##### Arguments in Favour of the Division:
* Checked exceptions thrown by *critical methods* force to handle them, which improves the program stability
* Checked exceptions *describe some behaviour of the method* 
##### Arguments Against the Division:
* It is possible to develop robust software without checked exceptions (e.g. C++ and C# don't have such a concept)
* Checked exceptions *violate the open/closed principle*. If a checked exception is thrown from a method and the catch is three levels above, that exception must be declared in the signature of each method in the chain. It means that a change at a low level of the software can force signature changes on many higher levels
* Checked exceptions *break encapsulation* because all functions in the path of a throw must know about details of that low-level exception.
