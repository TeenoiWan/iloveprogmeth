package logic.superClass;

public abstract class Tool {

    protected String target;

    public Tool(String target){
        this.target = target;
    }

    public abstract void execute();
}
