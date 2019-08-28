package db.helper.cons;

public class CategorySoftware {
    
    public static final String INSERT = "insert into "
            + "`categories-softwares` (categoryId, softwareId) "
            + "values (?,?)";
    
    public static final String REMOVE = "delete from categories-softwares where categoryId=? and softwareId=?";
    
    public static final String REMOVEALLBYSOFTWARE = "delete from categories-softwares where softwareId=?";
    
    public static final String REMOVEALLBYCATEGORY = "delete from categories-softwares where categoryId=?";
    
    public static final String SEARCH = "select * from categories-softwares";
    
    public static final String SEARCHBYCATEGORY = "select * from categories-softwares where categoryId=?";
    
    public static final String SEARCHBYSOFTWARE = "select * from categories-softwares where softwareId=?";
    
    public static final String SEARCHBYSOFTWAREGETCATEGORIES = "select * from categories-softwares join categories using(categoryId) where softwareId=?";

    
}