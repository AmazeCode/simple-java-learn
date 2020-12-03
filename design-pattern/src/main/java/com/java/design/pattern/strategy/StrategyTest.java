package com.java.design.pattern.strategy;

/*
    策略模式定义抽象公共算法
    使用抽象类最大的好处是能够继承，如果需要多实现的时候可以使用接口，
    使用抽象类的最大的好处是：策略和模板方法可以一起使用
 */
abstract class Strategy {

    abstract void algorithmInterface();
}

/**
 * 初级会员针对A算法
 */
class StrategyA extends Strategy{
    @Override
    void algorithmInterface() {
        System.out.println("初级会员针对A算法");
    }
}

/**
 * 初级会员针对B算法
 */
class StrategyB extends Strategy{
    @Override
    void algorithmInterface() {
        System.out.println("中级会员针对B算法");
    }
}

/**
 * 初级会员针对C算法
 */
class StrategyC extends Strategy{
    @Override
    void algorithmInterface() {
        System.out.println("高级会员针对C算法");
    }
}

/*
    定义上下文用于维护算法
 */
class Context{
    Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    /*
        这样包一层的作用可以隐藏具体算法
     */
    void algorithmInterface() {
        strategy.algorithmInterface();
    }
}

public class StrategyTest {

    public static void main(String[] args) {
        //Context只需要一个，最好做成单例的
        Context context = null;
        StrategyA strategyA = new StrategyA();
        context = new Context(strategyA);
        context.algorithmInterface();

        StrategyB strategyB = new StrategyB();
        context = new Context(strategyB);
        context.algorithmInterface();

        StrategyC strategyC = new StrategyC();
        context = new Context(strategyC);
        context.algorithmInterface();
    }
}


