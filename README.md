# Test_AsyncTask
##   AsyncTask 运用的场景就是我们需要进行一些耗时的操作，耗时操作完成后更新主线程，或者在操作过程中对主线程的UI进行更新。
> AsycnTask<Params,Progress,Result>是一个抽象类，有三个泛型参数，分别对应启动任务执行的输入参数类型(初始化参数)，后台任务完成的进度值类型，执行结果类型。

 1. 我们需要重载四个方法，onPreExecute()执行初始化工作；doInBackground(Params...)用于耗时操作；可以调用*publishProgress()*更新进度；onProgressUpdate()用于进度更新；onPostExecute()用于处理结果
 2. 缺陷：底层是一个线程池。AsyncTask中维护着一个长度为128的线程池，同时可以执行5个工作线程，还有一个缓冲队列，当线程池中已有128个线程，缓冲队列已满时，如果此时向线程提交任务，将会抛出RejectedExecutionException。

在3.0以前，最大支持128个线程的并发，10个任务的等待。在3.0以后，无论有多少任务，都会在其内部单线程执行


解决：由一个控制线程来处理AsyncTask的调用判断线程池是否满了，如果满了则线程睡眠否则请求AsyncTask继续处理。


## AsyncTask和Handler的区别

 1  AsyncTask实现的原理,和适用的优缺点

AsyncTask,是android提供的轻量级的异步类,可以直接继承AsyncTask,在类中实现异步操作,并提供接口反馈当前异步执行的程度(可以通过接口实现UI进度更新),最后反馈执行的结果给UI主线程.

使用的优点:

l  简单,快捷

l  过程可控

使用的缺点:

l  在使用多个异步操作和并需要进行Ui变更时,就变得复杂起来.

2 Handler异步实现的原理和适用的优缺点

在Handler 异步实现时,涉及到 Handler, Looper, Message,Thread四个对象，实现异步的流程是主线程启动Thread（子线程）àthread(子线程)运行并生成Message- àLooper获取Message并传递给HandleràHandler逐个获取Looper中的Message，并进行UI变更。

使用的优点：

l  结构清晰，功能定义明确

l  对于多个后台任务时，简单，清晰
> AsyncTask相比Thread加Handler更为可靠，更易于维护，但AsyncTask缺点也是有的比如一旦线程开启即 dobackground方法执行后无法给线程发送消息，仅能通过预先设置好的标记来控制逻辑，当然可以通过线程的挂起等待标志位的改变来通讯，对于某些应用Thread和Handler以及Looper可能更灵活

#注意 ： 只有在doinbackground方法里是子线程，其他方法都在主线程。
#回调顺序： onPreExecute--->doInbackground--->onProgressUpdate(需要在doinbackground中调用publicprogress（）)---->onPostExecute
