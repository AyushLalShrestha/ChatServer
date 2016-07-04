package com.leapfrog.ChatServer.entity;

public class oneToOne {

    public Client blockSource;
    public Client blockDestination;

    public oneToOne() {
    }

    public oneToOne(Client blockSource, Client blockDestination) {
        this.blockSource = blockSource;
        this.blockDestination = blockDestination;
    }

    public Client getBlockSource() {
        return blockSource;
    }

    public void setBlockSource(Client blockSource) {
        this.blockSource = blockSource;
    }

    public Client getBlockDestination() {
        return blockDestination;
    }

    public void setBlockDestination(Client blockDestination) {
        this.blockDestination = blockDestination;
    }

}
