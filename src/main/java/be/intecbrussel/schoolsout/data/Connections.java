package be.intecbrussel.schoolsout.data;

public enum Connections {
    MySQL_Moktok_Remote("remote"),
    H2_Localhost("local");

    Connections(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public Connections setValue(String value) {
        this.value = value;
        return this;
    }
}
