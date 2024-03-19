Welcome to BiblioCat!

Thank you for choosing our app. Below, you'll find important information about the features, usage, and troubleshooting tips.

App Name: BiblioCat
Version: 1.0
Developer: 
Aaron Fortner – afortner@islander.tamucc.edu
Pu Huang – pu.huang@tamucc.edu

Taskings for production of the vertical prototype:
Pu Huang:
•	Designed the initial UI forms for the add book and bookshelf functionality by producing XML layout files for the various activities and fragments implemented for this demonstration.
•	Drafted this document implementing corrections from horizontal prototype feedback and documentation relevant to this prototype.
Aaron Fortner:
•	Development of code to produce the functionality of the book addition and bookshelf viewing featured in this prototype, incorporating Pu’s layouts and integrating the underlying Kotlin code.
•	Implemented the SQLite basic implementation for the project to allow persistent storage of book data in the prototype.
•	Contributed details and technical aspects of the prototype to and edited this document.


Overview:
If you are an avid book collector with a potentially diverse and expansive collection, a user-friendly method of tracking and sharing that collection with other book lovers with an accessible interface is called for. BiblioCat addresses this challenge head-on by offering Android users a comprehensive 
solution for managing, organizing, and exploring their libraries. By seamlessly integrating features such 
as cataloging, reading progress tracking, title discovery, and community engagement, BiblioCat empowers users to optimize their reading experiences. 


Features:
Feature 1: The book counter on the top will remind you of the number of books in your collection.
Feature 2: Search a book title to see if it is in your or your friends' book collection.
Feature 3: In "Bookshelf," view your collection.
Feature 4: Keep a wish list of titles you are interested in.
Feature 5: Share your collection catalog with friends locally or over the internet to find readers with similar tastes and share your collections.
Feature 6: Track your reading history and what you are currently reading.

How to Use:
Installation:

Download the app from the Google Play Store.
Open the app after installation.

Use:
In the final release of this application, entering a title or author in the search bar will search your catalog and the catalogs shared with you for books that fit the search information on a separate page (inoperative) on the opening page; there are buttons to add books to the catalog, view the collection, add a title to the wish list, share the collection data with others, and a journaling functionality to track current reading and history. 

For this prototype, the functionality of manually adding a book to the collection, viewing the collection, and providing for the deletion and editing of the existing books. Currently, no imagery is incorporated into the collection data, but in the future, the intent is to display the book cover in the data entry displayed in the bookshelf function.
Demonstration:
1.	Open the application.
2.	The counter at the top of the screen notes the number of books in the collection.
3.	The search bar under the book count to locate a title in the collection is currently not implemented in any way in this prototype.
4.	Click on ‘Add Books’. The ‘Wishlist,’ ‘Share Collection,’ and ‘What am I reading?’ functions are currently unavailable.
5.	In the next screen, click on ‘Manual add.’ This prototype does not support the ‘Scan Book Cover,’ ‘Search Online,’ and ‘Scan Barcode’ functions.
6.	The data fields to input information on the book will appear below the button menu.  
7.	Scroll through the selections, fill in each field, and enter the pertinent information for the title.  
8.	Click the ‘add book’ button to finalize the entry and save it to the collection.
9.	Note the increase in the number of books in the collection at the top of the screen.
10.	Click the ‘back’ button twice to return to the home screen.
11.	Select the ‘Bookshelf’ button to go into the simplified viewing screen for the collection.
12.	On the next screen, you can scroll up and down to view the books in the collection.
13.	Selecting ‘Delete book’ will delete the book from the collection and decrease the collection count.
14.	Selecting ‘Edit’ will allow you to edit that book information in the database like the original data entry form.
15.	All changes to the collection are saved to the application SQLite database and will persist between uses of the application.


Troubleshooting:
App Crashing:

If the app crashes, try closing it and reopening it.
Ensure your device's operating system is up to date.
Slow Performance:

Close background apps.
Check for updates to the app.
Login/Registration Issues:

Verify your internet connection.
Make sure you are entering the correct login credentials.
Other Problems:


Support:
If you encounter any issues not covered in this guide or have suggestions for improvement, please don't hesitate to contact us.


Thank you for using BiblioCat! We hope you enjoy the experience. If you find our app helpful, please leave a positive review on the Google Play Store. Your feedback is invaluable to us.
