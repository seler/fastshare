package windows;

/**
 * Class representing single entry in main window's ComboBox
 */
public class ComboItem {
    /**
     * Entry's value
     */
    private String value;
    /**
     * Entry's label
     */
    private String label;

    /**
     * Constructor
     * @param value entry's value
     * @param label entry's label
     */
    public ComboItem(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * Returns entry's value
     * @return entry's value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns entry's label
     * @return entry's label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Overrided method - invoked when showing entry in ComboBox
     * @return entry's label
     */
    @Override
    public String toString() {
        return label;
    }
}