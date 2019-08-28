package classes.java;

public class Category {
    
    private String Name;
    private Boolean isForAGame;
    private int id;
    
    public Category(String Name, Boolean b) {
        this.Name = Name;
        this.isForAGame = b;
    }
    
    public Category(){};

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Boolean getIsForAGame() {
        return isForAGame;
    }

    public void setIsForAGame(Boolean isForAGame) {
        this.isForAGame = isForAGame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.Name;
    }
    
}
