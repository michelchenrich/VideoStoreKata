package hm.videostore.data;

public class Movie extends Entity {
    public String name;
    public Type type;

    public static enum Type {
        REGULAR, CHILDRENS, NEW_RELEASE
    }
}
