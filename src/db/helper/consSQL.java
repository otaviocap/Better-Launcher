package db.helper;

public class consSQL {

    public static final String INSERT = "insert into "
            + "aluno (matricula, nome, nota1, nota2, nota3, media) "
            + "values (?,?,?,?,?,?)";
    
    public static final String UPDATE = "update aluno set "
            + "nota1=?, nota2=?, nota3=?, media=? where matricula=?";
    
    public static final String REMOVE = "delete from aluno where matricula=?";
    
    public static final String SEARCH = "select * from aluno";        
}
