package com.example.zlv.service;

public interface MoveTargetService {
    String randomLocation();

    boolean moveToTarget(String location);
}
