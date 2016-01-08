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
This would insert the new <code>CLIENTS</code> records only if it does not already exist. You could use the <code>merge</code> statement (Oracle) or the <code>ingore</code> statement (MySQL), but still, there's lots of differences between the two syntaxes of the same language so insert a record only if it does not exist. In SDFL, we do:

```
insert into CLIENTS using template 
	"1001" -> CLIENT_ID,
	"Sylvain" -> FIRST_NAME,
	"Cloutier" -> LAST_NAME
only if not exist;
```

### Comments
SDFL comments are based on C/C++ comments, meaning that both C++’s single and multiline comment delimiters are available. For single line comments, use <code>//</code>. Everything after this delimiter until the end of the line will be ignored by the compiler. For example:

```
// This line is a single line comment
in package MY_DATA_FIX;
```

Multiline comments start with <code>/*</code> and end with <code>*/</code>, like:
```
/**
 * This is a multiline comment.
 * Created by Fragmatyc
 */
create datafix INIT – Initial load of the data
```

### SDFL Statements
#### Organization statements
SDFL offers a set of statements that are only destined to organize the code properly. SDFL source files are compiled into deployable package that are defined by those set of statements.

#####in package statement
The <code>in package</code> statement is a mandatory statement that must come at the first line of code in any deployable packages, apart from any comments. The purpose of this statement is to tell the compiler in which package the SDFL code is. Usually, the compiler creates a folder with the package identifier and stores the compiled code in it. 

The only parameter this statement accepts is the package identifier and is mandatory. It cannot be a <code>String</code> and must not contain spaces of special characters. Only alpha-numeric characters and underscores <code>_</code> are accepted.

Example:
```
// This creates a package called “PIZZAPP_FIX”
in package PIZZAPP_FIX;
```

#####create datafix statement
This statement is used to create a module in the data manipulation package and simply allows separation between logically related scripts. The compiler usually creates a separated folder to store the compiled files. There must be at least one data fix per package.

The create datafix statement takes 2 parameters. The first one is the identifier of the data fix. Like the package name, only alpha-numeric characters and underscores <code>_</code> are accepted. Then, there is the description. The identifier and the description must be separated by a dash <code>-</code>.

Example:
```
// This creates a new data fix called INIT
create datafix INIT_DDL – Create the tables;

// This one doesn’t have a description
create datafix INIT_LOAD;
```

#### Data modifications statements
##### import statement
One of the things SQL doesn’t provide is a way to simply load data from a CSV or XLSX file. SDFL offers the import statement to achieve this. All you need to specify is the input file and a <code>template</code> that does the mapping between the columns of the file and the DB.

Example:
```
import myFile.csv into MY_TABLE using template
	#1 -> MY_COLUMN_1,
	#2 -> MY_COLUMN_2,
	#3 -> MY_COLUMN_3;
```

This will load <code>myFile.csv</code> into <code>MY_TABLE</code> and the first column of the CSV file will be inserted into <code>MY_COLUMN_1</code>. The arrow operator can be read as for *goes in* or *into*. The above example uses column ids (<code>#1</code>) which means the CSV file does not have a header row. If it does, you can do:
```
import myFile.csv into MY_TABLE using template
	"CSV file column 1" -> MY_COLUMN_1,
	"CSV file column 2" -> MY_COLUMN_2,
	"CSV file column 3" -> MY_COLUMN_3;
```
This syntax will make the compiler skip the header row.

For detailed syntax documentation, see the wiki (to do)

