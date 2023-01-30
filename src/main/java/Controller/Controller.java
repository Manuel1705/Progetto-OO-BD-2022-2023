package Controller;

public class Controller
{
    private Controller(){
    }
    private static Controller instance=null;
    public static Controller getInstance(){
        if(instance==null)
            instance=new Controller();

        return instance;
    }

}
