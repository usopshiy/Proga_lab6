package exceptions;

public class FileWrongPermissionException extends FileException {
    public FileWrongPermissionException(String msg){
        super(msg);
    }
}
