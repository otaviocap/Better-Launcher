package db.helper.cons;

public class Categories {

    public static final String INSERT = "insert into "
            + "Categories (categoryId, name, isForAGame) "
            + "values (?,?,?)";
    
    public static final String UPDATE = "update Categories set "
            + "name=?, isForAGame=? where categoryId=?";
    
    public static final String REMOVE = "delete from Categories where categoryId=?";
    
    public static final String SEARCH = "select * from Categories";   
    
    public static final String SEARCHGAMES = "select * from Categories where isForAGame=?";
}
