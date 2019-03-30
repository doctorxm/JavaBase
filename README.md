# JavaBase
Java基础知识整理
## Java return 和finally的执行顺序
### 情况一
try 语句中有return; finally中没有，有语句块，执行顺序为 try return finally return

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

输出结果为：
 
	try block

	finally block!

	b>25,b=101

	101
  
##### 测试用例2

	public static String test11(){
        try{
            System.out.println("try block"); //第一步
            return test12() ;
        } finally {
            System.out.println("finally block!");//第三步
        }
    }
    public static String test12(){
        System.out.println("return statement")//第二步
        return "after return";//第四步
    }
输出结果：

	try block
	return statement
	finally block!
	after return

### 情况二
finally中有return,try中也有return,finally会覆盖try块中的return语句

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
输出结果为：

	try block
	finally block!
	b>25,b=101
	400（覆盖）
### 情况三

	//finally中没有return语句，原来的返回值可能因为finally里的修改而改变也可能不变。 
    (1)不变return值
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
                System.out.println("b>25,b=" + b);
                b=99;
            }
        }
        return 555;
    }
输出结果为：

	try block
	finally block!
	b>25,b=101
	101
(2)改变return值

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
            map=null;//失效
        }
     return map;
    }
结果为：
	
>FINALLY
##

>总结:   
   (1)如果try中有return语句，finally中没有，那么finallY语句块中的语句执行顺序在return语句执行后返回前执行。  
   (2)如果try和finally中都有return语句，那么最后返回结果时finally中的return结果会覆盖try中的结果。  
   (3)如果finally中语句块对return值做了修改，最后可能会影响返回值也可能不影响，主要涉及到Java引用传递和值传递的问题。  
   (4)try块中的return语句在异常的情况下不会执行。  
   (5)发生异常时，catch中的return执行情况和未发生异常try中return的执行情况一致。
#### finally什么时候不执行？
>(1) try语句没有被执行，在try语句之前就返回了。  
 (2)在try块中调用System.exit(0);这个语句是终止JVM虚拟机的。 
	 


