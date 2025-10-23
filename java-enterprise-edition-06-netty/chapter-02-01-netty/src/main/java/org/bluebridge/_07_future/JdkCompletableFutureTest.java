package org.bluebridge._07_future;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/15 13:46
 */
/**
 * CompletableFuture 是 Java8 引入的异步编程工具类，实现了 Future 和 CompletionStage 接口，提供了更强大的异步编程能力。
 * 核心特性
 *    1.异步执行
 *      - 支持异步执行任务而不阻塞主线程
 *      - 提供 supplyAsync 和 runAsync 等静态方法创建异步任务
 *    2.链式调用
 *      - 通过 thenApply、thenAccept、thenRun 等方法实现链式操作
 *      - 支持函数式编程风格
 *    3.组合操作
 *      - thenCombine：组合两个独立的 CompletableFuture
 *      - thenCompose：扁平化组合 CompletableFuture
 *      - allOf、anyOf：组合多个 CompletableFuture
 *    4.异常处理
 *      - exceptionally：处理异常并提供默认值
 *      - handle：统一处理成功和异常情况
 *    5.常用方法分类
 *      创建方法
 *         <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)：异步执行有返回值的任务（非线程池版）
 *         <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)：异步执行有返回值的任务（带线程池版）
 *
 *         CompletableFuture<Void> runAsync(Runnable runnable)：异步执行无返回值的任务（非线程池版）
 *         CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)：异步执行无返回值的任务（带线程池版）
 *      链式处理方法
 *         // 转换结果
 *         <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)：同步转换结果
 *         <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)：异步转换结果（非线程池版）
 *         <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)：异步转换结果（带线程池版）
 *         // 消费结果
 *         CompletableFuture<Void> thenAccept(Consumer<? super T> action)：同步消费结果
 *         CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action)：异步消费结果（非线程池版）
 *         CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor)：异步消费结果（带线程池版）
 *         // 执行后续操作
 *         CompletableFuture<Void> thenRun(Runnable action)：同步执行后续操作
 *         CompletableFuture<Void> thenRunAsync(Runnable action)：异步执行后续操作（非线程池版）
 *         CompletableFuture<Void> thenRunAsync(Runnable action, Executor executor)：异步执行后续操作（带线程池版）
 *      组合方法
 *         thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)：组合两个结果
 *         thenCompose(Function<? super T,? extends CompletionStage<U>> fn)：扁平化组合
 *      异常处理方法
 *         exceptionally(Function<Throwable, ? extends T> fn)：异常处理
 *         handle(BiFunction<? super T, Throwable, ? extends U> fn)：统一处理成功和异常情况
 */
@Slf4j
public class JdkCompletableFutureTest {

    /**
     * 测试 CompletableFuture
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableFutureBasic() throws ExecutionException, InterruptedException {
        // 创建 CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();
        // 异步完成任务
        CompletableFuture.runAsync(() -> {
            // 执行异步任务
            future.complete("Hello World");
        });
        // 获取结果
        String result = future.get(); // 阻塞等待
        log.info("异步任务执行结果: {}", result);
    }

    /**
     * 测试 CompletableFuture 链式调用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableFutureChainInvoke() {
        CompletableFuture<String> future = CompletableFuture
            .supplyAsync(() -> "Hello")
            .thenApply(result -> result + " World")
            .thenApply(String::toUpperCase);
        String finalResult = future.join(); // 输出: HELLO WORLD
        log.info("链式调用结果: {}", finalResult);
    }

    /**
     * 测试 CompletableFuture 异常处理
     */
    @Test
    public void testCompletableFutureExceptionDeal() {
        CompletableFuture<String> future = CompletableFuture
            .supplyAsync(() -> {
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Error occurred");
                }
                return "Success";
            })
            .exceptionally(throwable -> {
                log.info("捕获异常: {}", throwable.getMessage());
                return "Default Value";
            });
        String result = future.join();
        log.info("异常处理结果: {}", result);
     }

    /**
     * 测试 CompletableFuture supplyAsync() 和 runAsync()
     *    runAsync(): 无返回值，执行任务完成后，不返回任何结果
     *    supplyAsync(): 有返回值，执行任务完成后，返回一个结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableFutureSupplyAsyncAndRunAsync() {
        CompletableFuture<String> future = CompletableFuture
                //.runAsync(() -> log.info("runAsync"))
                .supplyAsync(() -> "supplyAsync")
                .thenApply(result -> result + " World")
                .thenApply(String::toUpperCase);
        String finalResult = future.join(); // 输出: HELLO WORLD
        log.info("测试supplyAsync()和runAsync()结果: {}", finalResult);
    }
}
