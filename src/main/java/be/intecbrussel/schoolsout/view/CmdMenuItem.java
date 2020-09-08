package be.intecbrussel.schoolsout.view;

public class CmdMenuItem {
    private Integer id;
    private String header;
    private String content;
    private String description;
    private CmdQuery query;

    CmdMenuItem(Integer id, String header, String content, String description, CmdQuery query) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.description = description;
        this.query = query;
    }

    public static CmdMenuItemBuilder builder() {
        return new CmdMenuItemBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getHeader() {
        return this.header;
    }

    public String getContent() {
        return this.content;
    }

    public String getDescription() {
        return this.description;
    }

    public CmdQuery getQuery() {
        return this.query;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuery(CmdQuery query) {
        this.query = query;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CmdMenuItem)) return false;
        final CmdMenuItem other = (CmdMenuItem) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$header = this.getHeader();
        final Object other$header = other.getHeader();
        if (this$header == null ? other$header != null : !this$header.equals(other$header)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$query = this.getQuery();
        final Object other$query = other.getQuery();
        if (this$query == null ? other$query != null : !this$query.equals(other$query)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CmdMenuItem;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $header = this.getHeader();
        result = result * PRIME + ($header == null ? 43 : $header.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $query = this.getQuery();
        result = result * PRIME + ($query == null ? 43 : $query.hashCode());
        return result;
    }

    public String toString() {
        return "CmdMenuItem(id=" + this.getId() + ", header=" + this.getHeader() + ", content=" + this.getContent() + ", description=" + this.getDescription() + ", query=" + this.getQuery() + ")";
    }

    public static class CmdMenuItemBuilder {
        private Integer id;
        private String header;
        private String content;
        private String description;
        private CmdQuery query;

        CmdMenuItemBuilder() {
        }

        public CmdMenuItem.CmdMenuItemBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CmdMenuItem.CmdMenuItemBuilder header(String header) {
            this.header = header;
            return this;
        }

        public CmdMenuItem.CmdMenuItemBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CmdMenuItem.CmdMenuItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CmdMenuItem.CmdMenuItemBuilder query(CmdQuery query) {
            this.query = query;
            return this;
        }

        public CmdMenuItem build() {
            return new CmdMenuItem(id, header, content, description, query);
        }

        public String toString() {
            return "CmdMenuItem.CmdMenuItemBuilder(id=" + this.id + ", header=" + this.header + ", content=" + this.content + ", description=" + this.description + ", query=" + this.query + ")";
        }
    }
}
