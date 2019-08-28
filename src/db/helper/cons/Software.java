package db.helper.cons;

public class Software {

    public static final String INSERT = "insert into "
            + "Softwares (name, isAGame, pathExec, args) "
            + "values (?,?,?,?)";
    
    public static final String UPDATE = "update Softwares set "
            + "name=?, isAGame=?, pathExec=?, args=? where softwareId=?";
    
    public static final String REMOVE = "delete from Softwares where softwareId=?";
    
    public static final String SEARCH = "select * from Softwares";        
}
