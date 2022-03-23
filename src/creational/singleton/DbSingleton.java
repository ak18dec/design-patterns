package creational.singleton;

public class DbSingleton {

    //We're creating an instance here whether we use this: hence called EAGERLY LOADED
//    private static DbSingleton instance = new DbSingleton();
//
//    private DbSingleton(){}
//
//    public static DbSingleton getInstance() {
//        return instance;
//    }

    //Lazily Loaded
//    private static DbSingleton instance = null;
//
//    private DbSingleton(){}
//
//    public static DbSingleton getInstance(){
//        if(instance == null){
//            instance = new DbSingleton();
//        }
//        return instance;
//    }

    /**
     * Lazily Loaded and Thread Safe
     * This volatile keyword helps us ensure that the instance will remain singleton
     * through any of the changes inside of the JVM.
     *
     */
    private static volatile DbSingleton instance = null;

    //Also need to take care that nobody could use Reflection on our code.
    private DbSingleton() {
        if(instance != null){
            throw new RuntimeException("Use getInstance() method to create");
        }
    }

    /**
     * Used a Double-checked Locking Mechanism and a synchronized check.
     * Common Mistake: Some people will make this whole method synchronized and the problem with that
     * it is a performance hit.
     * If we make this whole method synchronized every time we ask for an instance of it, we're going to
     * actually synchronize that class and slow it completely down
     *
     */
    /**
      * Rather than synchronize on whole method, we checked to see if the instance is equal to null,
      * then we synchronized on it. And the reason for doing this is it may be null, but if 2 threads
      * are trying to go at it, once we've synchronized and checked for null again, if another class has
      * lock on that, it will then block our code and create the instance and return that synchronized lock
      * to where our code would now go back in and say, if this instance is null it would go, oh no I am already
      * created and returned out of this.
      * You can see, how that double-checked with volatile instance inside there, is handling that functionality
      * inside our class.
     */
    public static DbSingleton getInstance(){
        if(instance == null){
            synchronized (DbSingleton.class){
                // check for null one more time
                // Idea is that it's only going to actually happen if we're creating this one time.
                // Means it should only run if we're actually creating this for the very first time.
                if (instance == null){
                    instance = new DbSingleton();
                }
            }
        }
        return instance;
    }
}
