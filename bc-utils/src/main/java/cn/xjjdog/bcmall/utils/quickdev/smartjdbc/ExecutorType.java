package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

public enum ExecutorType {
    SQL("sql"),
    SPRING_BEAN("spring"),
    COMPOSITE("composite");

    private String key;

    ExecutorType(String key) {
        this.key = key;
    }

}
