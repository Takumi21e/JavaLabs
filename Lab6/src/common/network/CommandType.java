package common.network;

import java.io.Serializable;

public enum CommandType implements Serializable {
    HELP,
    INFO,
    SHOW,
    ADD,
    UPDATE,
    REMOVE_BY_ID,
    CLEAR,
    EXECUTE_SCRIPT,
    EXIT,
    ADD_IF_MAX,
    SHUFFLE,
    REMOVE_GREATER,
    FILTER_BY_OWNER,
    FILTER_LESS_THAN_OWNER,
    FILTER_GREATER_THAN_PART_NUMBER
}