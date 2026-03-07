package com.everest.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Pattern {
    BOTTOM(21),
    MID(22),
    TOP(23),

    NONE(-1);
    public final int associatedId;
    public static Pattern getById(int tagId){
        for(Pattern pattern : Pattern.values()){
            if(pattern.getAssociatedId() == tagId)
                return  pattern;
        }
        return MID;
    }
}
