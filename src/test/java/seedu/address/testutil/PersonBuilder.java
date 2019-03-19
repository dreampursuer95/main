package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Nric;
import seedu.address.model.person.Company;
import seedu.address.model.person.Section;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NRIC = "S5234567A";
    public static final String DEFAULT_COMPANY = "Bravo";
    public static final String DEFAULT_SECTION = "1SIR";
    public static final String DEFAULT_RANK = "CPL";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";

    private Nric nric;
    private Company company;
    private Section section;
    private Rank rank;
    private Name name;
    private Phone phone;
    private Set<Tag> tags;

    public PersonBuilder() {
        nric = new Nric(DEFAULT_NRIC);
        company = new Company(DEFAULT_COMPANY);
        section = new Section(DEFAULT_SECTION);
        rank = new Rank(DEFAULT_RANK);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        nric = personToCopy.getNric();
        company = personToCopy.getCompany();
        section = personToCopy.getSection();
        rank = personToCopy.getRank();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    public PersonBuilder withSection(String section) {
        this.section = new Section(section);
        return this;
    }

    public PersonBuilder withRank(String rank) {
        this.rank = new Rank(rank);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Person build() {
        return new Person(nric, company, section, rank, name, phone, tags);
    }

}
