---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# GourmetGrid

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

GourmetGrid is adapted from [AddressBook-Level3](https://github.com/se-edu/addressbook-level3).

Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5).

AI was used to autocomplete content and occasionally code where appropriate, but always refined by the team and used with caution.
In particular, the [GitHub Copilot](https://github.com/features/copilot) tool was used as an IDE plugin to provide hints on how to complete our sentences.

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S2-CS2103T-T16-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature

The `add` command allows users to create and add a new contact to the list.

Its process largely follows from the previous address book implementation, but with slight modifications for GourmetGrid's unique functions.

#### Sequence Diagram

Below is the sequence diagram for the `add` command process:

<puml src="diagrams/AddSequenceDiagram.puml" alt="AddSequenceDiagram" />

### Add favourite feature

The `addfav` feature allows users to add suppliers as favourites.

#### Design considerations:

**Aspect: How a contact being set as favourite is represented:**

- **Alternative 1 (current choice):** A boolean field in the `Person` class is used to indicate whether a `Person` is a favourite
    - Pros: Make use of the current `Person` class by adding a simple primitive boolean to store information about favourites.
    - Cons: Not a uniform way of representing information in the `Person` class given that all other fields are their own defined classes.

- **Alternative 2:** A custom class field is created in the `Person` class to indicate whether a `Person` is a favourite
    - Pros: Aligns with the information representation of other fields in the `Person` class
    - Cons: Need to create a new `Favourite` wrapper class to store information on whether a person is a favourite. This may result in unnecessary abstraction given that the field and information to be stored are quite rudimentary.

#### Implementation

1. **Command Parsing:** The `AddFavouriteCommandParser` interprets the user input, extracts the specified indices and creates an instance of `AddFavouriteCommand`.
2. **Data Retrieval and Modification:** Upon execution, `AddFavouriteCommand` fetches the contacts specified by the indices and adds them as favourites by modifying the `isFavourite` field.
3. **Output Generation:** A summarising message that includes the names of the contacts modified is then displayed to the user.

#### Sequence Diagram

Below is the sequence diagram for the `addfav` command process:

<puml src="diagrams/AddFavouriteSequenceDiagram.puml" alt="AddFavouriteSequenceDiagram" />

### Remove favourite feature

The `removefav` feature allows users to remove suppliers from favourites.

#### Design considerations:

**Aspect: How a contact being removed from favourite is managed within Person objects:**

- **Alternative 1 (current choice):** Directly remove a contact from favourite by setting its corresponding boolean field in the `Person` class, which indicates whether a `Person` is a favourite, to false
    - Pros: Make use of the current `Person` class by modifying a simple primitive boolean to store information about favourites.
    - Cons: Not a uniform way of representing information in the `Person` class given that all other fields are their own defined classes.

- **Alternative 2:** Remove a contact from favourite by interacting with a custom class field in the `Person` class whose role is to indicate whether a `Person` is a favourite
    - Pros: Aligns with the information representation of other fields in the `Person` class
    - Cons: Need to create and interact with a new `Favourite` wrapper class to store information on whether a person is a favourite. This may result in unnecessary abstraction given that the field and information to be stored are quite rudimentary.

#### Implementation

1. **Command Parsing:** The `RemoveFavouriteCommandParser` interprets the user input, extracts the specified indices and creates an instance of `RemoveFavouriteCommand`.
2. **Data Retrieval and Modification:** Upon execution, `RemoveFavouriteCommand` fetches the contacts specified by the indices and removes them from favourites by modifying the `isFavourite` field.
3. **Output Generation:** A summarising message that includes the names of the contacts modified is then displayed to the user.

#### Sequence Diagram

Below is the sequence diagram for the `removefav` command process:

<puml src="diagrams/RemoveFavouriteSequenceDiagram.puml" alt="RemoveFavouriteSequenceDiagram" />

### List favourite feature

The `listfav` feature allows users to filter the contacts such that only the favourites are shown.

#### Design considerations:

**Aspect: How the end result of filtering manifests:**

- **Alternative 1 (current choice):** The filtering logic follows closely from that of the `find` feature.
    - Pros: Simple and easy to implement given the existing `find` feature.
    - Cons: May result in some similar functionality between `find` and `listfav` features.

- **Alternative 2:** Favourite contacts can be sorted to be above, with non-favourites below but still visible.
    - Pros: Allows users to see all contacts, with favourites at the top for easy access.
    - Cons: May result in confusion regarding the ordering of contacts.

#### Implementation

1. **Command Parsing:** The `ListFavouriteCommandParser` interprets the user input and creates an instance of `ListFavouriteCommand`.
2. **Filtering:** Upon execution, `ListFavouriteCommand` updates the filtering rule through the model to show only favourite contacts.
3. **Output Generation:** The newly filtered list of contacts is then displayed to the user along with a success message.

#### Sequence Diagram

Below is the sequence diagram for the `listfav` command process:

<puml src="diagrams/ListFavouriteSequenceDiagram.puml" alt="ListFavouriteSequenceDiagram" />

### Add order feature

The `addorder` feature allows users to add orders to a contact.

#### Design considerations:

**Aspect: How orders are stored:**

- **Alternative 1 (current choice):** Orders are stored as a list in `Person` class.
  - Pros: Make use of the current `Person` class by allowing it to store a list of orders.
  - Cons: `Person` class become more complicated as more attributes are added.

- **Alternative 2:** Orders are stored in a different storage class such as `OrderStorage`.
  - Pros: `Person` class remains simple.
  - Cons: Need to create a new `OrderStorage` storage class to store orders. This may result in duplicated code since the implementation of `AddressBookStorage` is similar.

#### Implementation

1. **Command Parsing:** The `AddOrderCommandParser` interprets the user input, extracts the index of the specified contact and the date and remark of the order. Then, it creates an instance of `Order` and `AddOrderCommand`.
2. **Data Retrieval and Modification:** Upon execution, `AddOrderCommand` fetches the selected contact from the model and adds the order to the contact.
3. **Output Generation:** A summarising message of the contact with his orders is then displayed to the user.

#### Sequence Diagram

Below is the sequence diagram for the `addorder` command process:

<puml src="diagrams/AddOrderSequenceDiagram.puml" alt="AddOrderSequenceDiagram" />

### List order feature

The `listorder` feature allows users to list all orders associated with a contact in the address book, sorted by date in ascending order first, then sorted by order they were added in if date is the same. This is particularly useful for users who wish to track the order history of contacts efficiently.

#### Design Considerations

- **Aspect:** Sorting orders by date.
    - **Motivation:** Users are likely interested in the most recent orders. Sorting orders by date in ascending order allows users to see the most relevant orders first.
- **Aspect:** Integration with existing data models.
    - **Motivation:** Utilising the existing `Person` and new implemented `Order` models minimizes code redundancy and maintains consistency within the application.

#### Implementation

1. **Command Parsing:** The `ListOrderCommandParser` interprets the user input, extracts the index of the specified person, and creates an instance of `ListOrderCommand`.
2. **Data Retrieval and Sorting:** Upon execution, `ListOrderCommand` fetches the selected person's orders from the model, sorts them by date, and prepares the output string.
3. **Output Generation:** A summarising message that includes the sorted orders is then displayed to the user.

#### Sequence Diagram

Below is the sequence diagram for the `listorder` command process:

<puml src="diagrams/ListOrderSequenceDiagram.puml" alt="ListOrderSequenceDiagram" />

#### Future Enhancements

- **Sorting by Status:** Introduce functionality to sort orders by their status (e.g., pending, completed), providing users with more flexibility in viewing order information.
- **Filtering Options:** Implement filters to allow users to view orders within a specific date range or with particular characteristics, such as orders over a certain value.

### Delete order feature

The `deleteorder` feature allows users to delete a specific order from a supplier's list of orders, ensuring accurate and up-to-date record-keeping.

#### Design Considerations

- **Aspect: How order deletion is managed within Person objects**:

- **Alternative 1 (current choice):** Directly manage orders within the Person class by removing them from the person's orders list.
  - Pros: Utilizes the existing structure of the Person class, allowing for straightforward access and modification of a person's order list.
  - Cons: Adds complexity to the Person class, which now handles both personal information and order management.

- **Alternative 2:** Implement a dedicated order management system within the model.
  - Pros: Separates concerns, making the system more modular and potentially easier to maintain.
  - Cons: Increases system complexity by introducing new components and possibly duplicating list management functionality.

#### Implementation

1. **Command Parsing:** The `DeleteOrderCommandParser` interprets user input to identify the target person and order indices, then creates an instance of `DeleteOrderCommand`.
2. **Data Retrieval and Sorting:**  Upon execution, `DeleteOrderCommand` locates the target person in the model, identifies the correct order based on the provided index (accounting for sorting by date, then addition sequence), and removes it from the person's orders.
3. **Output Generation:** A message confirms the successful deletion of the order.

#### Sequence Diagram

Below is the sequence diagram for the `deleteorder` command process:

<puml src="diagrams/DeleteOrderSequenceDiagram.puml" alt="DeleteOrderSequenceDiagram" />


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- regularly receives deliveries of supplies from different suppliers
- troublesome to make orders when supplies are running low
- has a need to manage a significant number of contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**:

- Our product is specifically tailored to restaurant owners like Bob who juggle multiple supplier relationships by streamlining regular delivery management and simplifying the tracking of supplier schedules.
- Working with both stable and irregular suppliers, it powerfully centralises contacts and smoothens day-to-day tasks.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                          | I want to …​                             | So that I can…​                                                                                |
|----------|----------------------------------|------------------------------------------|------------------------------------------------------------------------------------------------|
| `* * *`  | restaurant owner                 | add contacts with detailed information   | better manage relationship with contacts                                                       |
| `* * *`  | restaurant owner                 | immediately see a list of contacts       | access or contact them more easily                                                             |
| `* * *`  | restaurant owner                 | search by name, tag and company          | quickly find the person I want to contact                                                      |
| `* * *`  | restaurant owner                 | add orders to a contact                  | keep track of my orders with the contact                                                       |
| `* * *`  | restaurant owner                 | views orders of a contact                | keep track of my orders with the contact                                                       |
| `* * *`  | restaurant owner                 | mark certain contacts as favourites      | access them more easily in the future                                                          |
| `* * *`  | restaurant owner                 | unmark certain contacts as favourites    | keep my favourite contact list clean                                                           |
| `* * *`  | frequent user                    | view my favourite contacts               | access or contact them more easily                                                             |
| `* * *`  | long time user                   | edit contact details                     | keep my address book up to date                                                                |
| `* * *`  | long time user                   | delete contacts                          | keep my address book relevant and clean                                                        |
| `* * *`  | long time user                   | delete orders                            | keep my order list relevant and clean                                                          |
| `* * *`  | potential user exploring the app | see examples with sample data on the app | easily visualize how the app will look like when handling data that is typical for my use case |
| `* * *`  | potential user exploring the app | delete all sample data                   | easily add my own data to a clean slate                                                        |
| `* *`    | restaurant owner                 | receive notifications of incoming orders | know the orders that I am expecting today                                                      |
| `*`      | careless user                    | undo my commands                         | reduces the number of actions I have to take                                                   |

### Use cases

(For all use cases below, the **System** is the `GourmetGrid` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a contact**

**MSS**

1.  User requests to list contacts
2.  System shows a list of contacts
3.  User requests to delete a specific contact in the list
4.  System deletes the contact

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. System detects that the contact does not exist.

  - 3a1. System shows an error message.

    Use case ends.

---

**Use case: Find Contacts**

**MSS**

1. User enters a command to find contacts by name, tag, or company with one or more keywords.
2. System searches and displays all contacts matching all the given keywords.

   Use case ends.

**Extensions**

- 1a. The search keywords are missing or incorrectly formatted.
    - 1a1. System shows an error message.

      Use case ends.

- 2a. No contacts match the search criteria.
    - 2a1. System shows a message indicating no contacts were found.

      Use case ends.

---

**Use case: Add an order**

**MSS**

1. User requests to add an order to a contact
2. System adds the order to the contact

   Use case ends.

**Extensions**

- 1a. System detects an error in the user command.
  - 1a1. System shows an error message.

    Use case ends.

- 1b. System detects that the contact does not exist.
  - 1b1. System shows an error message.

    Use case ends.

---

**Use case: List Orders**

**MSS**

1. User requests to list orders for a specific contact.
2. System displays all orders associated with the contact.

   Use case ends.

**Extensions**

- 1a. The specified contact index is out of bounds.
    - 1a1. System shows an error message.

      Use case ends.

- 1b. The specified order index does not exist.
    - 1b1. System shows an error message indicating the order does not exist.

      Use case ends.
  
- 2a. The specified contact has no orders.
    - 2a1. System shows a message indicating there are no orders for the contact.

      Use case ends.

---

**Use case: Delete an Order**

**MSS**

1. User requests to delete a specific order from a specific contact’s list of orders.
2. System deletes the order.

   Use case ends.

**Extensions**

- 1a. System detects an error in the user command.
    - 1a1. System shows an error message.

      Use case ends.

- 1b. The specified order index is out of bounds.
    - 1b1. System shows an error message.

      Use case ends.

- 1c. The specified contact index is out of bounds.
    - 1c1. System shows an error message.

      Use case ends.
---

**Use case: List favourites**

**MSS**

1. User requests to list favourite contacts
2. System lists favourite contacts

    Use case ends.
    
**Extensions**

- 1a. System detects an error in the user command.
    - 1a1. System shows an error message.

    Use case ends.
    
---

**Use case: Add a contact as favourite**

**MSS**

1. User requests to add a contact as favourite
2. System marks the contact as favourite

   Use case ends.

**Extensions**

- 1a. System detects an error in the user command.
    - 1a1. System shows an error message.

    Use case ends.

- 1b. System detects that the contact does not exist.
    - 1b1. System shows an error message.

      Use case ends.
     
- 1c. System detects that the contact is already marked as favourite.
    - 1c1. System shows a warning message.

      Use case resumes from Step 2.

---

**Use case: Removing a contact from favourites**

**MSS**

1. User requests to remove a contact from favourites
2. System removes the contact from favourites

   Use case ends.

**Extensions**

- 1a. System detects an error in the user command.
    - 1a1. System shows an error message.

      Use case ends.

- 1b. System detects that the contact does not exist.
    - 1b1. System shows an error message.

      Use case ends.

- 1c. System detects that the contact is not marked as favourite.
    - 1c1. System shows a warning message.

      Use case resumes from Step 2.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to a reasonable number of contacts with their corresponding information reliably without detriment to performance.
3.  A user with above average typing speed for regular English text should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should work consistently regardless of environment, such as the settings of the phone running it (within reasonable bounds).
5.  Users should easily navigate the app with minimal hesitance.
6.  Should remain functional and fully usable in the event that the user enters unexpected inputs, with appropriate error statement displayed before the app returns to a functional state to continue running.
7.  Should update automatically or have a good way to manually update in case of time zone changes.

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, MacOS
- **Reasonable Number of Contacts**: Set to be 100 for now
- **Hesitance**: Time spent deliberating by user due to uncertainty of UI interactions
- **Address Book**: Often used interchangeably with **Contact List**.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Deleting a contact

1. Deleting a contact while all contacts are being shown

   1. Prerequisites: List all contacts using the `list` command. There should be at least one contact in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   1. Test case: `delete 0`<br>
      Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Finding a contact

1. Finding contacts by names, tags, or companies

   1. Prerequisites: List all contacts using the `list` command. There should be multiple contacts in the address book with varying names, tags, and company affiliations.
   
   1. Test case: `find n/Alice`<br>
      Expected: The list is filtered to include only contacts with names containing "Alice" as a substring. The details of the contacts found are shown in the status message.
   
   1. Test case: `find t/friend`<br>
         Expected: The list is filtered to show only contacts tagged with "friend" as a substring. Details of the action are displayed in the status message.
   
   1. Test case: `find c/Acme`<br>
      Expected: The list is filtered to show only contacts associated with a company containing the "Acme" substring. Status message shows the details of the matches.
   
   1. Test case: `find n/Bob c/XYZ`<br>
      Expected: Contacts that match both the name "Bob" substring and are associated with the company "XYZ" substring are displayed. This is a compound query demonstrating the logical AND operation.


### Adding an order

1. Adding an order to a contact

    1. Prerequisites: List all contacts using the list command. There is only 1 contact in the address book.

    2. Test case: `addorder 1 d/ 2025-01-01 r/ 100 chicken wings`<br>
       Expected: The number of orders of the first contact increases by 1. Details of the contact, with the new order, are shown in the status bar. The date of the new order is 2025-01-01 and the remark is 100 chicken wings.

    3. Test case: `addorder 0 d/ 2025-01-01 r/ 100 chicken wings`<br>
       Expected: No new order is added. An error indicating invalid command format is shown in the status bar.

    4. Test case: `addorder 5 d/ 2025-01-01 r/ 100 chicken wings`<br>
       Expected: No new order is added. An error indicating invalid person index is shown in the status bar.

    5. Test case: `addorder 1 d/ 2025-99-99 r/ 100 chicken wings`<br>
       Expected: No new order is added. An error indicating invalid date format is shown in the status bar.

2. Viewing orders after adding order to a contact.

    1. To view the orders of the first contact after adding an order, use the `listorder 1` command.


### Listing orders

1. Listing orders for a specific contact

   1. Prerequisites: There must be at least one person in the address book who has orders. Use `addorder` command to add orders if necessary.

   1. Test case: `listorder 1`<br>
      Expected: All orders associated with the first contact in the list are displayed. Details of the orders are shown in the status message.

   1. Test case: `listorder 0`<br>
      Expected: No orders are listed. An error message indicating invalid command format is shown in the status message.
 
   1. Test case: `listorder x` (where x is larger than the list size)<br>
      Expected: No orders are listed. An error message indicating invalid person index is shown in the status message.

### Deleting an order

1. Deleting an order from a person's list of orders

   1. Prerequisites: List all contacts using the `list` command. Ensure that at least the first person has multiple orders. You may use `addorder` to add orders to a person.
   
   1. Test case: `deleteorder 1 o/1`<br>
      Expected: The first order from the first person’s order list is deleted. The details of the action are shown in the status message.
   
   1. Test case: `deleteorder 1 o/x` (where x is larger than the number of orders the person has)<br>
      Expected: No order is deleted. An error message indicating an invalid order index is shown in the status message.
   
   1. Test case: `deleteorder 0 o/1`<br>
      Expected: No order is deleted. An error message indicating invalid command format due to an incorrect person index is shown.
   
   1. Test case: `deleteorder x o/1` (where x is larger than the list size)<br>
      Expected: Similar to previous.


### Listing favourites

1. Listing contacts that have been added to favourites

   1. Prerequisites: Add a few contacts to favourites using the `addfav` command.

   1. Test case: `listfav`<br>
      Expected: All contacts that have been added to favourites are displayed. A success response is shown in the status message.

   1. Test case: `listfav x` (where x is any non-space character(s))<bar>
         Expected: An error message indicating invalid command format due to trailing text after `listfav` is shown. No filtering occurs on the displayed list.

2. Removing a contact from favourites after `listfav`

   1. Prerequisites: Call `listfav` successfully.

   1. Test case: `removefav i/1`<br>
      Expected: The first contact currently displayed in list of favourites is removed as favourite. A successful status message shows the name of the contact removed from favourites. Updated full list of contacts is shown.

### Adding contact(s) as favourite

1. Adding contact(s) as favourite

    1. Prerequisites: List all contacts using the `list` command. Three contacts in the list.

    1. Test case: `addfav i/ 1,3`<br>
       Expected: First and third contact are added as favourites. Details of the affected contacts are shown in the status message. 

    1. Test case: `addfav i/ 1`<br>
       Expected: First contact is added as favourite. Details of the affected contact is shown in the status message. A warning regarding contacts that were already in favourites is also shown in the status message. 

   1. Test case: `addfav i/ 0`<br>
      Expected: No contact is added as favourite. An error message indicating invalid command format is shown in the status message.

   1. Test case: `addfav x i/ 1` (where x is any character) <br>
      Expected: Similar to previous.

### Removing contact(s) from favourites

1. Removing contact(s) from favourites

    1. Prerequisites: List all contacts using the `list` command. Three contacts in the list of which the first and third contacts are marked as favourite. You may use `addfav` to add these contacts as favourites.

    1. Test case: `removefav i/ 1,3`<br>
       Expected: First and third contact are removed from favourites. Details of the affected contacts are shown in the status message. 

    1. Test case: `removefav i/ 1`<br>
       Expected: First contact is removed from favourites. Details of the affected contact is shown in the status message. A warning regarding contacts that were not previously in favourites is also shown in the status message. 

    1. Test case: `removefav i/ 0`<br>
       Expected: No contact is removed from favourites. An error message indicating invalid command format is shown in the status message.

    1. Test case: `removefav x i/ 1` (where x is any character) <br>
       Expected: Similar to previous.

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

---

## **Appendix: Planned Enhancements**

Team size: 4

1. **UI improvements:** The current UI shows the orders of a contact in`StatusBarFooter` when `listorder`
command is called. We plan to add an `OrderListPanel` beside the existing `PersonListPanel` to show the orders
of a contact instead. This will allow users to view the orders of a contact in a more user-friendly manner,
without having the need to call `listorder` repeatedly for the same contact whenever a new command updates `StatusBarFooter`.
Furthermore, when a very long field is added, the UI text may be truncated. We plan to add a tooltip to show
the full text when the mouse hovers over the truncated text.

2. **Make `addorder` message more specific:** The current `addorder` command does not show a preview of the
order added, making it inconvenient for users as they have to scroll all the way to end of `StatusBarFooter`
to view their newly added order. We plan to show a preview of the order added. For example:
`Added Order: [100 oranges (by: 2024-04-15)] from Alex Yeoh`.

3. **Raise error when an outdated order date is added:** The current date validation does not check if the
order date is outdated when `addorder` command is called. We plan to raise an error when an outdated order date
is added. For example: `Order date cannot be in the past`.

4. **Support more flexible phone number formats:** The current phone number validation only accepts numerical inputs.
We plan to support more flexible formats, including country codes and special characters. For example:
`+65 1234 5678`, `+129-123-334-5678`.

5. **Improve Search Functionality:** The current implementation of the find command allows users to search for
contacts based on their names, tags, or company names. However, it does not support searching by address, email,
or phone number. We acknowledge that the ability to search by these fields can significantly enhance user experience
by providing more flexibility and efficiency in locating contact information. The initial decision to exclude address,
email, and phone number from the search criteria was based on a focus on the most commonly used identifiers for
quick search and to maintain simplicity in the search interface. We also considered the privacy implications and
the less frequent necessity of searching by personal information such as phone numbers or addresses. However,
in order to enhance the utility of our contact management system, we are planning to introduce expanded search
capabilities. This will include the ability to search for contacts by their phone numbers, email addresses, and
physical addresses. This enhancement aims to provide a comprehensive search functionality that meets the needs
of all users, making the tool more versatile and efficient for locating specific entries.

6. **Warning when using `clear` command:** The current implementation of the `clear` command deletes all contacts
and orders without any warning or confirmation from the user. This can lead to accidental data loss if the user
mistakenly enters the command. To prevent such incidents, we plan to introduce a warning prompt when the `clear`
command is executed. The warning message will inform the user about the irreversible nature of the action and
prompt them to confirm their decision before proceeding with the deletion.

7. **Advanced data validation for inputs:** The current implementation does not check the length of text-based inputs.
We plan to add a reasonable length limit for relevant fields such as `NAME`, `EMAIL`, `ADDRESS`, `TAG`, `COMPANY` and `REMARK`
to prevent database overflow, ensure data integrity, and enhance user experience.

8. **Adding a new contact:** The current implementation of the `add` command does not allow users to add a new
contact with the same name but different address, email, and number. We plan to enhance the `add` command to support the
addition of multiple contacts with the same name but different contact details.
