# SDFL
**SDFL** (Simple Data Fix Language) is a language intended to either be compiled into pure SQL regardless of the DBMS, or executed by an interpreter written in Java.

**SDFL** is intended to be used by developers, architects, DBA or even analysts. It is designed to be as readable as possible and strongly structured. It is also designed to be simple, with a short learning curve. It basically does everything SQL can do and offers some built-in API functions for common actions such as data import or extract, regardless of the DBMS. The point is to have a language on top of SQL so we only have to learn this. 

As we work with different technologies, I found out that the main problem with the SQL language is that it's proprietary and depends a lot on the DBMS, which is not a surprise. I guess that's the whole point of the *no-SQL* tendency of these days. Of course, there are some standards in the industry regarding the simple statements, but what about complex business rules? We have to create stored functions, stored procedures, database packages, triggers or even write it in Java or C#. Can't we standardize?

## How it works 
In this section you will find details about the architecture of **SDF**L. it all starts with an interpreter that parses the text source files into POJOs.

### SDFL Interpreter 
The source files are parsed by the interpreter which creates POJOs of every statements found in the code. Those are called SDFL statements that are runnable by the SDFL Executor.

The interpreter also performs the syntax validation. Any error found while parsing the source files are thrown and logged by the interpreter as <code>ParsingException</code>. Each <code>Statement</code> (POJO) contains the original code to help the user correct the code.

### SDFL Compilers 
**SDFL** has (will have!) many different implementations of the compiler. The compiler is used to convert the Statement POJOs into proprietary SQL code based on the implementation of the compiler. For example, The <code>OracleCompiler</code> is used to create SQL and PL/SQL code from the Statement POJOs, and the <code>MySQLCompiler</code> does the same thing but targets the MySQL syntax.

Once the code is compiled into SQL files, the compiler builds a package to make the transfer and the deployment as easy as possible. Depending on the execution environment (OS and software installed), the compiler builds a standardized directory structure and an executor file. The executor file runs the package using the applications installed such as SQL*Plus or the **SDFL** Executor.

During compilation, any error occuring are logged as <code>CompilerError</code>.

###SDFL+ Compiler 
The **SDFL+** compiler is one that has a larger set of statements. Some functions are not available in technology specific compilers and are implemented in the **SDFL+** compiler such as logging, file transfer, emailing, etc.. This compiler also produces a cross-platform executable Java package intended to be used by the **SDFL** Executor. A copy of the executor is included in the package.

###SDFL Executor 
This is a standalone Java application intended to execute the package compiled by the **SDFL+** Compiler. The executor is included in the package, which means the package kind of executes itself. In fact, the package produced by the **SDFL+** compiler contains the executor and the Statement POJOs. The executor is triggered, loads and runs the statements.

## The syntax
In SDFL, S stands for Simple. It's main purpose is to make the DB code less verbose. For example, in SQL, you would do:

```sql
insert into
  CLIENTS (
    CLIENT_ID,
    FIRST_NAME,
    LAST_NAME)
select
  '1001',
  'Sylvain',
  'Cloutier'
from 
  DUAL
where
  not exists (
    select 
      'x' 
    from 
      CLIENTS 
    where 
      CLIENT_ID = '1001');
```
