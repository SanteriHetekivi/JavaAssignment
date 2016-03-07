package com.hetekivi;

/**
 * Created by Santeri Hetekivi on 3/5/16.
 */

/**
 * Runner class to run Thread classes at timed intervals
 */
public class TimedRunner extends ThreadRoot {

    /**
     * Time to wait between starts in seconds
     * and public setter and getter.
     */
    private int time = 0;
    public int Time(){ return this.time; }
    public void Time(int seconds){ this.time = seconds; }

    /**
     * ThreadRoot based class to be started by interval
     * and public setter and getter.
     */
    private ThreadRoot runnable = null;
    public ThreadRoot Runnable(){ return this.runnable; }
    public void Runnable(ThreadRoot runnable){ this.runnable = runnable; }

    /**
     * Default Constructor for the runner
     */
    public TimedRunner()
    {

    }

    /**
      * Overload Constructor
      * @param seconds Interval time in seconds.
     */
    public TimedRunner(int seconds)
    {
        this.Time(seconds);
    }

    /**
     * Initializer for setting CLASS and FILE.
     */
    @Override
    protected void init() {
        super.init();
        this.CLASS = "TimedRunner";
        this.FILE = "TimedRunner.java";
    }

    /**
     * Function to start the Thread.
     */
    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * Threads run function.
     */
    @Override
    public void run()
    {
        super.run();
        final String FUNCTION = "run()";
        // Run while time is bigger than 0 and there are no errors.
        while(this.Time() > 0 && this.Errors.HasErrors() == false &&
                (this.Runnable() == null || this.Runnable().Errors.HasErrors() == false))
        {
            // If runnable class is set
            if(this.Runnable() != null)
            {
                // If given runnable thread is terminated and set to be finished.
                if(this.Runnable().getState() == State.TERMINATED && this.Runnable().Finished())
                {
                    // Make new runnable by cloning the old one.
                    this.Runnable(this.Runnable().Clone());
                }
                // If runnable thread is NEW. Start the thread.
                if(this.Runnable().getState() == State.NEW) this.Runnable().start();
            }
            //Trying to
            try {
                sleep(this.Time()*1000);    // Sleep set amount of seconds
            } catch (InterruptedException e) {
                // Setting error if exception is thrown.
                this.addError(FUNCTION, e.getLocalizedMessage());
            }
        }
        this.END(); // Report about the end of the thread.
    }
}
