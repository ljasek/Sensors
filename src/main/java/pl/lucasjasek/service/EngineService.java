package pl.lucasjasek.service;


import java.util.List;

public interface EngineService {

    List<String> getIncorrectlyEngine(int pressureTreshold, int tempTreshold);
}