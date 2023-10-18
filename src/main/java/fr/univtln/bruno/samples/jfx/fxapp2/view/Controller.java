package fr.univtln.bruno.samples.jfx.fxapp2.view;

import fr.univtln.bruno.samples.jfx.fxapp2.model.Group;
import fr.univtln.bruno.samples.jfx.fxapp2.model.Page;
import fr.univtln.bruno.samples.jfx.fxapp2.model.Person;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class Controller {
    //A DAO to access the model
    private final Group.DAO groupDAO = Group.DAO.newInstance();

    //A programmatic widget
    private final TableView<Person> table = createTable();
    private final int pageSize = 10;
    //FXML widget bindings
    @FXML
    Pagination paginator;
    @FXML
    private ListView<Person> searchList;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox dataContainer;

    @FXML
    private void initialize() {
        //The pagination to browse data
        paginator = new Pagination();
        paginator.setMaxPageIndicatorCount(10);
        //The method to build a page on change
        paginator.setPageFactory(this::createPage);

        //The search field and button action
        searchButton.setOnAction(event -> searchInNames());
        searchList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Person> call(ListView<Person> param) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Person person, boolean empty) {
                        super.updateItem(person, empty);
                        if (empty || person == null) {
                            setText(null);
                        } else {
                            setText(person.getName());
                        }
                    }
                };
            }
        });
        dataContainer.getChildren().add(paginator);
    }

    //Programmatic creation a of tableview
    @SuppressWarnings("unchecked")
    private TableView<Person> createTable() {
        TableView<Person> tableView = new TableView<>();
        TableColumn<Person, Integer> id = new TableColumn<>("UUID");
        id.setSortable(false);
        id.setCellValueFactory(new PropertyValueFactory<>("uuid"));
        TableColumn<Person, String> name = new TableColumn<>("NAME");
        name.setSortable(false);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person, String> address = new TableColumn<>("ADDRESS");
        address.setSortable(false);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.getColumns().addAll(id, name, address);
        return tableView;
    }

    private Node createPage(int pageNumber) {
        Page<Person> page = groupDAO.findAll(pageSize, pageNumber);
        table.setItems(FXCollections.observableArrayList(page.content()));
        paginator.setPageCount((int) (page.dataSize() / page.pageSize()));
        return new BorderPane(table);
    }

    private void searchInNames() {
        String searchText = searchField.getText();
        Task<List<Person>> task = new Task<>() {
            @Override
            protected List<Person> call() {
                updateMessage("Loading data");
                return Group.DAO.newInstance().search(searchText).content();
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        task.setOnSucceeded(event -> {
            searchList.setItems(FXCollections.observableList(task.getValue()));
        });

    }
}
