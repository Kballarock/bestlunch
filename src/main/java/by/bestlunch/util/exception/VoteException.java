package by.bestlunch.util.exception;

public class VoteException extends ApplicationException {
    public static final String VOTING_TIME_EXCEPTION = "exception.voteTime.expired";

    public VoteException(String arg) {
        super(ErrorType.DATA_ERROR, VOTING_TIME_EXCEPTION, arg);
    }
}