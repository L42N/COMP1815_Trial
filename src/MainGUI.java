import KotlinAlgo.BubbleSort;
import KotlinAlgo.MergeSort;
import KotlinAlgo.SearchAlgo;
import KotlinClass.Author;
import KotlinClass.Book;
import KotlinClass.DataPersistence;
import KotlinClass.Pub;
import Scala.RadixSort;
import SubGUI.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;


public class MainGUI {
    private JPanel MainPanel;
    private JTable InfoTable;
    private JScrollPane scrollPane;
    private JButton loadButton;
    private JButton searchButton;
    private JButton sortButton;
    private JTextField searchField;
    private JRadioButton BookButton;
    private JRadioButton authorRadioButton;
    private JTextField loadField;
    private JTextField timeTextField;
    private JComboBox sortCombobox;
    private JLabel RuntimeLabel;
    private JButton pubAddNewEntryButton;
    private JButton NUKEButton;
    private JButton refreshButton;
    private JTabbedPane tabbedPane;
    private JPanel runTimePanel;
    private JPanel controlPanel;
    private JButton addNewEntryButton;
    private JButton deleteButton;
    private JButton editButton;
    private JTable authInfoTable;
    private JButton authExitButton;
    private JButton authLoadButton;
    private JButton authSearchButton;
    private JTextField authLoad;
    private JTextField authSearchEntry;
    private JButton authAddNewEntryButton;
    private JButton authEditButton;
    private JButton authDeleteButton;
    private JScrollPane authScrollPane;
    private JPanel bookPanel;
    private JPanel authControlPanel;
    private JTable pubInfoTable;
    private JButton pubLoadButton;
    private JButton pubSearchButton;
    private JTextField pubLoad;
    private JTextField pubSearchEntry;
    private JButton pubEditButton;
    private JButton pubDeleteButton;
    private JPanel authPanel;
    private JPanel pubPanel;
    private JScrollPane pubScrollPane;
    private JButton pubExitButton;
    private JPanel pubControlPanel;
    private List<Book> books;
    private List<Author> authors;
    private List<Pub> pubs;

    public MainGUI() {
        // Load books by retrieving path from text field for use in getBooks function
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                books = DataPersistence.INSTANCE.getBooks(loadField.getText());
                
                bookTable();
            }
        });

        // Load authors by retrieving path from text field for use in getAuthors function
        authLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authors = DataPersistence.INSTANCE.getAuthors(authLoad.getText());
                authorTable();
            }
        });

        // Load publishers by retrieving path from text field for use in getPublishers function
        pubLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pubs = DataPersistence.INSTANCE.getPublishers(pubLoad.getText());
                publisherTable();
            }
        });

        // Add book
        addNewEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookWindow window = new AddBookWindow();
                window.bookWindow();

            }
        });

        // Add author
        authAddNewEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAuthorWindow window = new AddAuthorWindow();
                window.bookWindow();
            }
        });

        // Add publisher
        pubAddNewEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPubWindow window = new AddPubWindow();
                window.bookWindow();
            }
        });

        // Delete book
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteBookWindow window = new DeleteBookWindow();
                window.bookWindow();
            }
        });

        // Delete author
        authDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteAuthorWindow window = new DeleteAuthorWindow();
                window.bookWindow();
            }
        });

        // Delete publisher
        pubDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeletePubWindow window = new DeletePubWindow();
                window.bookWindow();
            }
        });

        // Edit book
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditBookWindow window = new EditBookWindow();
                window.bookWindow();
            }
        });

        // Edit author
        authEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditAuthorWindow window = new EditAuthorWindow();
                window.bookWindow();
            }
        });

        // Edit publisher
        pubEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPubWindow window = new EditPubWindow();
                window.bookWindow();
            }
        });

        // Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var potato = searchField.getText().toLowerCase();

                var filteredBooks = SearchAlgo.INSTANCE.searchTitle(potato,books);

                DefaultTableModel bookModel = (DefaultTableModel) InfoTable.getModel();
                bookModel.setRowCount(0);
                filteredBooks.forEach(book -> {
                    Object[] bookRow = new Object[]{
                            book.getId(),
                            book.getTitle(),
                            book.getAuthors(),
                            book.getYearOfPublication(),
                            book.getPublisher(),
                            book.getSubject()
                    };
                    bookModel.addRow(bookRow);
                });



            }
        });


        // Execute selected sort algorithm
        sortButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == sortButton){

                    // Execute algorithm & record timings
                    String algorithmType = sortCombobox.getSelectedItem().toString();

                    long startTime = System.nanoTime();
                    if (algorithmType.equals("Merge Sort")){
                        MergeSort test = new MergeSort();

                            test.mergeSort(books);


                    }
                    else if (algorithmType.equals("Bubble Sort")) {

                        BubbleSort sort = new BubbleSort();

                        if (BookButton.isSelected()) {
                            sort.bubbleSort(books);
                        } else {
                            sort.bubbleSortAuthor(books);
                        }

                    }

                    else if (algorithmType.equals("Radix Sort")) {
                        RadixSort potato = new RadixSort();

                        if (authorRadioButton.isSelected()) {
                            books = potato.initRadixSort(books,false);
                        } else {
                            books = potato.initRadixSort(books,true);
                        }





                    }

                    long endTime = System.nanoTime();

                    // Calculate algorithm timings and display results
                    System.out.println("That took " + (endTime - startTime) + " nanoseconds");
                    String timings = String.valueOf((endTime - startTime) / 1000);
                    timeTextField.setText(timings + " milliseconds");

                    bookTable();

                }
            }
        });

        // Terminate code
        NUKEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    // Create infotables for book, author and publisher with respective columns
    private void createUIComponents() {
        // TODO: place custom component creation code here
        Object[] mainCols = {"ID", "Title", "Author" ,"Year", "Publisher", "Subject"};
        InfoTable = new JTable(new DefaultTableModel(mainCols, 0));
        scrollPane = new JScrollPane(InfoTable);

        Object[] authCols = {"ID", "First Name", "Surname" };
        authInfoTable = new JTable(new DefaultTableModel(authCols, 0));
        authScrollPane = new JScrollPane(authInfoTable);

        Object[] pubCols = {"ID", "Publisher Name"};
        pubInfoTable = new JTable(new DefaultTableModel(pubCols, 0));
        pubScrollPane = new JScrollPane(pubInfoTable);

    }

    //Create main frame
    public static void main(String[] args) {
        JFrame frame = new JFrame("GUIBOI");
        frame.setContentPane(new MainGUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Refresh and load infotable (to be used after sort algorithm)
    public void bookTable() {
        DefaultTableModel bookModel = (DefaultTableModel) InfoTable.getModel();
        bookModel.setRowCount(0);
        books.forEach(book -> {
            Object[] bookRow = new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthors(),
                    book.getYearOfPublication(),
                    book.getPublisher(),
                    book.getSubject()
            };
            bookModel.addRow(bookRow);
        });
    }

    // Refresh and load AuthInfotable (to be used after sort algorithm)
    public void authorTable(){
        DefaultTableModel authModel = (DefaultTableModel)authInfoTable.getModel();
        authModel.setRowCount(0);
        authors.forEach(author -> {
            Object[] authRow = new Object[]{
                    author.getId(),
                    author.getFirstName(),
                    author.getLastName()
            };
            authModel.addRow(authRow);
        });
    }

    // Refresh and load PubInfotable (to be used after sort algorithm)
    public void publisherTable(){
        DefaultTableModel pubModel = (DefaultTableModel)pubInfoTable.getModel();
        pubModel.setRowCount(0);
        pubs.forEach(pub -> {
            Object[] pubRow = new Object[]{
                    pub.getId(),
                    pub.getPubName()
            };
            pubModel.addRow(pubRow);
        });
    }
}
