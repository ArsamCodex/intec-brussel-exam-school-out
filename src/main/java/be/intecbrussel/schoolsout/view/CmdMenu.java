package be.intecbrussel.schoolsout.view;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class CmdMenu {

    private List<CmdMenuItem> items;

    public void print() {
        CmdTable menuTable = new CmdTable();
        menuTable.setShowVerticalLines(true);
        items.forEach(item -> menuTable.addRow(String.valueOf(item.getId()), item.getHeader(), item.getContent()));
        menuTable.print();
    }

    public CmdMenuItem select(final int option) {
        final Optional<CmdMenuItem> menuItem = items.stream().filter(item -> item.getId() == option).findFirst();
        return menuItem.orElse(null);
    }
}
