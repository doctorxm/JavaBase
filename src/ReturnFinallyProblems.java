import java.util.HashMap;
import java.util.Map;

/**
 * @Author 厉害的小马fighting!
 * @Date 2019/3/30
 * @Description: PACKAGE_NAME
 */
public class ReturnFinallyProblems {
    public static void main(String[] args) {
        System.out.println(test32().get("KEY").toString());
    }
    //情况一：try 语句中有return finally中没有，有语句块，执行顺序为 try return finally return
    public static int test1(){
        int b=20;
        try{
            System.out.println("try block");//第一步
            return b+=81;
        }catch (Exception e){
            System.out.println("catch block!");
        }finally {
            System.out.println("finally block!");
            if(b>25) {
                System.out.println("b>25,b=" + b);
            }
        }
        return b+2;
    }
    public static String test11(){
        try{
            System.out.println("try block");//第一步
            return test12() ;
        } finally {
            System.out.println("finally block!");
        }
    }
    public static String test12(){
        System.out.println("return statement");
        return "after return";
    }
    //情况二，finally中的return会覆盖try中的return(最后执行结果)
    public static int test2(){
        int b=20;
        try{
            System.out.println("try block");//第一步
            return b+=81;
        }catch (Exception e){
            System.out.println("catch block!");
        }finally {
            System.out.println("finally block!");
            if(b>25) {
                System.out.println("b>25,b=" + b);
            }
            return 400;
        }
        //return b+2;不可达；
    }
    //情况三：finally中没有return语句，原来的返回值可能因为finally里的修改而改变也可能不变。
    public static int test3(){
        int b=20;
        try{
            System.out.println("try block");//第一步
            return b+=81;//b=101
        }catch (Exception e){
            System.out.println("catch block!");
        }finally {
            System.out.println("finally block!");
            if(b>25) {
                System.out.println("b="+b);
                b=99;
                System.out.println("b>25,b=" + b);
            }
        }
        return 555;
    }
    public static Map<String,String> test32(){
        Map<String,String> map=new HashMap<>();
        map.put("KEY","INIT");
        try{
            map.put("KEY","TRY");
            return map;
        }catch (Exception e){
            map.put("KEY","CATCH");
        }
        finally{
            map.put("KEY","FINALLY");
            map=null;
        }
     return map;
    }
}
