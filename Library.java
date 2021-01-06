import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {

    private String libraryName;
    private ArrayList<String> unordered = new ArrayList<String>();
    private ArrayList<String> unorderedBooks = new ArrayList<String>();
    private SortedArrayList<Book> books = new SortedArrayList<Book>();
    private SortedArrayList<User> users = new SortedArrayList<User>();
    private final Book blank = new Book("blank", "blank", "blank", false);

    public Library(String libraryName){
        this.libraryName = libraryName;
    }

    public static void main(String[] args) { }

    /*
    The retrieve data method takes the data from a file and stores it in an array list so the data in the file can be manipulated,
    The method returns the arraylist with the raw data, as there is the possibility to add books and users manually I've handled the exception as the program could still be used.
     */
    public ArrayList retrieveData() {

        try {
            File f = new File("input.txt");
            Scanner scan = new Scanner(f);

            while (scan.hasNext()) {
                String data = scan.nextLine();
                unordered.add(data);
            }

        }

        catch (FileNotFoundException e1) {
            System.out.println("\nError: the file holding user and book data has not been found.\n");
        }

        return unordered;
    }

    /*
    The split data method takes the raw data from the retrieve data method and splits it into users and books,
    It uses the fact that the first value in the file multiplied by 2 is the number of lines of book data in the file and splits the data at this point,
    The method returns a new array list with all the book data, leaving the user data in the old arraylist.
     */
    public ArrayList splitData() {

        try {
            int users = Integer.parseInt(unordered.get(0));
            int records = (users * 2) + 1;

            for (int i = 0; i < records; i++) {
                String data = unordered.remove(0);
                unorderedBooks.add(i, data);
            }
        }

        catch (IndexOutOfBoundsException e2) {
            System.out.println("Error: There have been errors initialising the program, once the main menu is displayed please try restarting the program or else the data you expect to use will not be available.\n");
        }

        return unorderedBooks;
    }

    /*
    The create book array method sorts through the books arraylist and arranges the data into book objects,
    The method returns a sorted array list made up of books that can then be sorted alphabetically.
     */
    public SortedArrayList createBookArray() {

        for(int i = 1; i < unorderedBooks.size()-1; i++) {
            String firstName = null;
            String lastName = null;

                String bookName = unorderedBooks.get(i);
                String fullName = unorderedBooks.get(i + 1);

                String[] temp = fullName.split(" ");
                if(temp.length == 3) {
                    firstName = temp[0] + " " + temp [1];
                    lastName = temp[2];
                }
                else {
                    firstName = temp[0];
                    lastName = temp[1];
                }

                Book newBook = new Book(firstName, lastName, bookName, false);
                books.add(newBook);

                i++;
            }

        return books;
    }

    /*
    The create user array method looks through the users arraylist and arranges the data into user objects,
    The method returns a sorted array list made up of users that can then be sorted alphabetically.
     */
    public SortedArrayList createUserArray() {

        for(int i = 1; i < unordered.size(); i++) {
            Book[] booksBorrowed = new Book[3];
            booksBorrowed[0] = blank;
            booksBorrowed[1] = blank;
            booksBorrowed[2] = blank;

            String fullName = unordered.get(i);
            String[] temp = fullName.split(" ");
            String firstName = temp[0];
            String lastName = temp[1];

            User newUser = new User(firstName, lastName, booksBorrowed);
            users.add(newUser);

        }

        return users;
    }

    /*
    The organise books method takes the sorted array list of books and uses the compare to method in the books class to sort the books by author surname,
    The organised sorted array list is returned and can now be used by other methods, this method uses the insertion sort algorithm.
     */
    public SortedArrayList<Book> organiseBooks(){
        int loc;

        for(loc = 1; loc < books.size(); loc++) {

            Book value = books.get(loc);
            int location;

            for(location = loc; location > 0; location--) {

                if(books.get(location - 1).compareTo(value) > 0) {

                    Book temp = books.get(location - 1);
                    books.set(location - 1, value);
                    books.set(location, temp);

                } else { break; }

            }

        }

        /*for(Book book : books) {
            System.out.println(book.toString());
        }*/

        return books;
        //This method does work, the order is based on the surname not the first name
    }

    /*
    The organise users method takes the sorted array list of users and uses the compare to method in the user class to sort the users by user surname and then first name,
    The organised sorted array list is returned and can now be used by other methods, this method uses the insertion sort algorithm.
     */
    public SortedArrayList<User> organiseUsers(){
        int loc;

        for(loc = 1; loc < users.size(); loc++) {

            User value = users.get(loc);
            int location;

            for(location = loc; location > 0; location--) {

                if(users.get(location - 1).compareTo(value) > 0) {

                    User temp = users.get(location - 1);
                    users.set(location - 1, value);
                    users.set(location, temp);

                } else { break; }

            }

        }
        return users;
    }

    /*
    The get create data method runs all the methods needed the organise the data from the input file,
    This means the program only has to call one method in order to get the data from a file, organise it and place it into sorted array lists.
     */
    public void getCreateData() {
        retrieveData();
        splitData();
        createBookArray();
        createUserArray();
        organiseBooks();
        organiseUsers();
    }

    /*
    The checkout book method reviews a user's book array to see how many books they have borrowed, if they have not borrowed the maximum number of books there will be placeholders in their book array,
    If a position is filled by a placeholder (a "blank" book) that position is set to the book to be borrowed and the book is marked as on loan, if their array is full a message is displayed.
    */
    public void checkoutBook(User user, Book book) {

        if (book.getOnLoan() == false) {

            Book[] booksBorrowed = user.getBooks();
            //the code below checks if the user has books in various points in their book array.
            if (booksBorrowed[0] == blank) {

                booksBorrowed[0] = book;
                System.out.println("\n" + user.getFirstName() + " " + user.getLastName() + " has borrowed the book: " + book.getBookName() + " by " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + "\n");
                user.printNumBooksBorrowed(blank);
                book.setOnLoan(true);

            } else if (booksBorrowed[1] == blank) {

                booksBorrowed[1] = book;
                System.out.println("\n" + user.getFirstName() + " " + user.getLastName() + " has borrowed the book: " + book.getBookName() + " by " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + "\n");
                user.printNumBooksBorrowed(blank);
                book.setOnLoan(true);

            } else if (booksBorrowed[2] == blank) {

                booksBorrowed[2] = book;
                System.out.println("\n" + user.getFirstName() + " " + user.getLastName() + " has borrowed the book: " + book.getBookName() + " by " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + "\n");
                user.printNumBooksBorrowed(blank);
                book.setOnLoan(true);

            } else {
                System.out.println("\nThis user has already borrowed three books, they cannot borrow any more books.\n");
            }

        } else {
            System.out.println("\nThis book is already on loan\n");
            bookOnLoan(book);
        }

    }

    /*
    The book on loan method is used when the book a user wants to borrow has already been lent to a user,
    This method takes the book to be borrowed as a parameter and looks through all the users to find out which user has borrowed the book,
    Once the for loop finds the user who has borrowed the book it uses another method to print a message to send to the user.
     */
    public void bookOnLoan(Book bookToLoan) {
        int loc;
        Boolean found = false;

        while (!found) {

            for (loc = 0; loc < users.size(); loc++) {

                User currentUser = users.get(loc);

                Book[] temp = currentUser.getBooks();

                /*
                The following if statement looks at the user's book array and checks if they have borrowed the book we are looking for,
                If the user has borrowed the book, a message is printed asking them to return the book, the message is printed by another method.
                 */
                if(temp[0].equals(bookToLoan)) {
                    loanedBookMessage(currentUser, temp[0]);
                    found = true;}

                else if(temp[1].equals(bookToLoan)) {
                    loanedBookMessage(currentUser, temp[1]);
                    found = true;}

                else if(temp[2].equals(bookToLoan)) {
                    loanedBookMessage(currentUser, temp[2]);
                    found = true;
                }

                else { }
            }
        }
    }

    /*
    The loaned book message takes the user and book passed through as parameters, gets the required information from these objects and writes a message to a file asking the user to return a book they have borrowed.
     */
    public void loanedBookMessage(User user, Book book) {

        try {
            FileWriter print = new FileWriter("output.txt", true);

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String bookName = book.getBookName();

            print.append("Hi " + firstName + " " + lastName + ",\n\nWe've been made aware that you have the book '" + bookName + "' on loan currently.\nWe would appreciate if you could return this book as soon as possible, as another Library member would like to borrow it.\n\nMany thanks\n" + libraryName + " Library" +
                    "\n\n----------------------------------------------------------------------------------\n");

            print.close();
        }

        catch (IOException e1) {
            System.out.println("\nError: the file to print the return message to has not been found, return book messages are not being printed.\n");
        }
    }

    /*
    The return book method takes the user and book passed through as parameters and looks to see if the user has borrowed the book they are returning,
    If the user has borrowed the book it is removed from their array and the book is no longer marked as on loan, if not a message is displayed.
     */
    public void returnBook(User user, Book book) {

        Book[] booksTemp = user.getBooks();

        if((booksTemp[0] == blank && booksTemp[1] == blank && booksTemp[2] == blank)) {
            System.out.println("\nThis user has not borrowed any books, please check the user information given.");
        }

        else if(booksTemp[0].equals(book)) {
            book.setOnLoan(false);
            System.out.println("\nThe book " + book.getBookName() + " by " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + " has been returned\n");
            booksTemp[0] = blank;
        }

        else if(booksTemp[1].equals(book)) {
            book.setOnLoan(false);
            System.out.println("\nThe book " + book.getBookName() + " by " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + " has been returned\n");
            booksTemp[1] = blank;
        }

        else if(booksTemp[2].equals(book)) {
            book.setOnLoan(false);
            System.out.println("\nThe book " + book.getBookName() + " by " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + " has been returned\n");
            booksTemp[2] = blank;
        }

        else if((!booksTemp[0].equals(book)) && (!booksTemp[1].equals(book)) && (!booksTemp[2].equals(book))) {
            System.out.println("\nThis user has not borrowed this book");
        }
        else {}
    }

    public SortedArrayList<Book> getBooks() {
        return books;
    }

    public SortedArrayList<User> getUsers() {
        return users;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public Book getBlank() {
        return blank;
    }
}
