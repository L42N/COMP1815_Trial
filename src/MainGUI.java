import KotlinAlgo.BubbleSort;
import KotlinAlgo.MergeSort;
import KotlinClass.Book;
import KotlinClass.DataPersistence;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    private JButton pubAddButton;
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
    private JButton AuthRefeshButton;
    private JTextField authLoad;
    private JTextField authSearchEntry;
    private JButton authAdd;
    private JButton authEdit;
    private JButton authDelete;
    private JScrollPane authScrollPane;
    private JPanel bookPanel;
    private JPanel authControlPanel;
    private JTable pubInfoTable;
    private JButton pubLoadButton;
    private JButton pubSearchButton;
    private JButton pubRefreshButton;
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


    public MainGUI() {
        // Load books by retrieving path from text field for use in getBooks function
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                books = DataPersistence.INSTANCE.getBooks(loadField.getText());
                refreshTable();

            }
        });

        // Load authors by retrieving path from text field for use in getAuthors function
        authLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var authorImport = DataPersistence.INSTANCE.getAuthors(authLoad.getText());
                DefaultTableModel authModel = (DefaultTableModel)authInfoTable.getModel();
                authorImport.forEach(author -> {
                    Object[] authRow = new Object[]{
                            author.getId(),
                            author.getFirstName(),
                            author.getLastName()
                    };
                    authModel.addRow(authRow);
                });
            }
        });

        // Load publishers by retrieving path from text field for use in getPublishers function
        pubLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var pubImport = DataPersistence.INSTANCE.getPublishers(pubLoad.getText());
                DefaultTableModel pubModel = (DefaultTableModel)pubInfoTable.getModel();
                pubImport.forEach(pub -> {
                    Object[] pubRow = new Object[]{
                            pub.getId(),
                            pub.getPubName()
                    };
                    pubModel.addRow(pubRow);
                });
            }
        });


        addNewEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Execute selected sort algorithm
        sortButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == sortButton){
                    // Create ArrayList for authors and books to sort
                    ArrayList<String> sortedAuthors = new ArrayList<>();
                    ArrayList<String> sortedBooks = new ArrayList<>();
                    // Scan CSV and append author and book names to corresponding ArrayLists
                    try {
                        Scanner sc = new Scanner(new File("resources/Book.csv"));
                        while (sc.hasNext()) {
                            String line = sc.nextLine();
                            String[] sortedItems = line.split(",");  // Set comma as delimiter pattern
                            sortedAuthors.add(sortedItems[2]);
                            sortedBooks.add(sortedItems[1]);

                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Display unsorted array list
//                    System.out.println("Before Authors");
//                    System.out.println(sortedAuthors);
//
//                    System.out.println("Before Books");
//                    System.out.println(sortedBooks);
//
                    // Execute algorithm & record timings
                    String algorithmType = sortCombobox.getSelectedItem().toString();
                    long startTime = System.nanoTime();
                   if (algorithmType.equals("Merge sort")){
                       MergeSort test = new MergeSort();
                       test.mergeSort(books);
                   } else if (algorithmType.equals("Bubble Sort")) {
                      // BubbleSort sort = new BubbleSort();
                  //     sort.bubbleSort(books);
                   }

                    long endTime = System.nanoTime();

                    // Calculate algorithm timings and display results
                    System.out.println("That took " + (endTime - startTime) + " nanoseconds");
                    String timings = String.valueOf((endTime - startTime) / 1000);
                    timeTextField.setText(timings + " milliseconds");

//                    MergeSort booktest = new MergeSort((sortedBooks));


//                    test.forEach();
//                    test.sortAuthors();
//                    test.showSortedAuthors();
//                    System.out.println("After Authors ");
//
//////                    booktest.forEach();
//                    test.sortBooks();
//                    test.showSortedBooks();
//                    System.out.println("After Books ");


                    refreshTable();

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

    // Refresh infotable (to be used after sort algorithm)
    public void refreshTable() {
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
}
