package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.stream.Stream;

import seedu.address.commons.core.UserType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RewardCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RewardCommand object
 */
public class RewardCommandParser implements Parser<RewardCommand> {

    private static final Prefix PREFIX_INDEXES = new Prefix("i/");
    private static final Prefix PREFIX_POINTS = new Prefix("p/");

    private static final String MESSAGE_POINTS_OUT_OF_RANGE = "Input points out of range";

    @Override
    public RewardCommand parse(String args, UserType userType, String userName) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEXES, PREFIX_POINTS);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEXES, PREFIX_POINTS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RewardCommand.MESSAGE_USAGE));
        }

        try {
            int points = Integer.parseInt(argMultimap.getValue(PREFIX_POINTS).get());

            if (points <= 0) {
                throw new InvalidParameterException();
            }

            HashSet<Index> indexes = new HashSet<>();
            for (String s : argMultimap.getValue(PREFIX_INDEXES).get().split(" ")) {
                indexes.add(ParserUtil.parseIndex(s));
            }
            return new RewardCommand(points, indexes);
        }
        catch (InvalidParameterException pe) {
           throw new ParseException(MESSAGE_POINTS_OUT_OF_RANGE);
        }
        catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RewardCommand.MESSAGE_USAGE), pe);

        }


    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
