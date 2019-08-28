package db.helper.cons;

public class Info {

    public static final String INSERT = "insert into "
            + "Infos (softwareId, description, releaseYear, imgUrl) "
            + "values (?,?,?,?)";
    
    public static final String UPDATE = "update Infos set "
            + "description=?, releaseYear=?, imgUrl=? where softwareId=?";
    
    public static final String REMOVE = "delete from Infos where softwareId=?";
    
    public static final String SEARCH = "select * from Infos";        
}
