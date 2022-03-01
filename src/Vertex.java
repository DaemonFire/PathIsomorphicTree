/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

public class Vertex {
    private Object label;

    public Object getLabel(){
        return label;
    }

    public void setLabel(Object label){
        this.label = label;
    }

    public Vertex (Object label){
        this.label = label;
    }
}
