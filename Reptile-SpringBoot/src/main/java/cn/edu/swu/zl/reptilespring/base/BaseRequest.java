package cn.edu.swu.zl.reptilespring.base;

public class BaseRequest<T> {

    private String checksum;
    private T data;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
