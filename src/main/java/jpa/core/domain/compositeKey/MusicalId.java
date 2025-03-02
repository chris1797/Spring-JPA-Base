package jpa.core.domain.compositeKey;


import java.io.Serializable;

public class MusicalId implements Serializable {

    private String title;
    private String actor;

    public MusicalId() {}

    public MusicalId(String title, String actor) {
        this.title = title;
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicalId musicalId)) return false;

        if (!title.equals(musicalId.title)) return false;
        return actor.equals(musicalId.actor);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + actor.hashCode();
        return result;
    }
}
