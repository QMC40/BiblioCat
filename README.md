Welcome to BiblioCat!

Thank you for choosing our app. Below, you'll find important information about the features, usage, and troubleshooting tips.

App Name: BiblioCat
Version: 1.4
Developer:
Aaron Fortner – afortner@islander.tamucc.edu
Pu Huang – pu.huang@tamucc.edu

Taskings for production of the Alpha version:
Pu Huang:
•	Designed the initial UI forms for the add book and bookshelf functionality by producing XML layout files for the various activities and fragments implemented for this demonstration.
•	Drafted this document implementing corrections from testing results and documentation relevant to this Alpha version of the product.
•	Carried out testing of application and user interface evaluation for ease of use.
Aaron Fortner:
•	Development of code to produce the current level of functionality incorporating agreed-upon layouts and integrating the underlying Kotlin code.
•	Implemented the SQLite basic implementation for the project to allow persistent storage of book data in the application.
•	Contributed details and technical aspects to and edited this document.


Overview:
If you are an avid book collector with a potentially diverse and expansive collection, a user-friendly method of tracking and sharing that collection with other book lovers with an accessible interface is called for. BiblioCat addresses this challenge head-on by offering Android users a comprehensive
solution for managing, organizing, and exploring their libraries. By seamlessly integrating features such
as cataloging, reading progress tracking, title discovery, and community engagement, BiblioCat empowers users to optimize their reading experiences.


Features:
Feature 1: The book counter on the top will remind you of the number of books in your collection or, in the wish list and read section, the appropriate count of items for that aspect of your collection.
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
In this application release, searching for a keyword within the current collection is implemented. The interface and core mechanism for editing and adding books to the collection have been reworked based on user feedback during testing to improve user-friendliness and make use more intuitive.  Currently, the social aspect of the product is still disabled, and placeholder images are embedded in the collection views. Database issues are inhibiting the full implementation of the feature.  Basic integration with the camera faculty of the platform running the application the above-mentioned database problem precludes integrating the obtained image into the collection as the cover for the addition to the collection.


In the final release of this application, entering a title or author in the search bar will search your catalog and the catalogs shared with you for books that fit the search information on a separate page (inoperative) on the opening page; there are buttons to add books to the catalog, view the collection, add a title to the wish list, share the collection data with others, and a journaling functionality to track current reading and history.  In the various views of the members of the collection, limited attributes are displayed to use the screen area efficiently, but in the next iteration of the application, clicking on a member of the current viewing group will open the individual book information on a separate page for easier viewing and full access to all the contents of the entry in the collection.  The ability to search online for information on a title and integrate it into a new entry into the collection is also unavailable, as is the barcode scanning feature.

Demonstration:
1.	Open the application.
2.	The counter at the top of the screen notes the number of books in the collection.
3.	The search bar under the book count accepts a keyword for searching in all fields of the collection's entries looking for a match; in future iterations, the search could be limited to particular fields of the collection if the user chooses.
4.	In the ‘Add Books’ section, you can add all the fields of a new entry to the collection using the reimagined interface by following the field hints and labels and clicking on ‘Add’ to insert the entry into the collection. Currently, the ‘Add with Cover’ feature is not working correctly, and the ‘Search Online’ and ‘Scan Barcode’ selections are not implemented.
5.	The ‘Bookshelf’ feature will list the contents of your collection by title, author, and cover. For each title, the option to delete a book or edit its information is available.
6.	The ‘Wishlist’ feature repeats the operation of the bookshelf only for items you're interested in, and the ‘What am I reading?' feature does the same for your reading history. Both display the count for the category at the top above the scrolling view of the members of that division of your collection.


Progress and known issues:  
•	Database issue with working with images in SQLite is holding up progress on incorporating book covers correctly into the collection.
•	A feature to back up the database must be integrated into the application.
•	A confirmation feature for deleting books from the collection needs to be added to reduce accidental deletion of items, as it’s just too easy to mistakenly hit ‘delete’ right now.
•	Time constraints have limited work on the social and sharing aspects of the application but these are now the foremost features to be worked on in the last development iteration.

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
