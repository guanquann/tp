---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/COMPANY [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123 c/John's Burgers`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/West Street 12 p/91234567 c/Great Vegs t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/COMPANY] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, existing tags other than the Favourite tag of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.


### Add Contacts as Favourites `addfav`

- Adds the contacts specified by index as favourites

Format: `addfav [i/INDICES]`
- Adds the contacts at the specified `INDICES` as favourites. The indices refer to comma-separated index numbers (i.e. index, index, index) shown in the displayed person list. Each index **must be a positive integer** 1,2,3, ...

Examples:
- `addfav i/ 1` Sets the contact at index `1` as favourite
- `addfav i/ 1, 1, 1` Sets the contact at index `1` as favourite once
- `addfav i/ 1, 2, 5` Sets the contacts at the indices `1, 2, 5` as favourites
- `addfav i/ -10, 0, -100`, `addfav i/ abc` and `addfav i/////` return an error message as the 'INDICES' field must consist of comma-separated positive integers
- `addfav i/ 10, 1` returns an error message as the 'INDICES' field must consist of valid index values which are positive integers from 1 to the total number of contacts in the address book
- `addfav i/` returns an error message as the 'INDICES' field cannot be empty
- `addfav` returns an error message as it must be accompanied by the 'INDICES' field
- `addfav 1 i/ 2, 5` returns an error message as there should not be prefixes before the 'INDICES' field

### Search Contact `find`

- Search feature supports search by name and/or tags **ONLY**.
- Finds all contacts whose names or tags matches the substring keyword provided.

General Format: `find FIELD/ KEYWORD FIELD/ KEYWORD ...`
- Where `FIELD` is either `n/` for name or `t/` for tag.
- `KEYWORD` is the keyword (**alphabets only**) to search for.

#### Search Guidelines

* 'KEYWORD' can **ONLY** be alphabets and **CANNOT** contain spaces or be empty.
  * e.g. `find n/John Doe` will **NOT** work. Try `find n/John n/Doe` instead to represent finding John and Doe
  * e.g. `find n/` will **NOT** work as 'KEYWORD' cannot be empty.
  * e.g. `find n/John123` will **NOT** work as 'KEYWORD' cannot contain non-alphabetic characters.


* 'KEYWORD' and next 'FIELD' should be separated by a space.
  * e.g. `find n/John t/friends` will find all instances of John that have the tag friends 
  * but `find n/Johnt/tfriends` will instead return an error since it assumes you are searching for 'Johnt/tfriends'
  * and there should not be non-alphabetic characters in the 'KEYWORD' field.


* Multiple of the same 'FIELDs' will be treated as a **Logical AND (&&)**.
  * e.g. `find n/John n/Doe` will return all instances of John and Doe.
  * e.g. `find n/Ale n/le` will still return the following example instances ["Alex Liew", "Alexis Lebrun", "Alec"]


* 'KEYWORD' should **NOT** be empty and there should be at least one 'FIELD' and 'KEYWORD' pair.
  * e.g. `find n/ t/` and `find ` will **NOT** work.


* There should not be prefixes before the first 'FIELD' and 'KEYWORD' pair. 
  * e.g. `find testing123 n/John` will **NOT** work.


* The search is case-insensitive. 
  * e.g. `find n/hans` will match `Hans Niemann` and `Hans Zimmer`

* The order of the keywords does not matter. 
  * e.g. Results of `find n/Hans n/Bo` will match the results of`find n/Bo n/Hans`

* You can have multiple of the same 'FIELD's. 
  * e.g. `find n/J n/Do` will match names with `J` AND `Do`, like `John Doe`


Examples:
* `find n/Joh` returns `john`, `John Doe` and `Johnann Sebastian Bach`

* `find n/alex n/david` returns `Alex Davidson` and `David Alexis`

* `find n/Alex t/friends` returns `Alex Yeoh` who is tagged as a `friend`

* `find n////` returns an error message as the 'KEYWORD' field must consist of alphabets only

* `find n/` or `find t/` or `find n/ t/` returns an error message as the 'KEYWORD' field cannot be empty

* `find` returns an error message as there should be at least one 'FIELD' and 'KEYWORD' pair

* `find testing123 n/John` returns an error message as there should not be 
      prefixes before the first 'FIELD' and 'KEYWORD' pair

### Adding an order : `addorder`

Adds an order to a supplier.

Format: `addorder INDEX d/DATE r/REMARK`

* Adds an order to the supplier at the specified `INDEX`. The index refers to the index number shown in the displayed supplier list. The index **must be a positive integer, starting from 1** (1, 2, 3, …​)
* The date must be in the format `YYYY-MM-DD`. For example, `2020-12-31`.

<box type="tip" seamless>

**Note:** A person can have any number of orders (including 0)
</box>

Examples:
* `addorder 1 d/2020-01-01 r/100 chicken wings`
* `addorder 2 r/ 100 chicken wings d/ 2020-12-31`
* `addorder 3 d/2020-01-01 r/100 chicken wings`
* `addorder d/2020-01-01 r/100 chicken wings` returns an error as the index is not specified
* `addorder r/` or `addorder d/` or `addorder r/ d/` returns an error message as the 'KEYWORD' field cannot be empty

### Listing orders : `listorder`

Shows a list of all orders for a supplier in order of Date.

Format: `listorder INDEX`

* Shows a list of all orders for the supplier at the specified `INDEX`. The index refers to the index number shown in the displayed supplier list. The index **must be a positive integer, starting from 1** (1, 2, 3, …​)


### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action        | Format, Examples                                                                                                                                                                                |
|---------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**       | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/COMPANY [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 c/Freshest Farm t/friend t/colleague` |
| **Clear**     | `clear`                                                                                                                                                                                         |
| **Delete**    | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                             |
| **Edit**      | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [c/COMPANY] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                         |
| **Find**      | `find KEYWORD/ [KEYWORD]`<br> e.g., `find n/ James n/ T t/ friend t/ rich`                                                                                                                      |
| **Add Order** | `addorder INDEX d/DATE r/REMARK`<br> e.g., `addorder 1 d/ 2020-01-01 r/ 100 chicken wings`                                                                                                      |
| **Add Favourite** | `addfav [i/INDICES]`                                                                                                                                                                            |
| **List**      | `list`        
| **Help**      | `help`      
