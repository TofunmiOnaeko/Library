import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IO {

    public IO() {}

    /*
    The main method starts the program and enables the user to select from a number of operations.
     */
    public static void main(String[] args) throws FileNotFoundException {
        IO program = new IO();
        Scanner scanner = new Scanner(System.in);
        Library lib1 = new Library("Robinson");
        lib1.getCreateData();

        File exportFile = new File("output.txt");
        PrintWriter toFile = new PrintWriter(exportFile);

        System.out.println("Please input a letter to select an option from the menu below:");
        program.mainMenu();

        boolean exit = false;

        while (!exit) {

            String choice = scanner.nextLine();

            switch (choice) {
                case "f":
                    System.out.println("\nYou have chosen to quit the program.");
                    exit = true;
                    break;

                case "b":
                    System.out.println("\nYou have chosen to display information regarding all the books within the library:\n");
                    SortedArrayList<Book> books = lib1.getBooks();

                    for (Book book : books) {
                        System.out.println(book.toString());
                    }

                    System.out.println("The data you requested is displayed above, you will now be returned to the main menu.");
                    System.out.println("\nPlease input a letter to select an option from the menu below:");
                    program.mainMenu();

                    break;

                case "u":
                    System.out.println("\nYou have chosen to display information regarding all the users at this library:\n");
                    SortedArrayList<User> users = lib1.getUsers();

                    for (User user : users) {
                        System.out.println(user.toString());
                        user.printNumBooksBorrowed(lib1.getBlank());
                    }

                    System.out.println("The data you requested is displayed above, you will now be returned to the main menu.");
                    System.out.println("\nPlease input a letter to select an option from the menu below:");
                    program.mainMenu();

                    break;

                case "i":
                    System.out.println("\nYou have chosen to issue a book to a user");
                    program.issueBook(lib1, toFile);
                    System.out.println("\nPlease input a letter to select an option from the menu below:");
                    program.mainMenu();
                    break;

                case "r":
                    System.out.println("\nYou have chosen to return a book");
                    program.returnBook(lib1);
                    System.out.println("\nPlease input a letter to select an option from the menu below:");
                    program.mainMenu();
                    break;

                    /*The following operations (new user and new book) have been created to make use of the sortArray method in the SortedArrayList class,
                    but as the operations are not mentioned in the project specification they do not appear to the user as options in the main menu.*/
                case "new user":
                    program.createUser(lib1);
                    System.out.println("\nPlease input a letter to select an option from the menu below:");
                    program.mainMenu();
                    break;

                case "new book":
                    program.createBook(lib1);
                    System.out.println("\nPlease input a letter to select an option from the menu below:");
                    program.mainMenu();
                    break;
            }
        }
    }

    /*
    The mainMenu method prints the main menu to the console for the user,
    This method has been used throughout the program to ensure the user sees the menu after completing an operation.
     */
    public void mainMenu() {
        System.out.println("f - Finish running the program\n" +
                "b - Display information about all the books in the library\n" +
                "u - Display information about all the users in the library\n" +
                "i - Issue book to user and update stored data\n" +
                "r - Return book to library and update stored data\n");
    }

    /*
    The issueBook method takes the borrower and book details input by the user, completes checks and then allows or does not allow the borrower to borrow the book.
     */
    public void issueBook(Library library, PrintWriter print) {
        Scanner scanner = new Scanner(System.in);

        SortedArrayList<User> users = library.getUsers();
        SortedArrayList<Book> books = library.getBooks();
        SortedArrayList<User> temp = new SortedArrayList<User>();
        SortedArrayList<Book> temp1 = new SortedArrayList<Book>();

        System.out.println("Please type in the first name of the user you plan to issue the book to:");
        String firstName = scanner.nextLine();

        System.out.println("\nPlease type in the last name of the user you plan to issue the book to:");
        String lastName = scanner.nextLine();

        System.out.println("\nPlease type in the name of the book:");
        String bookName = scanner.nextLine();

        System.out.println("\nPlease type in the surname of the book's author:");
        String authorName = scanner.nextLine();

        //The following block of code looks through the user and book arraylists to see if the user and book details entered match to an existing user or book
        for (int i = 0; i < users.size(); i++) {

            if (firstName.equals(users.get(i).getFirstName()) && lastName.equals(users.get(i).getLastName())) {
                User user1 = users.get(i);

                for (int j = 0; j < books.size(); j++) {
                    if (bookName.equals(books.get(j).getBookName()) && authorName.equals(books.get(j).getAuthorLastName())) {
                        Book book1 = books.get(j);
                        library.checkoutBook(user1, book1);
                    }
                    //If the book details are not found they are added to a temporary arraylist
                    else if (!bookName.equals(books.get(j).getBookName()) || !authorName.equals(books.get(j).getAuthorLastName())) {
                        temp1.add(books.get(j));
                    }

                    else {}
                }
            }
            //If the user details are not found they are added to a temporary arraylist
            else if (!firstName.equals(users.get(i).getFirstName()) || !lastName.equals(users.get(i).getLastName())) {
                temp.add(users.get(i));
            }

            else {}

        }
        //If the user does not exist in the system the temporary arraylist should be the same length as the original user arraylist and a message is printed to the console.
        if(temp.size() == users.size()) {
            System.out.println("\nThis user does not exist in the system, please check if the information entered is correct.\n");
        }

        //If the book does not exist in the system the temporary arraylist should be the same length as the original book arraylist and a message is printed to the console.
        if(temp1.size() == books.size()) {
            System.out.println("\nThis book does not exist in the system, please check if the information entered is correct.\n");
        }

    }

    /*
    The returnBook method takes the borrower and book details given to the librarian, checks if that user has borrowed the book and returns the book if they have borrowed it.
     */
    public void returnBook(Library library) {
        Scanner scanner = new Scanner(System.in);

        SortedArrayList<Book> books = library.getBooks();
        SortedArrayList<User> users = library.getUsers();
        SortedArrayList<User> temp = new SortedArrayList<User>();
        SortedArrayList<Book> temp1 = new SortedArrayList<Book>();

        System.out.println("Please type in the first name of the user returning the book:");
        String firstName = scanner.nextLine();

        System.out.println("\nPlease type in the last name of the user returning the book:");
        String lastName = scanner.nextLine();

        System.out.println("\nPlease type in the name of the book:");
        String bookName = scanner.nextLine();

        System.out.println("\nPlease type in the surname of the book's author:");
        String authorName = scanner.nextLine();

        //The following block of code looks through the user and book arraylists to see if the user and book details entered match to an existing user or book
        for (int i = 0; i < users.size(); i++) {

            if (firstName.equals(users.get(i).getFirstName()) && lastName.equals(users.get(i).getLastName())) {
                User user1 = users.get(i);

                for (int j = 0; j < books.size(); j++) {
                    if (bookName.equals(books.get(j).getBookName()) && authorName.equals(books.get(j).getAuthorLastName())) {
                        Book book1 = books.get(j);
                        library.returnBook(user1, book1);
                    }
                    //If the book details are not found they are added to a temporary arraylist
                    else if (!bookName.equals(books.get(j).getBookName()) || !authorName.equals(books.get(j).getAuthorLastName())) {
                        temp1.add(books.get(j));
                    }

                    else {}
                }
            }
            //If the user details are not found they are added to a temporary arraylist
            else if (!firstName.equals(users.get(i).getFirstName()) || !lastName.equals(users.get(i).getLastName())) {
                temp.add(users.get(i));
            }

            else {}

        }
        //If the user does not exist in the system the temporary arraylist should be the same length as the original user arraylist and a message is printed to the console.
        if(temp.size() == users.size()) {
            System.out.println("\nThis user does not exist in the system, please check if the information entered is correct.\n");
        }
        //If the book does not exist in the system the temporary arraylist should be the same length as the original user arraylist and a message is printed to the console.
        if(temp1.size() == books.size()) {
            System.out.println("\nThis book does not exist in the system, please check if the information entered is correct.\n");
        }

    }

    /*
    The createUser method enables the user to create a new library user,
    Once the library user has been created they are then added to the SortedArrayList of users and the ArrayList is sorted,
    This method has not been requested in the coursework but it has been added to demonstrate that the sortArray Method in the Sorted Array List class works
     */
    public void createUser(Library library) {
        Scanner scanner = new Scanner(System.in);
        Book[] books = new Book[3];
        books[0] = library.getBlank();
        books[1] = library.getBlank();
        books[2] = library.getBlank();

        System.out.println("\nPlease type in the first name of the new user.");
        String firstName = scanner.nextLine();

        System.out.println("\nPlease type in the last name of the new user.");
        String lastName = scanner.nextLine();

        User user = new User(firstName, lastName, books);
        SortedArrayList<User> Users = library.getUsers();

        System.out.println("\nThe new user has been created.");

        Users.sortArray(Users, user);
    }

     /*
    The createBook method enables the user to create a new library book,
    Once the book has been created they are then added to the SortedArrayList of books and the ArrayList is sorted,
    As with the createUser method this method has not been requested in the coursework but it has been added to demonstrate that the sortArray Method in the Sorted Array List class works
     */
    public void createBook(Library library) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nPlease type in the book name.");
        String bookName = scanner.nextLine();

        System.out.println("\nPlease type in the first name of the Author.");
        String firstName = scanner.nextLine();

        System.out.println("\nPlease type in the last name of the Author.");
        String lastName = scanner.nextLine();

        Book book = new Book(firstName, lastName, bookName, false);
        SortedArrayList<Book> Books = library.getBooks();

        System.out.println("\nThe new book has been created.");

        Books.sortArray(Books, book);
    }

}

