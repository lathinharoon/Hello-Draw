package Action;


public interface DrawAction {//the draw action interface provides a template for different action that the app provides.

    public void execute();//this function will provide the execution for that specific action.

    public void redo();//this function always calls the execute function to redo the action.

    public void undo();//this function which is called during the undo process. does the inverse of the undo function.

}

