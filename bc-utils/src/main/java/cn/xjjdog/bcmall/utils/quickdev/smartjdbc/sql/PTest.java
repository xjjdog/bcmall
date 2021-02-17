package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

/**
 * @author xjjdog
 */
public class PTest{
    public static void test() {
       P.p("select * from USERS\n" +
                "where 1=1 and #{id>:smallId\n} " +
                "#{\n" +
                " and FIRST_NAME like concat('中',:firstName,'%') }\n");

        System.out.println(P.p(
                "select\n" +
                        " * from project where \n" +
                        "/*\n" +
                        "此处\n" +
                        "为多行注释内容\n" +
                        "*/#{id=:id} \n" +
                        "#{and level>:level} #{and  a>:a} #{and  b>:b} #{\n" +
                        "and d > (select count(1) from project where id > :subID) } #{or c>:c} "
                ));
    }
}
