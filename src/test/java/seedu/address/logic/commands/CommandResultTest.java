package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.UiCommandInteraction;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", null)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", UiCommandInteraction.HELP)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", UiCommandInteraction.EXIT)));

        // different list value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", UiCommandInteraction.PEOPLE_LIST)));

        //different calendarCurrent value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", UiCommandInteraction.CALENDAR_CURRENT)));

        //different calendarNext value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", UiCommandInteraction.CALENDAR_NEXT)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", UiCommandInteraction.HELP)
                .hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", UiCommandInteraction.EXIT)
                .hashCode());

        //different list value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", UiCommandInteraction.PEOPLE_LIST)
                .hashCode());

        //different calendarCurrent value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback",
                UiCommandInteraction.CALENDAR_CURRENT)
                .hashCode());

        //different calendarNext value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback",
                UiCommandInteraction.CALENDAR_NEXT)
                .hashCode());
    }
}
