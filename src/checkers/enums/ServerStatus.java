package checkers.enums;

import java.io.Serializable;

/**
 * Created by Praca on 2017-06-27.
 */
public enum ServerStatus implements Serializable

{

    INACTIVE, LOOKINGFORPLAYERS, FILLTHEBOARD, SEND_DATA_TO_CLIENT, WAITING_FOR_WHITE_MOVE
}
