package com.umutavci;

import java.util.function.Consumer;

public class WebsiteChangeWatcher{
    private String target;
    private String path;
    public Consumer<String> onChange;
    public boolean running;
    public WebsiteChangeWatcher(String target, String path, Consumer<String> onChange) {
        this.target = target;
        this.path = path;
        this.onChange = onChange;
    }
    public void hasChanged(){
        new Thread ( () -> {
            while(running){
                try {
                    String content = retrieveWebsiteContent();
                    Thread.sleep(30000);
                    String newContent = retrieveWebsiteContent();
                    if(!content.equals(newContent)){
                        onChange.accept(newContent);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        ).start();
    }
    public void cancel(){
        running = false;
    }

    /**
     * Reads the content of a website and returns its content as html.
     * @return The content of a website as String.
     */
    private String retrieveWebsiteContent() {
        return "";
    }
    public static void main(String[] args) {
        Consumer<String> cons = m -> System.out.println(m);

        try {
            WebsiteChangeWatcher wcw = new WebsiteChangeWatcher("zeit.de", "campus", cons);
            wcw.hasChanged();
            Thread.sleep(120000);
            wcw.cancel();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
