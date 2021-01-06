import java.util.Objects;

public class User implements Comparable<User> {

    private String firstName;
    private String lastName;
    private Book[] books = new Book[3];

    public User(String firstName, String lastName, Book[] books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public static void main(String[] args) {
    }

    public int compareTo(User user) {
        int lnCmp = lastName.compareTo(user.getLastName());
        if(lnCmp != 0) {return lnCmp;}
        int fnCmp = firstName.compareTo(user.getLastName());
        if(fnCmp != 0) {return fnCmp;}
        return lnCmp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Book[] getBooks() {
        return books;
    }

    /*
    The print books borrowed method looks into the user's book array and prints a list of the books they've borrowed to the console.
     */
    public void printBooksBorrowed(Book blank) {
        System.out.println("\n" + firstName + " " + lastName + " has borrowed the following books:\n");
        for(Book book: books) {
            if (book == blank) {}
            else {System.out.println(book.toString());}
        }
    }

    /*
    The print num books borrowed method figures out how many books a user has borrowed by assessing how many "blank" books they have in their book array
    After figuring out how many books a user has borrowed it prints it to the console.
     */
    public void printNumBooksBorrowed(Book blank) {

        if(books[0] == blank && books[1] == blank && books[2] == blank) {
            System.out.println("This user has not borrowed any books.\n");
        }
        else if((books[0] == blank && books[1] == blank) || (books[1] == blank && books[2] == blank) || (books[0] == blank && books[2] == blank)) {
            System.out.println("This user has borrowed 1 book.\n");
        }
        else if((books[0] == blank) || (books[1] == blank) || (books[2] == blank)) {
            System.out.println("This user has borrowed 2 books.\n");
        }
        else {System.out.println("This user has borrowed 3 books.\n");}

        }

    @Override
    public String toString() {
        return String.format("User Information\n" +
                "First Name: %s\n" +
                "Last Name: %s",
                firstName,
                lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

}
