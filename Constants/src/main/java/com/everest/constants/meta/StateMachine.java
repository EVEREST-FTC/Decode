package com.everest.constants.meta;

import com.everest.CommandBased.compositions.SequentialCommandGroup;
import com.everest.CommandBased.definition.Command;

import org.example.core.implementations.AdjacencyListGraph;
import org.example.core.interfaces.Graph;

import lombok.Setter;

public class StateMachine {
    Graph<State, Command> graph;

    @Setter
    private State currentState;

    public StateMachine(State defaultState){
        graph = new AdjacencyListGraph<>(
                Graph.GraphType.DIRECTED, true
        );

        currentState = defaultState;
    }

    public void createRelation(State first, State second, Command command){
        graph.addRelation(first, second, command);
    }

    public Command setState(State newState){
        if(!graph.hasRelation(currentState, newState )) return currentState.getAssociatedCommand();
        Command command = new SequentialCommandGroup(
                graph.getRelation(currentState, newState),
                newState.getAssociatedCommand()
        );
        this.currentState = newState;
        return  command;

    }

}
