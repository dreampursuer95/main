package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.AdminCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GeneralCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonnelDatabaseParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPersonnelDatabase;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final PersonnelDatabaseParser personnelDatabaseParser;
    private boolean addressBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        personnelDatabaseParser = new PersonnelDatabaseParser();

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getPersonnelDatabase().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText, UserType user) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;

        CommandResult commandResult;
        try {
            if(user == UserType.ADMIN) {
                AdminCommand command = personnelDatabaseParser.parseCommand(commandText);
                commandResult = command.executeAdmin(model, history);
            } else if (user == UserType.GENERAL) {
                GeneralCommand command = personnelDatabaseParser.parseCommand(commandText);
                commandResult = command.executeGeneral(model, history);
            } else {
                throw new CommandException("The person index provided is invalid");
            }
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.savePersonnelDatabase(model.getPersonnelDatabase());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPersonnelDatabase getAddressBook() {
        return model.getPersonnelDatabase();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    //@Override
    //public ObservableList<Person> getDutyForDates() {return model.getDutyForDates(); }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getPersonnelDatabaseFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }
}
