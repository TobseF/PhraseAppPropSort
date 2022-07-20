# 🛠 PhraseApp property file formatter

[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.61-blue.svg?style=flat&logo=kotlin&logoColor=white)](http://kotlinlang.org)
[![BCH compliance](https://bettercodehub.com/edge/badge/TobseF/PhraseAppPropSort?branch=master)](https://bettercodehub.com/)  
This command line tool to **format** and **sort** Java message properties files in the same way as [PhraseApp](https://phraseapp.com/).

### When to use it:

* You need consistently formatted  `.properties` over all your projects.
* You want to reduce merge conflicts.
* You want to delete unused message keys in phraseapp.
* You like to do a `phraseapp pull` without troubles.
* You use the GitHub Sync.

## Features:

### Formats yor message property files:

 ``` properties
 Popup_Messages_Alter = Alert
 Popup_Messages_Warning = Be careful
 # Some comment for this email 
 Settings_User_Email_Title = Email
 Settings_User_FirstName = First Name
 Settings_User_LastName = Last Name
 Settings_Window_Title  = Title
 ```

### Checks the syntax and warns on errors:

> Warning: Omitting duplicate key (With the same value):
> Double_Key_With_SameValue= 'Value_1'


> Error: Found duplicate key, with different values:  
> Double_Key_With_DifferentValue= 'Value_2'

ℹ For details check out the `MessagePropertyParserTest`.

### Reads the PhraseApp config File

It can read the `phraseapp.yml` file to search for `.properties`-files listed there.
So you can simply call `prosort` your project root dir to sort all configured message properties.

## Usage

Sort & Format all files in the current directory:  
ℹ If a `phraseapp.yml` is present in this dir, only files listed there will be processed.

### Formatting

``` shell
propsort
```

Sort & Format specific files:

``` shell
propsort file1.properties
propsort file1.properties file2.properties
propsort path/file1.properties path/file2.properties
```

### Compare

Compare folders of property files:

``` shell
propsort -compare folder1 folder2
```

### Backup

Backup files to temp dir:

``` shell
propsort -backup $PATH_TO_REPO
```

`$PATH_TO_REPO`: Folder which contains the message property files, or the `phraseapp.yml`.

### Sync

Delete remote keys which are not present in the local message property files.

``` shell
propsort -backup $PATH_TO_REPO
phraseapp $PATH_TO_REPO
propsort -delete-unused -simulate  $PATH_TO_REPO
propsort -delete-unused $PATH_TO_REPO
```

`$PATH_TO_REPO`: Folder which contains the message property files, or the `phraseapp.yml`.
