package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserType;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.ReadOnlyPersonnelDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonPersonnelDatabaseStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.NricUserPair;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyPersonnelDatabase> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    private NricUserPair nricUserPair = new NricUserPair(UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);

    public TestApp(Supplier<ReadOnlyPersonnelDatabase> initialDataSupplier,
                   Path saveFileLocation, NricUserPair nricUserPair) {
        this(initialDataSupplier, saveFileLocation);
        this.nricUserPair = nricUserPair;
    }

    public TestApp(Supplier<ReadOnlyPersonnelDatabase> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonPersonnelDatabaseStorage jsonPersonnelDatabaseStorage =
                    new JsonPersonnelDatabaseStorage(saveFileLocation);
            try {
                jsonPersonnelDatabaseStorage.savePersonnelDatabase(initialDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setPersonnelDatabaseFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the personnel database data stored inside the storage file.
     */
    public PersonnelDatabase readStoragePersonnelDatabase() {
        try {
            return new PersonnelDatabase(storage.readPersonnelDatabase().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the PersonnelDatabase format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getPersonnelDatabaseFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getPersonnelDatabase()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.startTest(primaryStage, nricUserPair);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
