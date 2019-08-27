package classes.java;

import java.util.List;
import java.util.Objects;

public class App {
    
    private String imgUrl;
    private String name;
    private String pathExec;
    private int releaseYear;
    private String description;
    private boolean isGame;
    private List<String> categories;
    private String args;
    private int timesExecuted = 0;
    private int id; // The id will be generated after being added to the database;

    public App(String name, String pathExec, int releaseYear, String description, boolean isGame, List<String> categories, String args, String imageUrl) {
        this.name = name;
        this.pathExec = pathExec;
        this.releaseYear = releaseYear;
        this.description = description;
        this.isGame = isGame;
        this.categories = categories;
        this.args = args;
        this.imgUrl = imageUrl;
    }
    
     public App(String name, String pathExec, int releaseYear, String description, boolean isGame, List<String> categories, String args) {
        this.imgUrl = "";
        this.name = name;
        this.pathExec = pathExec;
        this.releaseYear = releaseYear;
        this.description = description;
        this.isGame = isGame;
        this.categories = categories;
        this.args = args;
    }
    
    
    public App() {
    }
    
    
    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getPathExec() {
        return pathExec;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public boolean isGame() {
        return isGame;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getArgs() {
        return args;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPathExec(String pathExec) {
        this.pathExec = pathExec;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsGame(boolean isGame) {
        this.isGame = isGame;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public int getTimesExecuted() {
        return timesExecuted;
    }

    public void setTimesExecuted(int timesExecuted) {
        this.timesExecuted = timesExecuted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "App{" + "imgUrl=" + imgUrl + ", name=" + name + ", pathExec=" + pathExec + ", releaseYear=" + releaseYear + ", description=" + description + ", isGame=" + isGame + ", categories=" + categories + ", args=" + args + ", timesExecuted=" + timesExecuted + ", id=" + id + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final App other = (App) obj;
        if (this.releaseYear != other.releaseYear) {
            return false;
        }
        if (this.isGame != other.isGame) {
            return false;
        }
        if (this.timesExecuted != other.timesExecuted) {
            return false;
        }
        if (!Objects.equals(this.imgUrl, other.imgUrl)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.pathExec, other.pathExec)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.args, other.args)) {
            return false;
        }
        if (!Objects.equals(this.categories, other.categories)) {
            return false;
        }
        return true;
    }
    
    
    
}
