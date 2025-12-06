package com.everest.constants.meta;

import com.everest.CommandBased.definition.Command;

public interface State {
    Command getAssociatedCommand();
}
