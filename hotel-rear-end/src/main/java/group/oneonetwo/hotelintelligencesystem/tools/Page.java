package group.oneonetwo.hotelintelligencesystem.tools;

/**
 * @author 文
 */
public class Page<A> {
    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;
    /**
     * 当前页
     */
    private int page = 1;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
