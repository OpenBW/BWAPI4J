package bwem;

public final class UserData {

    private int data;

    public UserData() {
        this.data = 0;
    }

    public UserData(UserData userData) {
        this.data = userData.data;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

}
