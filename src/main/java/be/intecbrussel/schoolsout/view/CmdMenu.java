package be.intecbrussel.schoolsout.view;

import java.util.List;
import java.util.Optional;

public class CmdMenu {

    private List<CmdMenuItem> items;

    public CmdMenu() {
    }

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

    public List<CmdMenuItem> getItems() {
        return this.items;
    }

    public void setItems(List<CmdMenuItem> items) {
        this.items = items;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CmdMenu)) return false;
        final CmdMenu other = (CmdMenu) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$items = this.getItems();
        final Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CmdMenu;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        return result;
    }

    public String toString() {
        return "CmdMenu(items=" + this.getItems() + ")";
    }
}
