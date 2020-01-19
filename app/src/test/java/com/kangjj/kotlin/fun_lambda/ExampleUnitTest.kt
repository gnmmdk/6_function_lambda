package com.kangjj.kotlin.fun_lambda

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /**
     * todo 局部函数
     */
    @Test
    fun useLocalFun(){
        println(getMethodFunc("square",3))
        println(getMethodFunc("cube",3))
        println(getMethodFunc("",3))
    }

    fun getMethodFunc(type: String,nn: Int) : Int{
        fun square(n : Int) : Int {
            return n * n;
        }

        fun cube(n : Int) : Int{
            return n*n*n;
        }
        fun factorial(n : Int) : Int{
            var result = 1;
            for (index in 2..n) {
                result *= index;
            }
            return result;
        }

        when(type){
            "square" -> return square(nn);
            "cube" -> return cube(nn);
            else -> return factorial(nn);
        }
    }

    /**
     * todo 使用函数类型
     */
    @Test
    fun useFunType(){
        fun pow (base:Int,exponent:Int):Int{
            var result = 1;
            for (i in 1..exponent) {
                result *=base;
                println("result"+i+"="+result)
            }
            return result;
        }
        myFun = ::pow
        println(myFun(3,4))//3的4次方
        fun area(width:Int,height:Int):Int{
            return width*height
        }
        myFun = ::area
        println(myFun(3,4))
    }

    lateinit var myFun : (Int, Int) -> Int

    /**
     * 使用函数类型作为形参类型
     */
    @Test
    fun useFunType2Parameter(){
        val data = arrayOf(3, 4, 9, 5, 8)
        println("原数据${data.contentToString()}")
        println(map(data , ::square).contentToString())
        println(map(data , ::cube).contentToString())
        println(map(data , ::factorial).contentToString())
    }

    //定义函数类型的形参，其中fn是(Int)->Int 类型的形参
    fun map(data : Array<Int>,fn:(Int)->Int):Array<Int>{
        var result = Array<Int>(data.size,{0})
        println(result.contentToString())
        for (i in data.indices) {
            result[i]=fn(data[i])
        }
        return result
    }


    fun square(n : Int) : Int {
        return n * n;
    }

    fun cube(n : Int) : Int{
        return n*n*n;
    }
    fun factorial(n : Int) : Int{
        var result = 1;
        for (index in 2..n) {
            result *= index;
        }
        return result;
    }

    /**
     * 使用函数类型作为返回类型
     */
    @Test
    fun useFunType2Return(){
        var mathFunc = getMethodFunc2("cube")
        println(mathFunc(5))
        mathFunc = getMethodFunc2("square")
        println(mathFunc(5))
        mathFunc = getMethodFunc2("other")
        println(mathFunc(5))
    }

    /**
     * 定义函数，该函数的返回类型为（Int)->Int
     */
    fun getMethodFunc2(type: String) : (Int)->Int{
        fun square(n : Int) : Int {
            return n * n;
        }

        fun cube(n : Int) : Int{
            return n*n*n;
        }
        fun factorial(n : Int) : Int{
            var result = 1;
            for (index in 2..n) {
                result *= index;
            }
            return result;
        }

        when(type){
            "square" -> return ::square;
            "cube" -> return ::cube;
            else -> return ::factorial;
        }
    }

    /**
     * 使用Lambda表达式代替局部函数
     */
    @Test
    fun useLambda1(){
        var mathFunc = getMethodFunc3("cube")
        println(mathFunc(5))
        mathFunc = getMethodFunc3("square")
        println(mathFunc(5))
        mathFunc = getMethodFunc3("other")
        println(mathFunc(5))

    }

    /**
     * 1、Lambda 表达式总是被大括号括着
     * 2、定义Lambda表达式不需要fun关键字，无须指定函数名
     * 3、形参列表（如果有的话）在->之前生命，参数类型可以自动省略
     * 4、函数体（Lambda表达式执行体）放在->之后
     * 5、函数的最后一个表达式自动被作为Lambda表达式的返回值，无须使用return关键字
     */
    fun getMethodFunc3(type : String):(Int)->Int{
        when(type) {
            "square" -> return { n:Int->
                n*n
            }
            "cube" -> return{ n:Int->
                n*n*n
            }
            else -> return{ n:Int->
                var result = 1
                for (index in 2..n) {
                    result *=index
                }
                result
            }
        }
    }

    /**
     * Lambda表达式的脱离
     */
    @Test
    fun useLambda2(){
        //调用collectFn函数两次，将会向lambdaList中添加元素（每个元素都是Lambda表达式）
        collectFn ({ it*it })
        collectFn { it*it*it }
        println(lambdaList.size)
        for (index in lambdaList.indices) {
            println(lambdaList[index](index+10))
        }

    }
    var lambdaList = java.util.ArrayList<(Int)->Int>()
    fun collectFn(fn: (Int)->Int){
        lambdaList.add(fn)
    }

    /**
     * 调用Lambda表达式
     */
    @Test
    fun useLambda3(){
        var square = { n:Int->
            n*n
        }
        println(square(5))
        println(square(6))
        //定义一个Lambda表达式，并在它后面添加圆括号来调用该Lambda表达式
        var result = { base:Int,exponent:Int->
            var result = 1;
            for (i in 1..exponent) {
                result *= base
            }
            result
        }(4,3)
        println(result)
    }

    @Test
    fun useLambda4(){
        //由于程序定义了square变量的类型，因此Kotlin可以推断出Lambda表达式的形参类型，所以Lambda表达式可以省略形参类型
        var square:(Int)->Int = { n -> n*n }

        println(square(5))
        println(square(6))
        //此时Kotlin无法推断base、exponent两个形参的类型，因此必须为其制定类型
        var result = { base:Int,exponent:Int->
            var result = 1;
            for (i in 1..exponent) {
                result *= base
            }
            result
        }(4,3)
        println(result)
        //下面这种方式无法指定（4,3）
        var result2:(Int,Int)->Int = { base,exponent->
            var result = 1;
            for (i in 1..exponent) {
                result *= base
            }
            result
        }
        println(result2(4,3))

        var list = listOf("Java","Kotlin","Android","Go")
        //使用Lambda表达式定义去除条件：长度大于3的集合元素被去除
        //由于doWhite（）方法的形参是（T)->Boolean类型，因此调用该方法时可省略形参类型
        val rt = list.dropWhile { e -> e.length > 3 }
        println(rt)
    }

    /**
     * 如果只有一个参数，那么Kotlin允许省略Lambda表达式的形参名，而此时->也不需要了，Lambda表达式可通过it来代表形参
     */
    @Test
    fun useLambda5(){
        var square:(Int)->Int = {it*it}
        println(square(5))
        println(square(6))

        var list = listOf("Java","Kotlin","Android","Go")
        val rt = list.dropWhile ({ it.length>3 })
        println(rt)

    }

    /**
     * 调用Lambda 表达式的约定
     */
    @Test
    fun useLambda6(){
        var list = listOf("Java","Kotlin","Android","Go")
        list.dropWhile(){it.length>3}

        var map = mutableMapOf("疯狂Android讲义" to 56)
        list.associateTo(map){"疯狂${it}讲义" to it.length}
        println(map)            //{疯狂Android讲义=7, 疯狂Java讲义=4, 疯狂Kotlin讲义=6, 疯狂Go讲义=2}
        var rtx = list.reduce{ acc,e->acc+e }//JavaKotlinAndroidGo    reduce函数：第一个参数acc是累加的返回值，e是当前遍历列表中的值
        println(rtx)

        var list2 = listOf(5,3,4)
        val reduce = list2.reduce { acc, e -> acc * e } //reduce函数：第一个参数acc是相乘的返回值，e是当前遍历列表中的值 注意上方的Stirng类型是无法用acc * e
        println(reduce)
    }

    /**
     * 个数可变的参数和Lambda参数
     * 到底将可变参数的形参放在最后，还是将函数类型的形参放在最后？
     * Kotlin约定：如果调用函数时，最后一个参数是Lambda表达式，则可将Lambda表达式放在圆括号外面，这样就无须使用命名参数了。
     * 因此答案是：如果一个函数既包含个数可变的形参，也包含函数类型的形参，那么就应该将函数类型的形参放在最后
     */
    @Test
    fun useLambda7(){
        var list1 = test("Java","Kotlin","Android"){it.length}//[4, 6, 7]
        var list2 = test("Java","Kotlin","Android"){"疯狂${it}讲义"}//[疯狂Java讲义, 疯狂Kotlin讲义, 疯狂Android讲义]
        println(list1)
        println(list2)
    }

    fun<T> test(vararg names:String,transform:(String)->T):List<T>{
        var mutableList:MutableList<T> = mutableListOf()
        for (name in names) {
            mutableList.add(transform(name))
        }
        return mutableList.toList()
    }

    /**
     * 匿名函数的用法
     */
    @Test
    fun useLambda8(){
        var test = fun(x:Int,y:Int):Int{//将普通函数的函数名去掉就变成了匿名函数
            return x+y
        }
        println(test(2,4))
        // 与普通函数不同的是，如果系统可以推断匿名函数的形参类型，那么匿名函数允许省略形参类型
        var filterredList = listOf(3,5,20,100,-25).filter(
            fun(e/*:Int*/):Boolean{//  /*:Int*/ List的泛型，这里自动推断出Int类型，所以不需要：Int
                return Math.abs(e)>20
            }
        )
        println(filterredList)
        //Lambda的用法，与上方的一样
        var filterredList2 = listOf(3,5,20,100,-25).filter{ e -> Math.abs(e)>20}

        println(filterredList2)

        //定义匿名函数的函数体是单体表达式，可以省略声明函数的返回值类型
        var wawa = fun(x:Int,y:Int) = x+y
        println(wawa(2,4))
        var rt = listOf(3,5,20,100,-25).filter(
            //使用匿名函数作为filter（方法的参数）
            //匿名函数的函数体是单体表达式，允许省略生命函数的返回值类型
            fun(e1) = Math.abs(e1)>20
        )
        println(rt)
    }

    /**
     * 匿名函数和Lambda表达式的return
     * 匿名函数的本质依然是函数，因此匿名函数的return则用于返回该函数本身；
     * 而Lambda表达式的return用于返回它所在的函数，而不是Lambda表达式（Lambda返回的是最后一行，不需要return）
     */
    @Test
    fun useLambda9(){
        var list = listOf(3,5,20,100,-25)
        list.forEach(fun(n){
            println("元素依次为：${n}")
            /** 打印结果：
             * 元素依次为：3
                元素依次为：5
                元素依次为：20
                元素依次为：100
                元素依次为：-25
             */
            return
        })

        list.forEach({n->
            println("元素依次为：${n}")
            /** 打印结果：
             * 元素依次为：3
            元素依次为：5
            元素依次为：20
            元素依次为：100
            元素依次为：-25
             */
            return@forEach//限定返回语法
        })

        list.forEach({n->
            println("元素依次为：${n}")//打印结果：        元素依次为：3
            return
        })
    }

    /**
     * 捕获上下文中的变量和常量
     * 可以访问或修改其上下文（俗称“闭包）中的变量和常量，这个过程被称为捕获
     *
     * Lambda表达式或匿名函数都会持有一个其所捕获的变量副本，因此表面上看addElement()访问的是makeList()
     * 函数的List集合变量，但只要程序返回一个新的addElement()函数，addElement函数就会自己持有一个新的list
     * 副本。如下所示：
     */
    @Test
    fun captureVariables(){
        println("*******************add1返回的List******************")
        val add1 = makeList("孙悟空")
        println(add1())
        println(add1())
        println("*******************add2返回的List******************")
        val add2 = makeList("猪八戒")
        println(add2())
        println(add2())
        /**
            *******************add1返回的List******************
            [孙悟空]
            [孙悟空, 孙悟空]
            *******************add2返回的List******************
            [猪八戒]
            [猪八戒, 猪八戒]
         */
    }

    fun makeList(ele:String):()->List<String>{
        var list:MutableList<String> = mutableListOf()
        fun addElement():List<String>{
            list.add(ele)
            return list
        }
        return ::addElement
    }
}
