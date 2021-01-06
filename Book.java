import java.util.Objects;

public class Book implements Comparable<Book> {

    private final String authorFirstName;
    private final String authorLastName;
    private final String bookName;
    private Boolean onLoan;

    public Book(String authorFirstName, String authorLastName, String bookName, Boolean onLoan) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.bookName = bookName;
        this.onLoan = onLoan;
    }

    public static void main(String[] args) {
    }

    public int compareTo(Book book) {
        int lnCmp = authorLastName.compareTo(book.getAuthorLastName());
        return lnCmp;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getBookName() {
        return bookName;
    }

    public Boolean getOnLoan() {
        return onLoan;
    }

    public void setOnLoan(Boolean onLoan) {
        this.onLoan = onLoan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getAuthorFirstName().equals(book.getAuthorFirstName()) &&
                getAuthorLastName().equals(book.getAuthorLastName()) &&
                getBookName().equals(book.getBookName()) &&
                getOnLoan().equals(book.getOnLoan());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorFirstName(), getAuthorLastName(), getBookName(), getOnLoan());
    }

    @Override
    public String toString() {
        return String.format("Book Information\n" +
                "Author First Name: %s\n" +
                "Author Last Name: %s\n" +
                "Book Name: %s\n",
        authorFirstName,
        authorLastName,
        bookName);
    }
}
