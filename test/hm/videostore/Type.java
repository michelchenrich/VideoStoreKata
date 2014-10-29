package hm.videostore;

enum Type {
    REGULAR(1), CHILDRENS(2), NEW_RELEASE(3);
    public final int code;

    Type(int code) {
        this.code = code;
    }
}
