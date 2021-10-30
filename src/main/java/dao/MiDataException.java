package dao;

public class MiDataException extends RuntimeException{

private static final long serialVersionUID = 6813697248809460396L;
	public MiDataException(Exception e) {
		super(e);
	}
}