package db.daos;

import java.util.List;

public interface Dao<E> {

    public boolean add(E m);

    public boolean alter(E m);

    public boolean remove(E m);

    public boolean search(E m);

    public List<E> searchAll();
}
