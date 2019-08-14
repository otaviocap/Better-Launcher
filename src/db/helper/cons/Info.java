package db.helper.cons;

public class Info {

    public static final String INSERT = "insert into "
            + "Infos (softwareId, info, yearReleased, picture, timesExecuted) "
            + "values (?,?,?,?,?)";
    
    public static final String UPDATE = "update Infos set "
            + "info=?, yearReleased=?, picture=?, timesExecuted=? where softwareId=?";
    
    public static final String REMOVE = "delete from Infos where softwareId=?";
    
    public static final String SEARCH = "select * from Infos";        
}
