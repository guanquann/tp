[![CI Status](https://github.com/AY2324S2-CS2103T-T16-3/tp/workflows/Java%20CI/badge.svg)](https://github.com/AY2324S2-CS2103T-T16-3/tp/actions)
[![codecov](https://codecov.io/gh/AY2324S2-CS2103T-T16-3/tp/graph/badge.svg?token=VEEBDKIOHF)](https://codecov.io/gh/AY2324S2-CS2103T-T16-3/tp)

![Ui](docs/images/Ui.png)

# GourmetGrid
<!-- * Table of Contents -->
- Introduction
- About
- Quick Start
- Features
  - Viewing help : help
  - Adding a person : add
  - Listing all persons : list
  - Editing a person : edit
  - Adding contacts as favourites : addfav
  - Showing favourite contacts : showfav
  - Removing contacts from favourites : removefav
  - Searching contact : find
  - Adding an order : addorder
  - Listing orders : listorder
  - Deleting an order: deleteorder
  - Deleting a person : delete
  - Clearing all entries : clear
  - Exiting a program : exit


--------------------------------------------------------------------------------------------------------------------


## Introduction


GourmetGrid is a **desktop app to help small restaurant owners manage supplier contacts and orders**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, GourmetGrid can get your day-to-day supplier management tasks done faster than traditional GUI apps.


## About
This user guide provides in-depth documentation on GourmetGrid installation process, system configuration and management. From setting up the app to managing supplier contacts and orders efficiently, we cover everything you need to know to use GourmetGrid effectively.


### How to Use the Guide


- **Navigation**: Use the Table of Contents to find the sections relevant to your needs. This guide is structured logically from initial setup to more complex features, ensuring you would have a smooth learning curve.


- **Conventions**: Throughout this guide, you will find consistent use of terminology and conventions to simplify understanding. Key terms/features are defined, and step-by-step instructions and examples are clearly outlined for your convenience.


- **Examples**: Practical examples are provided to illustrate how features can be used in real-world scenarios. These are intended to give you a better understanding of how to apply the app's functionalities to meet your needs.


**Getting Started**: If you're new to GourmetGrid, we recommend starting with the 'Quick Start' section to get up and running quickly. From there, explore the 'Features' section to discover how to leverage GourmetGrid's full capabilities.


<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `gourmetgrid.jar` from [here](https://github.com/AY2324S2-CS2103T-T16-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for GourmetGrid.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar gourmetgrid.jar` command to run the application.<br>

1. Type the command in the command box and press Enter to execute it.
   Some example commands you can try:

  * `list` : Lists all contacts.

  * `add n/James Lim p/98765432 e/jameslim@example.com a/West Street #01-01 c/The Big Butcher` : Adds a contact named `James Lim` to GourmetGrid.

  * `delete 3` : Deletes the 3rd contact shown in the current list.

  * `clear` : Deletes all contacts.

  * `exit` : Exits the app.

Refer to the [User Guide](https://ay2324s2-cs2103t-t16-3.github.io/tp/UserGuide.html) for details of each command. If unsure, you can also key in the command without additional arguments to check the expected command format, which will be shown in the response prompt.
