package db.helper.cons;

public class Categories {

    public static final String INSERT = "insert into "
            + "Categories (idCategory, name, isForAGame) "
            + "values (?,?,?)";
    
    public static final String UPDATE = "update Categories set "
            + "name=?, isForAGame=? where idCategory=?";
    
    public static final String REMOVE = "delete from Categories where idCategory=?";
    
    public static final String SEARCH = "select * from Categories";        
}
